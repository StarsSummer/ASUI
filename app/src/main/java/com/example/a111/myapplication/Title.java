package com.example.a111.myapplication;

/**
 * Created by 111 on 2017/8/2.
 */

public class Title {
    private String name;
    private int imageid;
    private int colorid;

    public Title(String name,int imageid,int colorid){
        this.name=name;
        this.imageid=imageid;
        this.colorid=colorid;
    }
    public String getName(){
        return  name;
    }
    public int getImageid(){
        return imageid;
    }
    public int getColor(){
        return colorid;
    }
}
