package com.example.a111.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    //components of activity_register
    private EditText userName;
    private EditText phoneNumView;
    private EditText passwordView;
    private EditText confirmpasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * hide status bar
         */
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_register);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Button registed=(Button) findViewById(R.id.sign_up_button);
        registed.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().equals(""))
                    Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                else if (phoneNumView.getText().toString().equals(""))
                    Toast.makeText(RegisterActivity.this,"联系方式不能为空",Toast.LENGTH_SHORT).show();
                else if (passwordView.getText().toString().equals(""))
                    Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                else if (!passwordView.getText().toString().equals(confirmpasswordView.getText().toString()))
                    Toast.makeText(RegisterActivity.this,"两次密码不一致"+"1:"+passwordView.getText().toString()+"2:"+confirmpasswordView.getText().toString(),Toast.LENGTH_SHORT).show();
                else
                    register();
            }
        });
        /**
         * init components
         */
        userName = (EditText) findViewById(R.id.editText);
        phoneNumView = (EditText) findViewById(R.id.editText2);
        passwordView = (EditText) findViewById(R.id.editText4);
        confirmpasswordView = (EditText) findViewById(R.id.editText5);
        /**
         * set broadcastReceiver
         * registe broadcast
         */
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver() ;
        IntentFilter intentFilter = new IntentFilter("REGISTER") ;
        localBroadcastManager.registerReceiver( broadcastReceiver , intentFilter );
    }

    /**
     * creat new service and set param
     */
    private void register(){
        Intent intent = new Intent();
        intent.setAction("intent_service");
        intent.setPackage(getPackageName());
        intent.putExtra("param",2);
        intent.putExtra("userName", userName.getText().toString());
        intent.putExtra("phonenum", phoneNumView.getText().toString());
        intent.putExtra("password", passwordView.getText().toString());
        startService(intent);

    }

    /**
     * get Broadcast and manipulate gui
     */
    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int code = intent.getIntExtra("code", -1);
            if(code != -1){
                Log.i("RegisterActivity","register");
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                //TODO: write to database
                RegisterActivity.this.setResult(RESULT_OK, new Intent().putExtra("code", code));
                RegisterActivity.this.finish();
            }
            else{
                Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * canceled register
     */
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}

