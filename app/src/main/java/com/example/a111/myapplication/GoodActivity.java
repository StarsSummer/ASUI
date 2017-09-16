package com.example.a111.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by 111 on 2017/9/12.
 */

public class GoodActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.good_introduce);
        WebView webview = (WebView) findViewById(R.id.good_web);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        Intent getfoodno =getIntent();
        String link= getfoodno.getStringExtra("link");
        webview.loadUrl(link);
    }

}
