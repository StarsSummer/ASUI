package com.example.a111.myapplication;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import testjson.Connection;
import testjson.HttpClient;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout dl;
    private FrameLayout fl;
    private LinearLayout ll;
    private FragmentManager fm;
    private List<Fragment> fms;
    private TextView bartitle;

    private List<Title> titles=new ArrayList<Title>();
    private ArrayAdapter<String> adapter;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences("setting", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if(!settings.getBoolean("client_status", false)) {
            editor.putBoolean("client_status", true);
            editor.putInt("code", HttpClient.getUserCode());
            editor.commit();
        }else{
            HttpClient.setUserCode(settings.getInt("code", -1));
        }

        Intent intent = new Intent();
        intent.setAction("intent_service");
        intent.setPackage(getPackageName());
        intent.putExtra("param",3);
        startService(intent);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_main);

        initTitles();
        TitleAdapter adapter = new TitleAdapter(MainActivity.this,R.layout.item_list,titles);
        final ListView listview=(ListView) findViewById(R.id.list_view);
        listview.setAdapter(adapter);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        bartitle=(TextView) findViewById(R.id.bar_title);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);

        dl = (DrawerLayout) findViewById(R.id.activity_main);
        fl = (FrameLayout) findViewById(R.id.Content);
        ll = (LinearLayout) findViewById(R.id.drawer);
        fms = new ArrayList<Fragment>();
        fm =super.getSupportFragmentManager();


        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, dl,
                toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                /**
                 * quit login status
                 */
                RelativeLayout quit =(RelativeLayout)findViewById(R.id.quit_main);
                quit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences settings = getSharedPreferences("setting", MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("client_status", false);
                        editor.putInt("code", -1);
                        editor.commit();
                        MainActivity.this.finish();
                    }
                });
                invalidateOptionsMenu();
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        abdt.syncState();
        dl.addDrawerListener(abdt);

        initFragments();
        fm.beginTransaction().replace(R.id.Content,fms.get(0),"t0").commit();
        bartitle.setText("个人中心");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Title title=titles.get(position);

                AlphaAnimation anim_alpha = new AlphaAnimation(1,0);
                anim_alpha.setDuration(300);
                anim_alpha.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dl.closeDrawer(ll);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.setAnimation(anim_alpha);
                switch (position){
                    case 0:
                        fm.beginTransaction().replace(R.id.Content,fms.get(0),"t0").commit();
                        bartitle.setText("个人中心");
                        break;
                    case 1:
                        fm.beginTransaction().replace(R.id.Content,fms.get(1),"t1").commit();
                        bartitle.setText("察舌观色");
                        break;
                    case 2:
                        fm.beginTransaction().replace(R.id.Content,fms.get(2),"t2").commit();
                        bartitle.setText("辨晓经脉");
                        break;
                    case 3:
                        fm.beginTransaction().replace(R.id.Content,fms.get(3),"t3").commit();
                        bartitle.setText("听息断形");
                        break;
                    case 4:
                        fm.beginTransaction().replace(R.id.Content,fms.get(4),"t4").commit();
                        bartitle.setText("方案推荐");
                        break;
                    /*
                    case 5:
                        bartitle.setText("分享交流");
                        break;*/
                    case 6:
                        fm.beginTransaction().replace(R.id.Content,fms.get(5),"t5").commit();
                        bartitle.setText("循症疑难");
                        break;
                }
            }
        });


    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int a = intent.getIntExtra("code",-1);
        Log.i("Mainactivity",Integer.toString(a));
        setIntent(intent);//设置新的intent

    }

    private void initTitles(){

        Title center=new Title("个人中心",R.drawable.ic_center,0XFF6bc08b);
        Title tongue=new Title("察舌观色",R.drawable.ic_tongue,0XFF3eb279);
        Title heart=new Title("辨晓经脉",R.drawable.ic_heart,0XFF6bc08b);
        Title voice=new Title("听息短形",R.drawable.ic_voice,0XFF3eb279);
        Title food=new Title("方案推荐",R.drawable.ic_food,0XFF6bc08b);
        Title share=new Title("分享交流",R.drawable.ic_share,0XFF3eb279);
        Title ask=new Title("循症疑难",R.drawable.ic_ask,0XFF6bc08b);
        titles.add(center);
        titles.add(tongue);
        titles.add(heart);
        titles.add(voice);
        titles.add(food);
        titles.add(share);
        titles.add(ask);

    }

    private void initFragments(){

        CenterFrag centerFragment =new CenterFrag();
        fms.add(centerFragment);
        TongueFrag tongueFragment=new TongueFrag();
        fms.add(tongueFragment);
        HeartFrag heartFragment =new HeartFrag();
        fms.add(heartFragment);
        VoiceFrag voicefragment=new VoiceFrag();
        fms.add(voicefragment);
        FoodFrag foodFragment=new FoodFrag();
        fms.add(foodFragment);
        ChatFrag chatFragment=new ChatFrag();
        fms.add(chatFragment);
        //fm.beginTransaction().replace(R.id.Content,fms.get(0),"t0").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener=new Toolbar.OnMenuItemClickListener(){
        @Override
        public boolean onMenuItemClick(MenuItem menuItem){
            switch (menuItem.getItemId()){
                case R.id.settings:
                    Intent set =new Intent(MainActivity.this,SettingActivity.class);
                    startActivity(set);
            }
            return true;
        }
    };
}
