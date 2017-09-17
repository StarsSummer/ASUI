package com.WallE.TCMK.Utils;

/**
 * Created by 111 on 2017/8/25.
 */

public class Food {
    private String name;
    private int imageId;
    private String foodurl;

    public Food(String name,int imageId,String foodurl){
        this.name = name;
        this.imageId =imageId;
        this.foodurl =foodurl;
    }

    public String getName(){
        return name;
    }
    public String getFoodurl() {return foodurl;}
    public int getImageId(){
        return imageId;
    }
}
