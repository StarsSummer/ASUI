package com.example.a111.myapplication;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pojo.Doctor;
import pojo.DoctorInformation;

/**
 * Created by 111 on 2017/8/25.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    private Context mContext;
    private List<Doctor> doctorList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View doctorView;
        TextView doctorstat;
        TextView doctorname;
        public ViewHolder(View view) {
            super(view);
            doctorView=view;
            doctorstat=(TextView)view.findViewById(R.id.id_status);
            doctorname=(TextView)view.findViewById(R.id.id_doctors);
        }
    }

    public DoctorAdapter(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.chat_list,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.doctorView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int positon = holder.getAdapterPosition();
                Doctor doctor = doctorList.get(positon);
                //TODO: 获取doctor类所有信息
                //Toast.makeText(v.getContext(),"向"+doctor.getName()+"咨询",Toast.LENGTH_SHORT).show();

                Intent asking = new Intent(mContext,AskActivity.class);
                mContext.startActivity(asking);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Doctor doctor=doctorList.get(position);
        //holder.doctorname.setText(doctor.getName());
        holder.doctorstat.setText(doctor.getDep());
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }
}
