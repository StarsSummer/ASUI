package com.WallE.TCMK.HTTPClientService;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.WallE.TCMK.POJO.Doctor;
import com.WallE.TCMK.POJO.PersonInfo;
import com.WallE.TCMK.POJO.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * Created by Jinffee on 2017/9/15.
 */

public class HTTPClientService extends IntentService {
    /**
     * signals of request
     */
    public static final int NO_PARMA = 0;
    public static final int LOGIN = 1;
    public static final int SIGN_UP = 2;
    public static final int INIT_CONNENT = 3;
    public static final int INIT_CHAT = 4;
    public static final int SEND_MESSAGE = 5;
    public static final int TONGUE_JUDGE = 6;
    public static final int QUERY = 7;
    public static final int UPDATE = 8;
    public static final int INSERT = 9;

    private static HttpClient httpClient = new HttpClient("","","");
    private static Connection connection;

    private static String logTag = "httpClientservice";

    private LocalBroadcastManager bm  = LocalBroadcastManager.getInstance(this);

    private static final int USER_STATUS_DEFAULT = -1;
    private static final int USER_STATUS_TEST = -2;
    private static int userCode = USER_STATUS_DEFAULT;

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int order = intent.getIntExtra("param", 0);
        switch (order){
            case NO_PARMA:
                Log.i(logTag, "did't get param");
                break;
            case LOGIN:
                setLogin(intent.getStringExtra("phonenum"),intent.getStringExtra("password"));
                break;
            case SIGN_UP:
                try {
                    setSignUp(new User(0, intent.getStringExtra("phonenum"),"user",intent.getStringExtra("password")), intent.getStringExtra("userName"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case INIT_CONNENT:
                try {
                    setInitConnent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case INIT_CHAT:
                connection.initChat();
                break;
            case SEND_MESSAGE:
                connection.send(intent.getStringExtra("message"));
                break;
            case TONGUE_JUDGE:
                try {
                    setTongueJudge(intent.getStringExtra("File"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case QUERY:
                try {
                    setQuery(intent.getStringExtra("hql"),Class.forName(intent.getStringExtra("className")));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case UPDATE:
                try {
                    setUpdate(intent.getSerializableExtra("Object"));
                } catch (HttpException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case INSERT:
                try {
                    setInsert(intent.getSerializableExtra("Object"));
                } catch (HttpException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private void setLogin(String phonenum, String password){
        /**
         * permission check
         */
        if(userCode == USER_STATUS_DEFAULT){
            if( phonenum.equals("test") && password.equals("test"))
                userCode = USER_STATUS_TEST;
            else
                try {
                    userCode = httpClient.login(phonenum, password);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            Log.i(logTag,"Login usercode:"+userCode);
            Intent intent = new Intent();
            intent.setAction("Log_in");
            intent.putExtra("code",userCode);
            bm.sendBroadcast(intent);
        }else
            try {
                throw new Exception("Wrong user");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    private void setSignUp(User user, String userName) throws IOException {
        /**
         * permission check
         */
        if(userCode == USER_STATUS_DEFAULT){
            try{
                userCode = httpClient.UserSignUp(user, userName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //TODO: send Broadcast to activity
            Intent intent = new Intent();
            intent.setAction("REGISTER");
            intent.putExtra("code",userCode);
            bm.sendBroadcast(intent);
            /**
             * create PersonInfo table
             */
            if(userCode != USER_STATUS_DEFAULT){
                try {
                    httpClient.insert(new PersonInfo(userCode,userName));
                } catch (HttpException e) {
                    e.printStackTrace();
                }
            }
        }else
            try {
                throw new Exception("Wrong user");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    private void setInitConnent() throws Exception {
        /**
         * permission check
         */
        if(userCode > USER_STATUS_DEFAULT){
            connection = httpClient.connectionInit(new WebSocketListener() {
                @Override
                public void onOpen(WebSocket webSocket, Response response) {
                    String usercode = new Gson().toJson(userCode);
                    Log.i(logTag, "UserCode:"+usercode);
                    webSocket.send("UserCode:"+usercode);
                }

                @Override
                public void onMessage(WebSocket webSocket, String text) {
                    Intent intent = new Intent();
                    Log.i(logTag,  "onMessage: " + text);
                    String[] texts = text.split(":",2);
                    if( texts[0].equals("List")){
                        Type type = new TypeToken<List<Object[]>>(){}.getType();
                        List<Object[]> objects = new Gson().fromJson(texts[1], type);
                        List<Doctor> doctors = new LinkedList<Doctor>();
                        for (Object[] doctor : objects)
                            doctors.add(new Doctor((int)doctor[0],(String)doctor[1],(String)doctor[2],(Byte[])doctor[3]));
                        //TODO:add print doctors method
                        intent.setAction("Get_Doctor_List");
                        intent.putExtra("List",(Serializable)doctors);
                        bm.sendBroadcast(intent);
                    }else if(texts[0].equals("Message")){
                        String message = new Gson().fromJson(texts[1], String.class);
                        Log.i(logTag, message);
                        //TODO:add print message method and store message method
                        intent.putExtra("Message",message);
                        intent.setAction("Get_Message");
                        bm.sendBroadcast(intent);
                    }else if(texts[0].equals("Error")){
                        String errorMessage = new Gson().fromJson(texts[1], String.class);
                        Log.i(logTag,  errorMessage);
                        //TODO:add Error sort method
//                    intent.setAction("Get_Error");
//                    sendBroadcast(intent);
                    }else if(texts[0].equals("Connection Setup")){
                        Log.i(logTag,   "Connection SetUp");
                    /*intent.setAction("Connection_SetUp");
                    sendBroadcast(intent);*/
                    }else if (texts[0].equals("\"close\"")) {
                        Log.i(logTag, "connection close");
//                    intent.setAction("Connection_SetUp");
//                    sendBroadcast(intent);
                    } else {
                        Log.i(logTag, "wrong information!");

                    }

                }

                @Override
                public void onClosing(WebSocket webSocket, int code, String reason) {
                    webSocket.close(1000, null);
                    Log.i(logTag,   "onClosing: " + code + "/" + reason);
                }

                @Override
                public void onClosed(WebSocket webSocket, int code, String reason) {
                    Log.i(logTag,   "onClosed: " + code + "/" + reason);
                }

                @Override
                public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                    Log.i(logTag,  "onFailure: " + t.getMessage());
                }
            });
        }else if(userCode == USER_STATUS_TEST){

        }else{
            throw new Exception("Wrong user");
        }
    }
    private void setTongueJudge(String path) throws Exception {
        String result;
        /**
         * permission check
         */
        if(userCode > USER_STATUS_DEFAULT){
            result = httpClient.tougunJudge(path, userCode);
        }
        else if (userCode == USER_STATUS_TEST){
            result = "OK";
        }else
            throw new Exception("Wrong user");

        Log.i(logTag, result);
        Intent intent = new Intent();
        intent.setAction("Result");
        intent.putExtra("result",result);
        bm.sendBroadcast(intent);
    }
    private <T> void setQuery(String hql, Class<T> clazz) throws Exception {
        List<T> result;
        /**
         * permission check
         */
        if(userCode > USER_STATUS_DEFAULT){
            result = httpClient.query(hql, clazz);
        }else if (userCode == USER_STATUS_TEST)
            result = null;
        else
            throw new Exception("Wrong user");
        Intent queryIntent = new Intent();
        queryIntent.setAction("Query_List");
        queryIntent.putExtra("List",(Serializable)result);
        bm.sendBroadcast(queryIntent);
    }
    private void setInsert(Object object) throws Exception {
        if(userCode > USER_STATUS_DEFAULT){
            httpClient.insert(object);
        }else if (userCode == USER_STATUS_TEST);
        else
            throw new Exception("Wrong user");
    }
    private void setUpdate(Object object) throws Exception {
        if(userCode > USER_STATUS_DEFAULT){
            httpClient.update(object);
        }else if (userCode == USER_STATUS_TEST);
        else
            throw new Exception("Wrong user");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public HTTPClientService() {
        super("HTTPClientService");
    }

    public static void setUserCode(int userCode){
        HTTPClientService.userCode = userCode;
    }
    public static int getUserCode(){
        return userCode;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(logTag,"On Start Command");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(logTag, "On Destroy");
        super.onDestroy();
    }
}
