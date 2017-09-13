package com.WallE.TCMK.UI.SettingFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.WallE.TCMK.R;

/**
 * Created by 111 on 2017/9/7.
 */

public class SetAdviceFrag extends Fragment {

    private View view;
    private EditText advicetext;
    private Button advicebtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.setting_advice,container,false);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        advicetext=(EditText)view.findViewById(R.id.advice_text);
        advicebtn=(Button)view.findViewById(R.id.btn_advice);
        advicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                advicetext.setText("");
                Toast.makeText(getActivity(), "感谢您的意见", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
