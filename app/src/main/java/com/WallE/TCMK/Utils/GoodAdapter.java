package com.WallE.TCMK.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.WallE.TCMK.R;
import com.WallE.TCMK.UI.UIActivities.GoodActivity;

import java.util.List;

/**
 * Created by 111 on 2017/9/16.
 */

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.ViewHolder> {
    private Context mcontext;
    private List<Good> goods;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View goodsView;
        ImageView imageView;
        TextView goodTitle;
        TextView goodPrice;
        public ViewHolder(View view){
            super(view);
            goodsView=(View)view;
            imageView=(ImageView)view.findViewById(R.id.good_image);
            goodTitle=(TextView)view.findViewById(R.id.good_name);
            goodPrice=(TextView)view.findViewById(R.id.good_price);
        }
    }

    public GoodAdapter(List<Good> goods){this.goods=goods;}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mcontext==null){
            mcontext=parent.getContext();
        }
        View view = LayoutInflater.from(mcontext).inflate(R.layout.good_list,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.goodsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Good good = goods.get(position);
                Intent introduce = new Intent(mcontext,GoodActivity.class);
                introduce.putExtra("link",good.getGoodurl());
                mcontext.startActivity(introduce);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Good good = goods.get(position);
        holder.imageView.setImageResource(good.getImageId());
        holder.goodTitle.setText(good.getName());
        holder.goodPrice.setText("¥"+String.valueOf(good.getGoodprice()));
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }
}
