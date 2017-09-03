package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import pojo.DoctorInformation;
import pojo.HealthInfo;
import pojo.PersonInfo;
import pojo.TonguePhoto;
import pojo.User;

/**
 * Created by Jinffee on 2017/8/30.
 */

public class MySql extends SQLiteOpenHelper {
    private static int version = 1;
    private static String databaseName = "test.db";
    //table names
    private static String[] Table_Name = {"User" , "PersonalInfo", "DoctorInformation", "HealthInfo", "TonguePhoto"};
    private static String[][] Column = {{"code INTEGER primary key","userAccount char(20)","userType char(20)","password char(20)"}
                                        ,{"code INTEGER primary key","sex char" ,"email char(20)","icon Blob","birthDate Long","nickname char(20)"}
                                        ,{"code INTEGER primary key","dep char(20)"}
                                        ,{"code INTEGER primary key","date DateTime primary key","bloodHigh float","bloodLow float","weight float","weight height"}
                                        ,{"code INTEGER primary key","uploadDate DateTime primary key","path char(20)"}};
    public MySql(Context context) {
        super(context, databaseName, null, version);
    }

    private String[] createSQL(){
        String[] sqls = new String[Table_Name.length];
        for(int i = 0; i <Column.length; i++) {
            sqls[i] = "Creat Table " + Table_Name[0] + " (" ;
            for (int j = 0; j < Column[i].length; j++)
                sqls[i] = sqls[i] + Column[i][j] + ",";
            sqls[i] = sqls[i].substring(0, sqls[i].length()-1);
            sqls[i] = sqls[i] + ")";
        }
        return sqls;
    }
    public void printStr(String[] sqls){
        for(String sql : sqls){
            System.out.println(sql);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String[] sqls = createSQL();
        for(int i = 0; i < Table_Name.length; i++)
            db.execSQL(sqls[i]);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long insertUser(String phonenum,String password,int code){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userAccount", phonenum);
        values.put("password", password);
        values.put("userType", "normal");
        values.put("code",code);
        return db.insert("User",null,values);
    }

    public long insertPersonInfo(PersonInfo pojo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("code", pojo.getCode());
        values.put("sex", pojo.getSex());
        values.put("email", pojo.getEmail());
        values.put("icon", pojo.getIcon());
        values.put("birthDate", pojo.getBirthDate().getTime());
        values.put("nickname",pojo.getNickname());
        return db.insert("PersonInfo",null,values);
    }
    public long insertDoctorInformation(DoctorInformation pojo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("code", pojo.getCode());
        values.put("dep", pojo.getDep());
        return db.insert("DoctorInfomation",null,values);
    }
    public long insertHealthInfo(HealthInfo pojo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("code", pojo.getId().getCode());
        values.put("date", pojo.getId().getDate().getTime());
        values.put("bloodHigh", pojo.getBloodHigh().doubleValue());
        values.put("bloodLow", pojo.getBloodLow().doubleValue());
        values.put("weight",pojo.getWeight().doubleValue());
        values.put("height", pojo.getHeight().doubleValue());
        return db.insert("HealthInfo", null, values);
    }


}
