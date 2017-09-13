package com.example.a111.myapplication;

/**
 * Created by 111 on 2017/8/2.
 */

public class Doctor {
    private String name;
    private String stat;

    public Doctor(String name,String stat){
        this.name=name;
        this.stat=stat;
    }
    public String getName(){
        return  name;
    }
    public String getStat() { return  stat; }

}
