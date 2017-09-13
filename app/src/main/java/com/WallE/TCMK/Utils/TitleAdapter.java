package com.WallE.TCMK.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.WallE.TCMK.R;

import java.util.List;

/**
 * Created by 111 on 2017/8/2.
 */

public class TitleAdapter extends ArrayAdapter<Title> {
    private int resourceID;
    public TitleAdapter(Context context, int textViewResouceID, List<Title> objects){
        super(context,textViewResouceID,objects);
        resourceID=textViewResouceID;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Title title=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceID,null);
        ImageView titleimage=(ImageView)view.findViewById(R.id.id_icon);
        TextView titletext=(TextView)view.findViewById(R.id.id_name);
        RelativeLayout titlelayout=(RelativeLayout)view.findViewById(R.id.id_title);
        titlelayout.setBackgroundColor(title.getColor());
        titleimage.setImageResource(title.getImageid());
        titletext.setText(title.getName());
        return view;
    }
}
