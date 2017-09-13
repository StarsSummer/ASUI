package com.example.a111.myapplication;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import POJO.PersonInfo;
import testjson.HttpClient;

/**
 * Created by 111 on 2017/9/7.
 */

public class SetSelfFrag extends Fragment {
    /**
     * components of setSelfFrag
     */
    private View view;
    private EditText nickNameView;
    private EditText sexView;
    private EditText birthDateView;
    private EditText emailView;
    private ImageView iconView;
    /**
     * status of request Activity
     */
    private static final int REQUEST_ALBUM = 0;
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_CUT = 2;

    private Uri imageUri;
    private Uri cutImageUri;

    private PersonInfo personInfo;

    private boolean isChange = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.setting_self,container,false);
        /**
         * init components of setselffrag
         */
        nickNameView = (EditText)view.findViewById(R.id.self_name_edit);
        sexView = (EditText)view.findViewById(R.id.self_sex_edit);
        birthDateView = (EditText)view.findViewById(R.id.self_year_edit);
        emailView = (EditText)view.findViewById(R.id.self_email_edit);
        iconView = (ImageView)view.findViewById(R.id.setting_headimg);
        Button getIconButton = (Button)view.findViewById(R.id.change_icon);
        getIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });
        /**
         * register broadcastReceiver to localBroadCast
         */
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver() ;
        IntentFilter intentFilter = new IntentFilter("Query_List") ;
        localBroadcastManager.registerReceiver( broadcastReceiver , intentFilter );
        /**
         * start getPersonInfo device
         */
        getPersonInfo();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * request user select icon getting method
     */
    private void showListDialog() {
        final String[] items = { "从相册获取","从照相机获取",};
        AlertDialog.Builder listDialog = new AlertDialog.Builder(this.getActivity());
        listDialog.setTitle("选择获取照片的方式");
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
                         * create a intent for camera
                         */
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        /**
                         * point out uri store photo
                         */
                        imageUri = createUri(getActivity().getExternalCacheDir(), "icon_photo");
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(cameraIntent, REQUEST_CAMERA);
                        break;
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
                    Uri uri = data.getData();
                    cutImageUri = createUri(getActivity().getExternalCacheDir(), "icon");
                    cutImageWithSquare(uri, cutImageUri);
                    break;
                case REQUEST_CAMERA:
                    cutImageUri = createUri(getActivity().getExternalCacheDir(), "icon");
                    cutImageWithSquare(imageUri, cutImageUri);
                    break;
                case REQUEST_CUT:
                    iconView.setImageURI(cutImageUri);
                    /**
                     * change personInfo
                     */
                    try {
                        personInfo.setIcon(getBytes(getContext().getContentResolver().openInputStream(cutImageUri)));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /**
                     * changed image
                     */
                    isChange = true;
                    break;
                default:
                    break;
            }

    }

    /**
     * create uri with photoname
     * @param photoName name of photo
     * @return file Uri of photo
     */
    public Uri createUri(File parentPath, String photoName){
        Uri uri;
        File outputImage =new File(parentPath, photoName);
        if (!outputImage.getParentFile().exists())
            outputImage.getParentFile().mkdirs();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            uri = FileProvider.getUriForFile(getActivity(),"com.example.a111.myapplication.fileprovider",outputImage);
        }else {
            uri = Uri.fromFile(outputImage);
        }
        return uri;
    }

    /**
     * cut image with square
     * @param originUri image of cut
     * @param copeUri image is cutting
     */
    private void cutImageWithSquare(Uri originUri, Uri copeUri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(originUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, copeUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_CUT);
    }

    /**
     * convert inputStream to byte
     * @param inputStream inputsteam
     * @return bytearray
     * @throws IOException
     */

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        /**
         * buffer of inputsteam to bytebuffer
         */
        byte[] buffer = new byte[1024];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    /**
     * get personinfo from server
     */
    public void getPersonInfo(){
        String hql = "from PersonInfo as p where p.code = "+ HttpClient.getUserCode();
        Intent intent = new Intent();
        intent.setAction("intent_service");
        intent.setPackage(getActivity().getPackageName());
        intent.putExtra("param",7);
        intent.putExtra("hql", hql);
        intent.putExtra("className", "POJO.PersonInfo");
        getActivity().startService(intent);
    }

    /**
     * set Personinfo
     */
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            /**
             * get PersonInfo
             */
            List<PersonInfo> list = (List<PersonInfo>)intent.getSerializableExtra("List");
            if(list.isEmpty() != true) {
                personInfo = list.get(0);
                /**
                 * set to GUI
                 */
                nickNameView.setText(personInfo.getNickname());
                sexView.setText(personInfo.getSex());
                if(personInfo.getBirthDate() != null){
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    birthDateView.setText(sdf.format(personInfo.getBirthDate()));
                }
                emailView.setText(personInfo.getEmail());
                if (personInfo.getIcon() != null)
                    iconView.setImageBitmap(BitmapFactory.decodeByteArray(personInfo.getIcon(), 0, personInfo.getIcon().length));
            }else{
                personInfo = new PersonInfo();
            }
        }
    }

    /**
     * update personInfo to server
     */
    private void updatePersonInfo(){
        Intent intent = new Intent();
        intent.setAction("intent_service");
        intent.setPackage(getActivity().getPackageName());
        intent.putExtra("param",8);
        intent.putExtra("Object", personInfo);
        getActivity().startService(intent);
    }

    /**
     * juaged personinfo is change
     */
    private void judgedChange(){
        if(!(nickNameView.getText().toString().equals(personInfo.getNickname()))) {
            isChange = true;
            personInfo.setNickname(nickNameView.getText().toString());
        }
        if(!sexView.getText().toString().equals(personInfo.getSex())){
            isChange = true;
            personInfo.setSex(sexView.getText().toString());
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(birthDateView.getText().toString());
            if(!date.equals(personInfo.getBirthDate())){
                isChange = true;
                personInfo.setBirthDate(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            birthDateView.setText(dateFormat.format(new Date()));
            Toast.makeText(getContext(),"格式错误",Toast.LENGTH_SHORT);
        }
        if(!emailView.getText().toString().equals(personInfo.getEmail())){
            isChange = true;
            personInfo.setEmail(emailView.getText().toString());
        }
    }
    @Override
    public void onDestroyView() {
        judgedChange();
        if(isChange)
            updatePersonInfo();
        Log.i("self",Boolean.toString(isChange));
        super.onDestroyView();
    }
}
