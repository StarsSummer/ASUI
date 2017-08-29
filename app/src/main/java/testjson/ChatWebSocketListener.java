package testjson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by Jinffee on 2017/8/19.
 */

public class ChatWebSocketListener extends WebSocketListener {
    //@param code usercode
    int code;

    public ChatWebSocketListener(int code){
        super();
        this.code = code;

    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        String usercode = new Gson().toJson(code);
        System.out.println("UserCode:"+usercode);
        webSocket.send("UserCode:"+usercode);

    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println("onMessage: " + text);
        String[] texts = text.split(":",2);
        if( texts[0].equals("List")){
            Type type = new TypeToken<List<Integer>>(){}.getType();
            List<Integer> doctors = new Gson().fromJson(texts[1], type);
            System.out.println(doctors);
            //add print doctors method
        }else if(texts[0].equals("Message")){
            String message = new Gson().fromJson(texts[1], String.class);
            System.out.println(message);
            //add print message method
        }else if(texts[0].equals("Error")){
            String errorMessage = new Gson().fromJson(texts[1], String.class);
            System.out.println(errorMessage);
        }else if(texts[0].equals("Connection Setup")){
            System.out.println("Connection SetUp");
        }else if(texts[0].equals("\"close\"")) {
            System.out.println("connection close");
        }else{
            System.out.println("wrong information!");
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        System.out.println("onMessage byteString: " + bytes);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        System.out.println("onClosing: " + code + "/" + reason);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        System.out.println("onClosed: " + code + "/" + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        System.out.println("onFailure: " + t.getMessage());
    }

}
