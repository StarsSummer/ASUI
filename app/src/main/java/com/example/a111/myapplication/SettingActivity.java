package com.example.a111.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class SettingActivity extends AppCompatActivity  {
    private TextView bartitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        setContentView(R.layout.activity_setting);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setBackgroundResource(R.color.black_overlay);
        bartitle=(TextView) findViewById(R.id.bar_title);
        bartitle.setText("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });

        LinearLayout setmyself =(LinearLayout) findViewById(R.id.set_myself);
        setmyself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data="myself";
                Intent setset =new Intent(SettingActivity.this,SetsetActivity.class);
                setset.putExtra("extra_data",data);
                startActivity(setset);
                overridePendingTransition(R.anim.slide_in_left,R.anim.fade_in_right);
            }
        });
        LinearLayout setpath =(LinearLayout)findViewById(R.id.set_path);
        setpath.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String data="path";
                Intent setset =new Intent(SettingActivity.this,SetsetActivity.class);
                setset.putExtra("extra_data",data);
                startActivity(setset);
                overridePendingTransition(R.anim.slide_in_left,R.anim.fade_in_right);
            }
        });
        LinearLayout setdoctor =(LinearLayout)findViewById(R.id.set_doctor);
        setdoctor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String data="doctor";
                Intent setset =new Intent(SettingActivity.this,SetsetActivity.class);
                setset.putExtra("extra_data",data);
                startActivity(setset);
                overridePendingTransition(R.anim.slide_in_left,R.anim.fade_in_right);
            }
        });
        LinearLayout setadvice =(LinearLayout)findViewById(R.id.set_advice);
        setadvice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String data="advice";
                Intent setset =new Intent(SettingActivity.this,SetsetActivity.class);
                setset.putExtra("extra_data",data);
                startActivity(setset);
                overridePendingTransition(R.anim.slide_in_left,R.anim.fade_in_right);
            }
        });
        LinearLayout setupgrade =(LinearLayout) findViewById(R.id.set_update);
        setupgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "已是最新版本", Toast.LENGTH_SHORT).show();
            }
        });


    }


}

