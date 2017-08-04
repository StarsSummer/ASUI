package com.example.a111.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by 111 on 2017/8/2.
 */

public class CenterFrag extends Fragment{

    private View view;
    private Button imp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.content_center,container,false);
        imp=(Button)view.findViewById(R.id.import_btn);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"导入中...",Toast.LENGTH_LONG).show();
            }
        });
    }

}
