package sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import pojo.TonguePhoto;

/**
 * Created by Jinffee on 2017/8/30.
 */

public class MySql extends SQLiteOpenHelper {
    private static int version = 1;
    private static String databaseName = "test.db";
    //table names
    private static String[] Table_Name = {"User" , "PersonalInfo", "DoctorInformation", "HealthInfo", "TonguePhoto"};
    private static String[][] Column = {{"code INTEGER primary key","userAccount char(20)","userType char(20)","password char(20)"}
                                        ,{"code INTEGER primary key","sex char" ,"email char(20)","icon Blob","birthDate DateTime","nickname char(20)"}
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
            sqls[i] = sqls[i] + " )";
        }
        return sqls;
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

}
