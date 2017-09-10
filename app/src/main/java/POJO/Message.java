package POJO;

import java.util.Date;

/**
 * Created by Jinffee on 2017/9/10.
 */

public class Message {
    private int senderCode;
    private int receiverCode;
    private String message;
    private Date date;

    public Message(int senderCode, int receiverCode, String message, Date date) {
        this.senderCode = senderCode;
        this.receiverCode = receiverCode;
        this.message = message;
        this.date = date;
    }

    public int getSenderCode() {
        return senderCode;
    }

    public void setSenderCode(int senderCode) {
        this.senderCode = senderCode;
    }

    public int getReceiverCode() {
        return receiverCode;
    }

    public void setReceiverCode(int receiverCode) {
        this.receiverCode = receiverCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
