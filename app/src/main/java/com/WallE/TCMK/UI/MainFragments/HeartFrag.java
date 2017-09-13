package com.WallE.TCMK.UI.MainFragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.WallE.TCMK.R;

/**
 * Created by 111 on 2017/8/2.
 */

public class HeartFrag extends Fragment{

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.content_heart,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        RadioButton pressure=(RadioButton)view.findViewById(R.id.link_pressure);
        Drawable drawable_pressure=getResources().getDrawable(R.drawable.ic_link_pressure);
        drawable_pressure.setBounds(0,0,100,100);
        pressure.setCompoundDrawables(null,drawable_pressure,null,null);
        RadioButton heart=(RadioButton)view.findViewById(R.id.link_heart);
        Drawable drawable_heart=getResources().getDrawable(R.drawable.ic_link_heart);
        drawable_heart.setBounds(0,0,100,100);
        heart.setCompoundDrawables(null,drawable_heart,null,null);
        RadioButton weight=(RadioButton)view.findViewById(R.id.link_weight);
        Drawable drawable_weight=getResources().getDrawable(R.drawable.ic_link_weight);
        drawable_weight.setBounds(0,0,100,100);
        weight.setCompoundDrawables(null,drawable_weight,null,null);
    }

}
