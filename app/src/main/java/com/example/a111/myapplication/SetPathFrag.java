package com.example.a111.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.MemoryFile;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 111 on 2017/9/8.
 */

public class SetPathFrag extends Fragment {

    private View view;
    private Button chooseFile;
    private TextView fileDile;
    private String curPath= "/storage";
    private String finalpath="/storage";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.setting_path,container,false);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Toast.makeText(getActivity(),Environment.getExternalStorageDirectory().getAbsolutePath(), Toast.LENGTH_SHORT).show();
        chooseFile=(Button)view.findViewById(R.id.btn_filechoose);
        fileDile=(TextView)view.findViewById(R.id.text_filechoose);
        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCurDir(curPath);
            }
        });

    }

    public void openCurDir(String curPath){
        File f = new File(curPath);
        File[] fs =f.listFiles();
        final List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
        if (!curPath.equals("/storage")){
            Map<String,Object> map1 = new HashMap<String,Object>();
            map1.put("name","返回上一级目录");
            map1.put("image",R.drawable.ic_path_back);
            map1.put("path",f.getParent());
            map1.put("isDire",true);
            mapList.add(map1);
        }
        if (f!=null){
            int i;
            for(i=0;i<fs.length;i++){
                Map<String,Object> mapf = new HashMap<String, Object>();
                mapf.put("name",fs[i].getName());
                mapf.put("image",(fs[i].isDirectory())?R.drawable.ic_path_folder:R.drawable.ic_path_file);
                mapf.put("path",fs[i].getPath());
                mapf.put("isDire",fs[i].isDirectory());
                mapList.add(mapf);
            }
            Map<String,Object> mape = new HashMap<String, Object>();
            mape.put("name","选择该目录");
            mape.put("image",R.drawable.ic_path_here);
            mape.put("path","");
            mape.put("isDire",false);
            mapList.add(mape);
        }

        SimpleAdapter adapter = new SimpleAdapter(getActivity(),mapList,R.layout.file_adapter,
                new String[]{"name","image"},new int[]{R.id.file_name,R.id.file_image});
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ((Boolean) mapList.get(which).get("isDire")){
                    finalpath=(String)mapList.get(which).get("path");
                    openCurDir((String)mapList.get(which).get("path"));
                }else {
                    if (mapList.get(which).get("name")=="选择该目录")
                        fileDile.setText(finalpath);
                }
            }
        });
        builder.show();
    }


}