package com.example.a111.myapplication;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.images.ImageManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Permission;
import java.util.jar.Manifest;

/**
 * Created by 111 on 2017/8/2.
 */

public class TongueFrag extends Fragment{

    private View view;
    private ImageView subt;

    public static final int TAKE_PHOTO=1;
    public static final int CROP_BIG_PICTURE = 2;
    private ImageView picture;
    private Uri imageUri;
    Uri copeUri;
    private File file;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.content_tongue,container,false);
        picture=(ImageView)view.findViewById(R.id.imageView5);
        subt=(ImageView)view.findViewById(R.id.imageView6);

        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        subt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("intent_service");
                intent.setPackage(getActivity().getPackageName());
                intent.putExtra("param",6);
                intent.putExtra("File",copeUri.getPath());
                getActivity().startService(intent);
                Toast.makeText(getActivity(),"图片已保存，解析中...",Toast.LENGTH_LONG).show();
            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage =new File(getActivity().getExternalCacheDir(),"tongue_image.jpg");
                file = outputImage;
                try {
                    if (!outputImage.getParentFile().exists())
                        outputImage.getParentFile().mkdirs();
                }catch (Exception e){
                    e.printStackTrace();
                }
                Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    imageUri=FileProvider.getUriForFile(getActivity(),"com.example.a111.myapplication.fileprovider",outputImage);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                }else {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(outputImage));
                    imageUri = Uri.fromFile(outputImage);
                }
                try{

                    AlphaAnimation anim_alpha = new AlphaAnimation(0,1);
                    anim_alpha.setDuration(2000);
                    v.setAnimation(anim_alpha);
                    startActivityForResult(intent,TAKE_PHOTO);
                }catch (ActivityNotFoundException an){
                    an.printStackTrace();
                }
            }
        });
    }
    private void cropImageUri(Uri originUri, Uri copeUri, int outputX, int outputY, int requestCode){

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.setDataAndType(originUri, "image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 1);

        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", outputX);

        intent.putExtra("outputY", outputY);

        intent.putExtra("scale", true);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, copeUri);

        intent.putExtra("return-data", false);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        intent.putExtra("noFaceDetection", true); // no face detection
        Log.i("TongueFrag",imageUri.toString());
        startActivityForResult(intent, requestCode);

    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==getActivity().RESULT_OK){
                    File outputImage =new File(getActivity().getExternalCacheDir(),"tongue_image_cope.jpg");
                    try {
                        if (!outputImage.getParentFile().exists())
                            outputImage.getParentFile().mkdirs();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    copeUri = Uri.fromFile(outputImage);
                    //Uri copeUri=FileProvider.getUriForFile(getActivity(),"com.example.a111.myapplication.fileprovider",outputImage);
                    //getActivity().grantUriPermission(getActivity().getPackageName(), copeUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    cropImageUri(imageUri,copeUri, 480, 480, CROP_BIG_PICTURE);

                }
                break;
            case CROP_BIG_PICTURE:
                if(resultCode==getActivity().RESULT_OK){

                       // Bitmap bitmap =BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                    Log.i("TongueFrag","crop");
                    picture.setImageURI(copeUri);


                }
                break;
            default:
                break;
        }
    }

}
