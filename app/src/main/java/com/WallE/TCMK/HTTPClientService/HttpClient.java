package com.WallE.TCMK.HTTPClientService;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.WallE.TCMK.POJO.Doctor;
import com.WallE.TCMK.POJO.DoctorInformation;
import com.WallE.TCMK.POJO.HealthInfo;
import com.WallE.TCMK.POJO.PersonInfo;
import com.WallE.TCMK.POJO.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
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

/**
 * Created by Jinffee on 2017/8/17.
 */

public class HttpClient {
    private String logTag = "HttpClient";
    private MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain");
    private MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");
    private MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");
    //address
    private String ip = "10.206.11.2";
    private String port = "8080";
    private String projectname = "caffe";
    //find user in database"

    private OkHttpClient client;
    private Gson gson;
    private String hql;
    public HttpClient(){
        client= new OkHttpClient.Builder().readTimeout(3000, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3000, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3000, TimeUnit.SECONDS).build();//设置连接超时时间  ;
        gson=new Gson();
    }

    public HttpClient(String ip, String port, String projectname){
        this.ip = ip;
        this.port = port;
        this.projectname = projectname;
        client= new OkHttpClient.Builder().readTimeout(3000, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3000, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3000, TimeUnit.SECONDS).build();//设置连接超时时间  ;
        gson=new Gson();
    }
    /**
     * User Login
     * @param phonenum
     * @param password
     * @return
     * @throws IOException
     */
    int login(String phonenum, String password) throws IOException {
        //find user in database
        hql = "from User as user where user.userAccount='"+phonenum+"' and user.password='"+password+"'";
        Log.i(logTag, hql);
        Request request = new Request.Builder()
                .url("http://" +  ip + ":" + port + "/" + projectname + "/Login")//url of server
                .post(RequestBody.create(MEDIA_TYPE_TEXT,hql))
                .build();
        //request will enqueue to send
        Response response = client.newCall(request).execute();
        if(!response.isSuccessful()) throw new IOException("Unexpected code: " + response);
        int userCode = gson.fromJson(response.body().charStream(), int.class);
        return userCode;
    }

    /**
     * User Sign Up
     * @param user
     * @param userName
     * @return
     * @throws IOException
     */
    int UserSignUp(User user, final String userName) throws IOException {
        Request request = new Request.Builder()
                .url("http://" + ip + ":" + port + "/" + projectname + "/Registion")//url of server
                .post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(user)))
                .build();
        //request will enqueue to send
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())  throw new IOException("Unexpected code: " + response);
        int userCode = gson.fromJson(response.body().charStream(), int.class);
        return userCode;

    }

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

    /**
     * judge Tougun
     * @param path
     * @param userCode
     * @return
     * @throws IOException
     */
    String tougunJudge(String path, int userCode) throws IOException {

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
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code: " + response);
        String result = gson.fromJson(response.body().charStream(), String.class);
        return result;
    }

    /**
     * initialize connection
     * @param listener
     * @return
     */
    Connection connectionInit(WebSocketListener listener){
        Request request = new Request.Builder()
                .url("ws://" +  ip + ":" + port + "/" + projectname + "/Chat")//url of server
                .build();
        Log.i(logTag, "ws://" + ip + ":" + port + "/" + projectname + "/Chat");
        WebSocket webSocket = client.newWebSocket(request, listener);
        return new Connection(webSocket);
    }

    /**
     *
     */
    private static class ListParameterizedType implements ParameterizedType {
        private Type type;
        private ListParameterizedType(Type type) {
            this.type = type;
        }
        @Override
        public Type[] getActualTypeArguments() {
            return new Type[] {type};
        }
        @Override
        public Type getRawType() {
            return ArrayList.class;
        }
        @Override
        public Type getOwnerType() {
            return null;
        }
    }

    /**
     *
     * @param hql
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> List<T> query(String hql, final Class<T> clazz) throws IOException {
        Request request = new Request.Builder()
                .url("http://" +  ip + ":" + port + "/" + projectname + "/Query")//url of server
                .post(RequestBody.create(MEDIA_TYPE_TEXT,hql))
                .build();
        //request will enqueue to send
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code: " + response);
        Type type = new ListParameterizedType(clazz);;
        List<T> result = gson.fromJson(response.body().charStream(), type);
        return result;

    }

    /**
     *
     * @param object
     * @throws HttpException
     * @throws IOException
     */
    public void insert(Object object) throws HttpException, IOException {
        String json;
        if(object instanceof PersonInfo) {
            json = "PersonInfo;"+gson.toJson(object);
        }else if(object instanceof HealthInfo){
            json = "HealthInfo;"+gson.toJson(object);
        }else if(object instanceof DoctorInformation){
            json = "DoctorInformation;"+gson.toJson(object);
        }else{
            throw new HttpException(HttpException.Type_Wrong);
        }
        Request request = new Request.Builder()
                .url("http://" +  ip + ":" + port + "/" + projectname + "/Save")//url of server
                .post(RequestBody.create(MEDIA_TYPE_JSON,json))
                .build();
        //request will enqueue to send
        Response response = client.newCall(request).execute();
        Log.i(logTag,"insert success");
    }
    public void update(Object object) throws HttpException, IOException {
        String json;

        if(object instanceof PersonInfo) {
            json = "PersonInfo;"+gson.toJson(object);
        }else{
            throw new HttpException(HttpException.Type_Wrong);
        }
        Request request = new Request.Builder()
                .url("http://" +  ip + ":" + port + "/" + projectname + "/Update")//url of server
                .post(RequestBody.create(MEDIA_TYPE_JSON,json))
                .build();
        //request will enqueue to send
        Response response = client.newCall(request).execute();
        Log.i(logTag,"update success");
//        if (!response.isSuccessful()) throw new HttpException(HttpException.CREATE_FIAL);
//        signUpStatues = gson.fromJson(response.body().charStream(), char.class);
//        Log.i(logTag, signUpStatues);
//        return signUpStatues;
    }



}
