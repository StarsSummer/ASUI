package com.WallE.TCMK.HTTPClientService;

import android.util.Log;

import okhttp3.WebSocket;

/**
 * Created by Jinffee on 2017/8/20.
 */

public class Connection {
    private String logTag = "Connection Class";
    //to send message
    private final static int NORMAL_CLOSURE = 1000;
    private WebSocket webSocket;
    public Connection(WebSocket webSocket) {
        this.webSocket = webSocket;
    }
    public  boolean initChat(){
        Log.i(logTag, "Connection");
        return webSocket.send("Connection");
    }
    public boolean selectDoctor(int doctorCode){
        return webSocket.send("Doctor:" + doctorCode);
    }
    public boolean send(String message){
        return webSocket.send("Message:" + message);
    }
    public boolean closeChat(){
        return webSocket.send("Close");
    }
    public boolean close(){
        return webSocket.close(NORMAL_CLOSURE, null);

    }
}
