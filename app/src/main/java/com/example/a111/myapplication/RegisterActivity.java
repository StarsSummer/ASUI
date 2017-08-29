package com.example.a111.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;

import pojo.User;
import testjson.HttpClient;
import testjson.SignUpException;

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
    private TextView userNameView;
    private RadioGroup sex;
    private RadioButton male;
    private RadioButton female;
    private TextView emailView;
    private TextView phoneNumView;
    private TextView passwordView;
    //child thread
    private UserRegisterTask newTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_register);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Button registed=(Button) findViewById(R.id.sign_up_button);
        registed.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reged =new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(reged);
            }
        });
        // init components
        phoneNumView = (TextView) findViewById(R.id.password);
        passwordView = (TextView) findViewById(R.id.password);

    }
    private void register(){

    }

    public class UserRegisterTask extends AsyncTask<User, Void, Character> {

        private String phonenum;
        private String password;

        //http client for sign in
        private HttpClient httpClient ;
        UserRegisterTask(String phoneNum, String password) {
            this.phonenum = phoneNum;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            httpClient = new HttpClient();
        }

        @Override
        protected Character doInBackground(User ... params) {
            // TODO: attempt authentication against a network service.
            char status = 2;
            try {
                status = httpClient.normalUserSignUp(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SignUpException e) {
                e.printStackTrace();
            }

          /*  for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }*/

            // TODO: register the new account here.
            return new Character(status);
        }

        @Override
        protected void onPostExecute(Character registerStatus) {
            newTask = null;
           // showProgress(false);

            if (registerStatus != -1) {
                // Show a progress spinner, and kick off a background task to
                // perform the user login attempt.
                Intent reged =new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(reged);
                finish();
            } else {
                //mPasswordView.setError(getString(R.string.error_incorrect_password));
                //mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            newTask = null;
            //showProgress(false);
        }
    }
}

