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
 * Created by 111 on 2017/9/1.
 */

public class ChatFrag extends Fragment{
    private View view;
    private List<Doctor> doctorList=new ArrayList<>();

    private DoctorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.content_chat,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initdoctors();
        RecyclerView recyclerView =(RecyclerView)view.findViewById(R.id.chat_recycle);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new DoctorAdapter(doctorList);
        recyclerView.setAdapter(adapter);
    }

    private void initdoctors(){
        doctorList.clear();
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
            doctorList.add(doctor0);

    }
}
