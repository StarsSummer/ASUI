package com.example.a111.myapplication;

/**
 * Created by 111 on 2017/9/3.
 */

public class Msg {
    public static final int TYPE_RECEIVED=0;
    public static final int TYPE_SENT=1;
    private String content;
    private int type;
    public Msg(String content,int type){
        this.content=content;
        this.type=type;
    }
    public String getMsgContent(){
        return content;
    }
    public int getMsgType(){
        return type;
    }

}
