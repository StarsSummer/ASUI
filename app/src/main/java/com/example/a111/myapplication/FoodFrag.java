package com.example.a111.myapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 111 on 2017/8/25.
 */

public class FoodFrag extends Fragment {
    private View view;
    private List<Food> fruitList=new ArrayList<>();

    private FoodAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.content_food,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initfoods();
        RecyclerView recyclerView =(RecyclerView)view.findViewById(R.id.recycle_view);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new FoodAdapter(fruitList);
        recyclerView.setAdapter(adapter);
    }

    private void initfoods(){
        fruitList.clear();
        Food food1=new Food("薏米粥",R.drawable.food_image_a,"http://m.meishichina.com/recipe/3786/");
        Food food2=new Food("桂圆百合莲子",R.drawable.food_image_b,"http://m.meishichina.com/recipe/66896/");
        Food food3=new Food("川贝蒸梨",R.drawable.food_image_c,"http://m.meishichina.com/recipe/77287/");
        Food food4=new Food("酸奶",R.drawable.food_image_d,"http://m.meishichina.com/recipe/25162/");
        Food food5=new Food("核桃",R.drawable.food_image_e,"http://m.meishichina.com/recipe/47548/");
        Food food6=new Food("羊肉汤",R.drawable.food_image_f,"http://m.meishichina.com/recipe/45823/");
        fruitList.add(food1);
        fruitList.add(food2);
        fruitList.add(food3);
        fruitList.add(food4);
        fruitList.add(food5);
        fruitList.add(food6);
    }
}
