package com.example.a111.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.images.ImageManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.Manifest;

/**
 * Created by 111 on 2017/8/2.
 */

public class TongueFrag extends Fragment{

    private View view;
    Activity mactivity;
    public static final int TAKE_PHOTO=1;
    private Uri imageUri;
    private ImageView photo;
    private ImageView subt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.content_tongue,container,false);
        photo=(ImageView)view.findViewById(R.id.imageView5);
        subt=(ImageView)view.findViewById(R.id.imageView6);
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File tonimg =new File(Environment.getExternalStorageDirectory(),"tongue.jpg");
                try{
                    if(tonimg.exists()){
                        tonimg.delete();
                    }
                    tonimg.createNewFile();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(),"启动相机...",Toast.LENGTH_LONG).show();
                imageUri=Uri.fromFile(tonimg);
                Intent intent =new Intent("android.media.action.IMAGE_CAPTURE");
                //getActivity().startActivity(intent);
            }
        });
        subt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"图片已保存，解析中...",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==Activity.RESULT_OK){
            switch (requestCode){
                case TAKE_PHOTO:
                    Bitmap bitmap=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/tongue.jpg");
                    bitmap.recycle();
                    break;
                default:
                    break;
            }
        }
    }

}
