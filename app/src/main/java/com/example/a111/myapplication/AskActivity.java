package com.example.a111.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 111 on 2017/9/3.
 */

public class AskActivity extends AppCompatActivity {
    private List<Msg> msgList = new ArrayList<>();
    private EditText input;
    private Button send;
    private RecyclerView msgRecycler;
    private MsgAdapter adapter;
    private TextView bartitle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
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
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String content=input.getText().toString();
                if(!"".equals(content)){
                    Msg msg =new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecycler.scrollToPosition(msgList.size()-1);
                    input.setText("");
                }
            }
        });
    }

    private void initmsgs(){
        Msg msg1 =new Msg("你好，这里是专业医生为你解惑。",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
    }
}