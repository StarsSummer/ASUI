package com.example.a111.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 111 on 2017/9/7.
 */

public class SetsetActivity extends AppCompatActivity {
    private TextView bartitle;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        setContentView(R.layout.setting_setting);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setBackgroundResource(R.color.black_overlay);
        bartitle = (TextView) findViewById(R.id.bar_title);
        bartitle.setText("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetsetActivity.this.finish();
            }
        });

        fm = super.getSupportFragmentManager();

        getSetting();
    }

    protected void getSetting(){
        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");
        switch (data){
            case "myself":
                bartitle.setText("我的资料");
                fm.beginTransaction().replace(R.id.setting_setting,new SetSelfFrag(),"t0").commit();
                break;
            case "path":
                bartitle.setText("存储管理");
                fm.beginTransaction().replace(R.id.setting_setting,new SetPathFrag(),"t1").commit();
                break;
            case "doctor":
                bartitle.setText("医生认证");
                break;
            case "advice":
                bartitle.setText("意见反馈");
                break;
        }
    }
}
