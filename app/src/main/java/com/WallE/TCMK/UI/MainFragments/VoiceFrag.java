package com.WallE.TCMK.UI.MainFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.WallE.TCMK.R;

/**
 * Created by 111 on 2017/8/2.
 */

public class VoiceFrag extends Fragment{

    private View view;
    private ImageView subt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.content_voice,container,false);
        subt=(ImageView)view.findViewById(R.id.imageView8);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        subt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"声音已保存，解析中...",Toast.LENGTH_LONG).show();
            }
        });
    }

}
