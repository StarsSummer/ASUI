package testjson;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
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
    String url;
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
        //url of server
        url = "http://[2001:da8:215:c658:3950:f92d:e1ca:8ed8]:8080/caffe/Login";
        //find user in database
        hql = "from User as user where user.userAccount='"+phonenum+"' and user.password='"+password+"'";
        System.out.println(hql);
        Request request = new Request.Builder()
                .url(url)
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
        url = "http://[2001:da8:215:c658:3950:f92d:e1ca:8ed8]:8080/caffe/Registion";

        Request request = new Request.Builder()
                .url(url)
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
        url = "http://[2001:da8:215:c658:3950:f92d:e1ca:8ed8]:8080/caffe/TongueAnalysis";
        File image = new File(path);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_JPG, image);
        builder.addFormDataPart("uploadfile", image.getName(), fileBody);
        builder.addFormDataPart("code", Integer.toString(userCode));
        builder.addFormDataPart("time",new Date().toString());
        Request request = new Request.Builder()
                .url(url)
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
        url = "ws://[2001:da8:215:c658:3950:f92d:e1ca:8ed8]:8080/caffe/Chat";
        String url1 = "ws://echo.websocket.org";
        Request request = new Request.Builder()
                .url(url)
                .build();
        webSocket = client.newWebSocket(request, listener);

        return new Connection(webSocket);
    }


}
