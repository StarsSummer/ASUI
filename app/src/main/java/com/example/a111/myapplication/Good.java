package com.example.a111.myapplication;

/**
 * Created by 111 on 2017/9/16.
 */

public class Good {
    private String name;
    private int imageId;
    private double goodprice;
    private String goodurl;

    public Good(String name,int imageId,double goodprice,String goodurl){
        this.name=name;
        this.imageId=imageId;
        this.goodprice=goodprice;
        this.goodurl=goodurl;
    }
    public String getName(){return name;}
    public int getImageId(){return imageId;}
    public double getGoodprice(){return goodprice;}
    public String getGoodurl(){return goodurl;}
}
