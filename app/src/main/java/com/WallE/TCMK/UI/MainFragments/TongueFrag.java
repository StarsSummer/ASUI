package com.WallE.TCMK.UI.MainFragments;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
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

/**
 * Created by 111 on 2017/8/2.
 */

public class TongueFrag extends Fragment{

    private View view;
    private ImageView subt;
    private TextView result;

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
        /**
         * registe broadcast
         */
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver() ;
        IntentFilter intentFilter = new IntentFilter("Result") ;
        localBroadcastManager.registerReceiver(broadcastReceiver , intentFilter );
        /**
         * init component
         */
        picture=(ImageView)view.findViewById(R.id.imageView5);
        subt=(ImageView)view.findViewById(R.id.imageView6);
        result = (TextView)view.findViewById(R.id.textView12);

        subt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("intent_service");
                intent.setPackage(getActivity().getPackageName());
                intent.putExtra("param", HTTPClientService.TONGUE_JUDGE);
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
                    imageUri=FileProvider.getUriForFile(getActivity(),"com.WallE.TCMK.myapplication.fileprovider",outputImage);
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
        return view;
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String resultString = intent.getStringExtra("result");
            if(resultString.equals("OK"))
                result.setText("正常");
            else if(resultString.equals("black"))
                result.setText("黑色舌");
            else
                result.setText("裂纹舌");
        }

    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


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
                    //Uri copeUri=FileProvider.getUriForFile(getActivity(),"com.WallE.TCMK.myapplication.fileprovider",outputImage);
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
