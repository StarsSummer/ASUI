package com.WallE.TCMK.UI.UIActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.WallE.TCMK.UI.Base.BasicActivity;
import com.WallE.TCMK.UI.SettingFragments.SetAdviceFrag;
import com.WallE.TCMK.UI.SettingFragments.SetDoctorFrag;
import com.WallE.TCMK.UI.SettingFragments.SetPathFrag;
import com.WallE.TCMK.UI.SettingFragments.SetSelfFrag;
import com.WallE.TCMK.R;

/**
 * Created by 111 on 2017/9/7.
 */

public class SetsetActivity extends BasicActivity {
    private TextView bartitle;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_setting);
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
                fm.beginTransaction().replace(R.id.setting_setting,new SetDoctorFrag(),"t2").commit();
                break;
            case "advice":
                bartitle.setText("意见反馈");
                fm.beginTransaction().replace(R.id.setting_setting,new SetAdviceFrag(),"t3").commit();
                break;
        }
    }
}
