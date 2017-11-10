package com.WallE.TCMK.UI.MainFragments;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.WallE.TCMK.HTTPClientService.HTTPClientService;
import com.WallE.TCMK.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 111 on 2017/8/2.
 */

public class PulseFrag extends Fragment{

    private View view;
    private ImageView subt;
    private TextView result;

    private static final int REQUEST_ALBUM = 0;

    private ImageView picture;
    private Uri uri = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.content_pulse,container,false);
        /**
         * registe broadcast
         */
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver() ;
        IntentFilter intentFilter = new IntentFilter(HTTPClientService.PULSE_RESULT) ;
        localBroadcastManager.registerReceiver(broadcastReceiver , intentFilter);
        /**
         * init component
         */
        picture=(ImageView)view.findViewById(R.id.imageView7);
        subt=(ImageView)view.findViewById(R.id.imageView8);
        result = (TextView)view.findViewById(R.id.textView17);

        subt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uri != null) {
                    Intent intent = new Intent();
                    intent.setAction("intent_service");
                    intent.setPackage(getActivity().getPackageName());
                    intent.putExtra("param", HTTPClientService.PULSE_JUDGE);
                    intent.putExtra("File", uri.getPath());
                    getActivity().startService(intent);
                    Toast.makeText(getActivity(), "数据已保存，解析中...", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), "无数据", Toast.LENGTH_LONG).show();
                }
            }
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });
        return view;
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String resultString = intent.getStringExtra("result");
            result.setText(resultString);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        subt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"已获得数据，正在解析...",Toast.LENGTH_LONG).show();
            }
        });
    }
    /**
     * request user select icon getting method
     */
    private void showListDialog() {
        final String[] items = { "从相册获取","直接检测",};
        AlertDialog.Builder listDialog = new AlertDialog.Builder(this.getActivity());
        listDialog.setTitle("选择获取数据的方式");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        /**
                         * create a intent for album
                         */
                        Intent albumIntent = new Intent(Intent.ACTION_PICK);
                        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(albumIntent, REQUEST_ALBUM);
                        break;
                    case 1:
                        /**
                         * TODO: connect wearable service
                         */
                    default:
                        break;
                }
            }
        });
        listDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == getActivity().RESULT_OK)
            switch(requestCode){
                case REQUEST_ALBUM:
                    uri = data.getData();
                    picture.setImageURI(uri);
                    break;
                default:
                    break;
            }

    }

}
