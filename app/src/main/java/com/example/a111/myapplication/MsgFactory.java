package com.example.a111.myapplication;

import POJO.Message;

/**
 * Created by Jinffee on 2017/9/10.
 */

public class MsgFactory {
    private static int userCode;

    public static void setUserCode(int userCode) {
        MsgFactory.userCode = userCode;
    }
    public static Msg convertToMsg(Message message) throws Exception {
        if(message.getSenderCode() == userCode){
            return new Msg(message.getMessage(),Msg.TYPE_SENT);
        }else if(message.getReceiverCode() == userCode){
            return new Msg(message.getMessage(),Msg.TYPE_RECEIVED);
        }else{
            throw new Exception("Wrong message");
        }
    }

}
