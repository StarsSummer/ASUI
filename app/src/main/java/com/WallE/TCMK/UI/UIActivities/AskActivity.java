package com.WallE.TCMK.UI.UIActivities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.WallE.TCMK.UI.Base.BasicActivity;
import com.WallE.TCMK.Utils.Msg;
import com.WallE.TCMK.UI.MainFragments.MsgAdapter;
import com.WallE.TCMK.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by 111 on 2017/9/3.
 */
//For chat
public class AskActivity extends BasicActivity {
    private List<Msg> msgList = new ArrayList<>();
    private EditText input;
    private Button send;
    private RecyclerView msgRecycler;
    private MsgAdapter adapter;
    private TextView bartitle;
    private int docotorCode;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //通过代码的方式动态注册MyBroadcastReceiver
        MyBroadcastReceiver receiver=new MyBroadcastReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("Get_Message");
        //注册receiver
        registerReceiver(receiver, filter);
        setContentView(R.layout.activity_ask);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        bartitle=(TextView) findViewById(R.id.bar_title);
        bartitle.setText("医生");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskActivity.this.finish();
            }
        });

        initmsgs();
        input=(EditText) findViewById(R.id.msg_input);
        send=(Button) findViewById(R.id.msg_send);
        msgRecycler=(RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        msgRecycler.setLayoutManager(layoutManager);
        adapter=new MsgAdapter(msgList);
        msgRecycler.setAdapter(adapter);
        //on click
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String content=input.getText().toString();
                if(!"".equals(content)){
                    Intent intent = new Intent();
                    intent.setAction("intent_service");
                    intent.setPackage(getPackageName());
                    intent.putExtra("param",5);
                    intent.putExtra("message", content);
                    startService(intent);
                    Msg msg =new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecycler.scrollToPosition(msgList.size()-1);
                    input.setText("");
                }
            }
        });
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String content = intent.getStringExtra("Message");
            msgList.add(new Msg(content, Msg.TYPE_RECEIVED));
        }
    }

    private void initmsgs(){
        Msg msg1 =new Msg("你好，这里是专业医生为你解惑。",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
    }

}
