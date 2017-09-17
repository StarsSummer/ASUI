package com.WallE.TCMK.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.WallE.TCMK.R;

import java.util.List;

/**
 * Created by 111 on 2017/9/3.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<Msg> msgList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftlayout;
        LinearLayout rightlayout;
        TextView leftmsg;
        TextView rightmsg;
        public ViewHolder(View view){
            super(view);
            leftlayout=(LinearLayout)view.findViewById(R.id.left_pop);
            rightlayout=(LinearLayout)view.findViewById(R.id.right_pop);
            leftmsg=(TextView)view.findViewById(R.id.left_msg);
            rightmsg=(TextView)view.findViewById(R.id.right_msg);
        }
    }

    public MsgAdapter(List<Msg> msgLists){
        msgList=msgLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MsgAdapter.ViewHolder holder, int position) {
        Msg msg =msgList.get(position);
        if (msg.getMsgType()==Msg.TYPE_RECEIVED){
            holder.leftlayout.setVisibility(View.VISIBLE);
            holder.rightlayout.setVisibility(View.GONE);
            holder.leftmsg.setText(msg.getMsgContent());
        }else if (msg.getMsgType()==Msg.TYPE_SENT){
            holder.rightlayout.setVisibility(View.VISIBLE);
            holder.leftlayout.setVisibility(View.GONE);
            holder.rightmsg.setText(msg.getMsgContent());
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
