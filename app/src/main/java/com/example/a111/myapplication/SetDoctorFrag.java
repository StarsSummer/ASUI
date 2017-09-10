package com.example.a111.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 111 on 2017/9/7.
 */

public class SetDoctorFrag extends Fragment {
    private TextView doctorDep;
    private View view;
    private Button submitbtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.setting_doctor,container,false);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        doctorDep = (TextView)view.findViewById(R.id.docDep);
        submitbtn=(Button)view.findViewById(R.id.btn_doctor_submit);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "成功提交", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
