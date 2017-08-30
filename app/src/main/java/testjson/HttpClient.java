package testjson;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
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
import pojo.*;

/**
 * Created by Jinffee on 2017/8/17.
 */

public class HttpClient {
    MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain");
    MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");
    MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");
    //address
    String ip = "[2001:da8:215:c658:3950:f92d:e1ca:8ed8]";
    String port = "8080";
    String projectname = "caffe";
    //find user in database"

    OkHttpClient client;
    WebSocket webSocket;
    Gson gson;
    String hql;

    public HttpClient(){
        client= new OkHttpClient.Builder().readTimeout(3000, TimeUnit.SECONDS)//设置读取超时时间
                                            .writeTimeout(3000, TimeUnit.SECONDS)//设置写的超时时间
                                            .connectTimeout(3000, TimeUnit.SECONDS).build();//设置连接超时时间  ;
        gson=new Gson();
    }
    //login
    public int login(String phonenum,String password) throws IOException {
        int code;
        //find user in database
        hql = "from User as user where user.userAccount='"+phonenum+"' and user.password='"+password+"'";
        System.out.println(hql);
        Request request = new Request.Builder()
                .url("http://" +  ip + ":" + port + "/" + projectname + "/Login")//url of server
                .post(RequestBody.create(MEDIA_TYPE_TEXT,hql))
                .build();
        Response response;
        response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code: " + response);
        code = gson.fromJson(response.body().charStream(), int.class);
        return code;
    }
    //User sign up
    public char normalUserSignUp(User user) throws IOException, SignUpException {
        // 0 is false 1 is ready
        char signUpStatues;

        Request request = new Request.Builder()
                .url("http://" +  ip + ":" + port + "/" + projectname + "/Registion")//url of server
                .post(RequestBody.create(MEDIA_TYPE_JSON,gson.toJson(user)))
                .build();
        Response response;
        response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new SignUpException(SignUpException.CREATE_FIAL);
        signUpStatues = gson.fromJson(response.body().charStream(), char.class);
        System.out.println(signUpStatues);
        return signUpStatues;

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
    public String tougunJudge(int userCode,String path) throws IOException {

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
        Response response;
        response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code: " + response);
        String result = gson.fromJson(response.body().charStream(), String.class);
        System.out.println(result);
        return result;
    }
    public Connection connectionInit(int code){
        ChatWebSocketListener listener = new ChatWebSocketListener(code);
        Request request = new Request.Builder()
                .url("ws://" +  ip + ":" + port + "/" + projectname + "/Chat")//url of server
                .build();
        webSocket = client.newWebSocket(request, listener);

        return new Connection(webSocket);
    }
    public <T> List<T> Query(String hql, Class<T> clazz) throws IOException {
        Request request = new Request.Builder()
                .url("http://" +  ip + ":" + port + "/" + projectname + "/Query")//url of server
                .post(RequestBody.create(MEDIA_TYPE_TEXT,hql))
                .build();
        Response response;
        response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code: " + response);
        Type type = new TypeToken<List<T>>(){}.getType();
        List<T> result = gson.fromJson(response.body().charStream() , type);
        return result;
    }

}
