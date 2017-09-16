package com.example.a111.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 111 on 2017/9/16.
 */

public class ShopFrag extends Fragment{
    private View view;
    private List<Good> goods=new ArrayList<>();

    private GoodAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.content_shop,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initgoods();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.shopping);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        adapter =new GoodAdapter(goods);
        recyclerView.setAdapter(adapter);
    }

    private void initgoods(){
        goods.clear();
        Good good1=new Good("藿香正气水",R.drawable.shopping_1,19.80,"https://detail.m.liangxinyao.com/item.htm?spm=a220m.6910245.0.0.1c0bb1cbSGPr0a&id=540235417005&skuId=3203011031609&pic=//img.alicdn.com/bao/uploaded/i3/TB1TBbzPXXXXXbNaXXXXXXXXXXX_!!0-item_pic.jpg_540x540Q50s50.jpg_.webp&itemTitle=%E5%A4%AA%E6%9E%81%E8%97%BF%E9%A6%99%E6%AD%A3%E6%B0%94%E5%8F%A3%E6%9C%8D%E6%B6%B210ml*10%E6%94%AF/%E7%9B%92%E4%B8%AD%E6%9A%91%E5%91%95%E5%90%90%E6%B3%84%E6%B3%BB%20%E5%A4%8F%E5%AD%A3%E9%98%B2%E6%9A%91%E6%AD%A3%E6%B0%94%E6%B0%B4&price=19.80&from=h5");
        Good good2=new Good("手环",R.drawable.shopping_2,149.00,"https://detail.m.tmall.com/item.htm?spm=a220m.6910245.0.0.1c0bb1cbHW5ozj&id=529014702990&skuId=3179562322611&pic=//img.alicdn.com/bao/uploaded/i1/1714128138/TB2w59Wa7UkyKJjSspkXXXhAFXa_!!1714128138.jpg_490x330Q50s50.jpg_.webp&itemTitle=%E5%B0%8F%E7%B1%B3%E6%89%8B%E7%8E%AF2%E6%99%BA%E8%83%BD%E8%93%9D%E7%89%99%E7%94%B7%E5%A5%B3%E6%AC%BE%E8%BF%90%E5%8A%A8%E8%AE%A1%E6%AD%A5%E5%99%A8%E5%BF%83%E7%8E%87%E7%9D%A1%E7%9C%A0%E7%9B%91%E6%B5%8B%E5%AD%A6%E7%94%9F%E6%89%8B%E8%A1%A8%E5%8C%85%E9%82%AE&price=149.00&from=h5");
        Good good3=new Good("板蓝根颗粒",R.drawable.shopping_3,12.50,"https://detail.m.liangxinyao.com/item.htm?spm=a220m.6910245.0.0.1c0bb1cbpsHByl&id=539366168084&skuId=3195408839255&pic=//img.alicdn.com/bao/uploaded/i1/T1sLp8Ft8fXXcZ1lnb_094754.jpg_490x330Q50s50.jpg_.webp&itemTitle=%E7%99%BD%E4%BA%91%E5%B1%B1%20%E6%9D%BF%E8%93%9D%E6%A0%B9%E9%A2%97%E7%B2%92%2010g*20%E8%A2%8B/%E5%8C%85&price=12.50&from=h5");
        Good good4=new Good("血压计",R.drawable.shopping_4,119.00,"https://detail.m.tmall.com/item.htm?spm=a220m.6910245.0.0.1c0bb1cbyhWPSZ&id=41797684534&skuId=3434463536214&pic=//img.alicdn.com/bao/uploaded/i1/2074722020/TB1KYu5foQIL1JjSZFhXXaDZFXa_!!0-item_pic.jpg_490x330Q50s50.jpg_.webp&itemTitle=%E5%AE%B6%E7%94%A8%E5%8C%BB%E7%94%A8%E8%80%81%E4%BA%BA%E4%B8%8A%E8%87%82%E5%BC%8F%E5%85%A8%E8%87%AA%E5%8A%A8%E9%AB%98%E7%B2%BE%E5%87%86%E8%AF%AD%E9%9F%B3%E7%94%B5%E5%AD%90%E9%87%8F%E8%A1%80%E5%8E%8B%E8%AE%A1%E6%B5%8B%E9%87%8F%E4%BB%AA%E5%99%A8%E6%B5%8B%E5%8E%8B%E4%BB%AA&price=119.00&from=h5");
        Good good5=new Good("加湿器",R.drawable.shopping_5,79.00,"https://detail.tmall.com/item.htm?spm=a220m.6910245.0.0.1c0bb1cbCd4ayc&id=41649437475&skuId=3595032734083&pic=//img.alicdn.com/bao/uploaded/i3/TB1s.UwNVXXXXaHapXXn6xCFFXX_093137.jpg_490x330Q50s50.jpg_.webp&itemTitle=Bear/%E5%B0%8F%E7%86%8A%20JSQ-A40A2&price=79.00&from=h5");
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);
        goods.add(good5);
    }

}
