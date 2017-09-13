package com.example.a111.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import POJO.DoctorInformation;
import POJO.PersonInfo;
import POJO.User;
import testjson.HttpClient;

/**
 * Created by 111 on 2017/9/7.
 */

public class SetDoctorFrag extends Fragment {
    private TextView doctorDep;
    private View view;
    private Button submitbtn;

    private User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.setting_doctor,container,false);
        /**
         * request service
         */
        getUser();
        /**
         * register broadcastReceiver to localBroadCast
         */
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver() ;
        IntentFilter intentFilter = new IntentFilter("Query_List") ;
        localBroadcastManager.registerReceiver( broadcastReceiver , intentFilter );

        doctorDep = (TextView)view.findViewById(R.id.docDep);
        submitbtn=(Button)view.findViewById(R.id.btn_doctor_submit);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setUserType("doctor");
                doctorRegister(new DoctorInformation(HttpClient.getUserCode(), doctorDep.getText().toString()));
                Toast.makeText(getActivity(), "成功提交", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private void getUser(){
        String hql = "from User as u where u.code = "+ HttpClient.getUserCode();
        Intent intent = new Intent();
        intent.setAction("intent_service");
        intent.setPackage(getActivity().getPackageName());
        intent.putExtra("param",7);
        intent.putExtra("hql", hql);
        intent.putExtra("className", "POJO.User");
        getActivity().startService(intent);
    }
    /**
     * get userType
     */
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<User> list = (List<User>)intent.getSerializableExtra("List");
            if(list.isEmpty() != true) {
                user = list.get(0);
                if(user.getUserType().equals("doctor")){
                    Toast.makeText(getActivity(), "已注册", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
            }else{
                 Toast.makeText(getContext(),"wrong usercode",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * registe doctor
     * @param doctorInformation
     */
    private void doctorRegister(DoctorInformation doctorInformation){
        Intent intent = new Intent();
        intent.setAction("intent_service");
        intent.setPackage(getActivity().getPackageName());
        intent.putExtra("param",8);
        intent.putExtra("Object", user);
        getActivity().startService(intent);
        Intent intent1 = new Intent();
        intent.setAction("intent_service");
        intent.setPackage(getActivity().getPackageName());
        intent.putExtra("param",9);
        intent.putExtra("Object", user);
        getActivity().startService(intent);
    }
}
