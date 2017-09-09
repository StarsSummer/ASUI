package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import POJO.DoctorInformation;
import POJO.HealthInfo;
import POJO.PersonInfo;

/**
 * Created by Jinffee on 2017/8/30.
 */

public class MySQLHelper extends SQLiteOpenHelper {
    //table names
    private static String[] Table_Name = {"User" , "PersonalInfo", "DoctorInformation", "HealthInfo", "TonguePhoto"};
    private static String[][] Column = {{"_id INTEGER primary key","userAccount char(20)","userType char(20)","password char(20)"}
                                        ,{"_id INTEGER primary key","sex char" ,"email char(20)","icon Blob","birthDate Long","nickname char(20)"}
                                        ,{"_id INTEGER primary key","dep char(20)"}
                                        ,{"code INTEGER ","date DateTime","bloodHigh float","bloodLow float","weight float","height float","constraint _id primary key (code,date)"}
                                        ,{"_id INTEGER primary key","uploadDate DateTime primary key","path char(20)"}};

    /**
     * new constructor of helper
     * @param context context of activity
     */
    public MySQLHelper(Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    /**
     * database SQL creation
     * @return SQL of creation
     */
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

    /**
     * create database
     * @param db database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableSQL = "CREATE TABLE " + Constant.TABLE_USER_NAME +
               "("+Constant.USER_CODE + " INTEGER primary key," + Constant.USER_USERACCOUNT+
               " char(20)," + Constant.USER_USERTYPE + " char(10)," + Constant.USER_PASSWORD + " char(20))";
        String createPersonInfoSQL = "CREATE TABLE " + Constant.TABLE_PERSONINFO_NAME +
                "(" + Constant.PERSONINFO_CODE + " INTEGER primary key, " +
                Constant.PERSONINFO_SEX + " char ," + Constant.PERSONINFO_EMAIL + " char(20)," +
                Constant.PERSONINFO_ICON + " Blob," + Constant.PERSONINFO_BIRTHDATE + " Long," +
                Constant.PERSONINFO_NICKNAME + " char(20))";
        String createDoctorInfoSQL = "CREATE TABLE " + Constant.TABLE_DOCTORINFO_NAME +
                "(" + Constant.DOCTORINFO_CODE +" INTEGER primary key," + Constant.DOCTORINFO_DEP + " char(20))";
        String createHealthInfoSQL = "CREATE TABLE " + Constant.TABLE_HEALTHINFO_NAME +
                "(" + Constant.HEALTHINFO_CODE + " INTEGER ," + Constant.HEALTHINFO_DATE + " DateTime," +
                Constant.HEALTHINFO_BLOODHIGH + " float," + Constant.HEALTHINFO_BLOODLOW + " float," +
                Constant.HEALTHINFO_WEIGHT + " float," + Constant.HEALTHINFO_HEIGHT + " float," +
                "constraint " + Constant.HEALTHINFO_ID + " primary key (" + Constant.HEALTHINFO_CODE + "," + Constant.HEALTHINFO_DATE + "))";
        String createMessageSQL = "CREATE TABLE message(" + Constant.MESSAGE_SENDERCODE + " INTEGER," + Constant.MESSAGE_RECEIVERCODE + " INTEGER," +
                Constant.MESSAGE_MESSAGE + " TEXT," + "constraint " + Constant.MESSAGE_ID + " primary key (" + Constant.MESSAGE_SENDERCODE + "," +
                Constant.MESSAGE_RECEIVERCODE + "))";
        try {
            db.beginTransaction();
            db.execSQL(createUserTableSQL);
            db.execSQL(createPersonInfoSQL);
            db.execSQL(createHealthInfoSQL);
            db.execSQL(createDoctorInfoSQL);
            db.execSQL(createMessageSQL);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
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
