package com.WallE.TCMK.UI.UIActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import com.WallE.TCMK.HTTPClientService.HTTPClientService;
import com.WallE.TCMK.HTTPClientService.HttpClient;
import com.WallE.TCMK.UI.Base.BasicActivity;
import com.WallE.TCMK.UI.MainFragments.CenterFrag;
import com.WallE.TCMK.UI.MainFragments.ChatFrag;
import com.WallE.TCMK.UI.MainFragments.FoodFrag;
import com.WallE.TCMK.UI.MainFragments.HeartFrag;
import com.WallE.TCMK.R;
import com.WallE.TCMK.UI.MainFragments.ShopFrag;
import com.WallE.TCMK.Utils.Title;
import com.WallE.TCMK.Utils.TitleAdapter;
import com.WallE.TCMK.UI.MainFragments.TongueFrag;
import com.WallE.TCMK.UI.MainFragments.VoiceFrag;

public class MainActivity extends BasicActivity {
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
            editor.putInt("code", HTTPClientService.getUserCode());
            editor.commit();
        }else{
            HTTPClientService.setUserCode(settings.getInt("code", -1));
        }

        Intent intent = new Intent();
        intent.setAction("intent_service");
        intent.setPackage(getPackageName());
        intent.putExtra("param",3);
        startService(intent);

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
                        HTTPClientService.setUserCode(-1);
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

                    case 5:
                        fm.beginTransaction().replace(R.id.Content,fms.get(5),"t5").commit();
                        bartitle.setText("健康商城");
                        break;

                    case 6:
                        fm.beginTransaction().replace(R.id.Content,fms.get(6),"t6").commit();
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
        Title shop=new Title("健康商城",R.drawable.ic_shop,0XFF3eb279);
        Title ask=new Title("循症疑难",R.drawable.ic_ask,0XFF6bc08b);
        titles.add(center);
        titles.add(tongue);
        titles.add(heart);
        titles.add(voice);
        titles.add(food);
        titles.add(shop);
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
        ShopFrag shopFragment=new ShopFrag();
        fms.add(shopFragment);
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
