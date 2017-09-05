package testjson;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import pojo.*;

import static testjson.SignUpException.SERVER_WRONG_RETURN;

/**
 * Created by Jinffee on 2017/8/17.
 */

public class HttpClient extends Service{
    private String logTag = "HttpClient";
    private MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain");
    private MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");
    private MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");
    //address
    private String ip = "[2001:da8:215:c658:d491:acfd:6a4e:6dc8]";
    private String port = "8080";
    private String projectname = "caffe";
    //find user in database"

    private OkHttpClient client;
    private WebSocket webSocket;
    private Gson gson;
    private String hql;
    private Connection conection;
    private int userCode;
    public HttpClient(){
        client= new OkHttpClient.Builder().readTimeout(3000, TimeUnit.SECONDS)//设置读取超时时间
                                            .writeTimeout(3000, TimeUnit.SECONDS)//设置写的超时时间
                                            .connectTimeout(3000, TimeUnit.SECONDS).build();//设置连接超时时间  ;
        gson=new Gson();
    }
    public class LocalBinder extends Binder {
        public HttpClient getService() {
            // Return this instance of LocalService so clients can call public methods
            return HttpClient.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new LocalBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    //login
    public void login(String phonenum,String password,Callback callback){
        int code;
        //find user in database
        hql = "from User as user where user.userAccount='"+phonenum+"' and user.password='"+password+"'";
        System.out.println(hql);
        Request request = new Request.Builder()
                .url("http://" +  ip + ":" + port + "/" + projectname + "/Login")//url of server
                .post(RequestBody.create(MEDIA_TYPE_TEXT,hql))
                .build();
        //request will enqueue to send
        client.newCall(request).enqueue(callback);
        //if (!response.isSuccessful()) throw new IOException("Unexpected code: " + response);
        //code = gson.fromJson(response.body().charStream(), int.class);
        //return code;
    }
    //User sign up
    public void normalUserSignUp(User user, Callback callback) {
        // 0 is false and 1 is ready
        char signUpStatues;

        Request request = new Request.Builder()
                .url("http://" + ip + ":" + port + "/" + projectname + "/Registion")//url of server
                .post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(user)))
                .build();
        //request will enqueue to send
        client.newCall(request).enqueue(callback);
    }
//        if (!response.isSuccessful()) throw new SignUpException(SignUpException.CREATE_FIAL);
//        signUpStatues = gson.fromJson(response.body().charStream(), char.class);
//        System.out.println(signUpStatues);
//        return signUpStatues;


   /* private byte[] imageToByte(String path) throws IOException {
        byte[] data = null;
        FileInputStream input = null;
        input = new FileInputStream(new File(path));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int numBytesRead = 0;
        while ((numBytesRead = input.read(buf)) != -1) {
            output.write(buf, 0, numBytesRead);
        }
        data = output.toByteArray();
        output.close();
        input.close();
        return data;
    }*/
    public void tougunJudge(int userCode, String path, Callback callback) throws IOException {

        File image = new File(path);
        Date date = new Date();
        //cover with json
        String dgson = gson.toJson(date);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_JPG, image);
        builder.addFormDataPart("uploadfile", image.getName(), fileBody);
        builder.addFormDataPart("code", Integer.toString(userCode));
        builder.addFormDataPart("time",dgson);
        Request request = new Request.Builder()
                .url("http://" +  ip + ":" + port + "/" + projectname + "/TongueAnalysis")//url of server
                .post(builder.build())
                .build();
        //request will enqueue to send
        client.newCall(request).enqueue(callback);
//        if (!response.isSuccessful()) throw new IOException("Unexpected code: " + response);
//        String result = gson.fromJson(response.body().charStream(), String.class);
//        System.out.println(result);
//        return result;
    }
    // websocket service
    public void connectionInit(final int code){
        Request request = new Request.Builder()
                .url("ws://" +  ip + ":" + port + "/" + projectname + "/Chat")//url of server
                .build();
        System.out.println("ws://" + ip + ":" + port + "/" + projectname + "/Chat");
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                String usercode = new Gson().toJson(code);
                System.out.println("UserCode:"+usercode);
                webSocket.send("UserCode:"+usercode);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Intent intent = new Intent();
                System.out.println( "onMessage: " + text);
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
                    sendBroadcast(intent);
                }else if(texts[0].equals("Message")){
                    String message = new Gson().fromJson(texts[1], String.class);
                    System.out.println(message);
                    //TODO:add print message method and store message method
                    intent.putExtra("Message",message);
                    intent.setAction("Get_Message");
                    sendBroadcast(intent);
                }else if(texts[0].equals("Error")){
                    String errorMessage = new Gson().fromJson(texts[1], String.class);
                    System.out.println( errorMessage);
                    //TODO:add Error sort method
//                    intent.setAction("Get_Error");
//                    sendBroadcast(intent);
                }else if(texts[0].equals("Connection Setup")){
                    System.out.println(  "Connection SetUp");
                    /*intent.setAction("Connection_SetUp");
                    sendBroadcast(intent);*/
                }else if (texts[0].equals("\"close\"")) {
                    System.out.println("connection close");
//                    intent.setAction("Connection_SetUp");
//                    sendBroadcast(intent);
                } else {
                    System.out.println("wrong information!");

                }

            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                webSocket.close(1000, null);
                System.out.println(  "onClosing: " + code + "/" + reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                System.out.println(  "onClosed: " + code + "/" + reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                System.out.println( "onFailure: " + t.getMessage());
            }
        });
        conection =new Connection(webSocket);
    }
    public Connection getConection(){
        return this.conection;
    }
    public void Query(String hql, Class<?> clazz, Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url("http://" +  ip + ":" + port + "/" + projectname + "/Query")//url of server
                .post(RequestBody.create(MEDIA_TYPE_TEXT,hql))
                .build();
        //request will enqueue to send
//        client.newCall(request).enqueue(callback);
//        if (!response.isSuccessful()) throw new IOException("Unexpected code: " + response);
//        Type type = new TypeToken<List<T>>(){}.getType();
//        List<T> result = gson.fromJson(response.body().charStream() , type);
//        return result;
    }

    public void insert(Object object,Callback callback) throws IOException, SignUpException {
        char signUpStatues;
        String json;
        if(object instanceof User){
            json = "User;"+gson.toJson(object);
        }else if(object instanceof PersonInfo) {
            json = "PersonInfo;"+gson.toJson(object);
        }else if(object instanceof HealthInfo){
            json = "HealthInfo;"+gson.toJson(object);
        }else if(object instanceof DoctorInformation){
            json = "DoctorInformation;"+gson.toJson(object);
        }else{
            throw new SignUpException(SignUpException.Type_Wrong);
        }
        Request request = new Request.Builder()
                .url("http://" +  ip + ":" + port + "/" + projectname + "/Save")//url of server
                .post(RequestBody.create(MEDIA_TYPE_JSON,json))
                .build();
        //request will enqueue to send
        client.newCall(request).enqueue(callback);
//        if (!response.isSuccessful()) throw new SignUpException(SignUpException.CREATE_FIAL);
//        signUpStatues = gson.fromJson(response.body().charStream(), char.class);
//        System.out.println(signUpStatues);
//        return signUpStatues;
    }

}
