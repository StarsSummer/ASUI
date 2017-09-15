package com.WallE.TCMK.UI.MainFragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import com.WallE.TCMK.POJO.Doctor;
import com.WallE.TCMK.Utils.DoctorAdapter;
import com.WallE.TCMK.R;

/**
 * Created by 111 on 2017/9/1.
 */

public class ChatFrag extends Fragment{
    private View view;
    private List<Doctor> doctorList=new ArrayList<>();

    private DoctorAdapter adapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.content_chat,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //initdoctors();
        //通过代码的方式动态注册MyBroadcastReceiver
        MyBroadcastReceiver receiver=new MyBroadcastReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("Get_Doctor_List");
        //注册receiver
        getActivity().getApplicationContext().registerReceiver(receiver, filter);
        recyclerView =(RecyclerView)view.findViewById(R.id.chat_recycle);

        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        //TODO: 医生列表获取及缓存处理
        Intent intent = new Intent();
        intent.setAction("intent_service");
        intent.setPackage(getActivity().getPackageName());
        intent.putExtra("param",4);
        getActivity().startService(intent);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            doctorList = (List<Doctor>)intent.getSerializableExtra("List");
            if(doctorList != null){
                adapter=new DoctorAdapter(doctorList);
                recyclerView.setAdapter(adapter);
                TextView headtext=(TextView)view.findViewById(R.id.headpic_text_chat);
                headtext.setText("专业医生答疑\n值得信赖");
            }
        }
    }

    private void initdoctors(){
        /*doctorList.clear();
        Doctor doctor1=new Doctor("医生甲","科室A");
        Doctor doctor2=new Doctor("医生乙","科室B");
        Doctor doctor3=new Doctor("医生丙","科室C");
        Doctor doctor4=new Doctor("医生丁","科室D");
        Doctor doctor0=new Doctor("","");
        doctorList.add(doctor1);
        doctorList.add(doctor2);
        doctorList.add(doctor3);
        doctorList.add(doctor4);
        while(doctorList.size()<10)
            doctorList.add(doctor0);*/

    }
}
