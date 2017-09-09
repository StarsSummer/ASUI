package sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import POJO.DoctorInformation;
import POJO.User;

/**
 * Created by Jinffee on 2017/9/9.
 */

public class MySQLDBManager {
    private static MySQLHelper helper;

    /**
     * get MySqlHelper instance
     * @param context activity context
     * @return MySqlHelper instance
     */
    public static MySQLHelper getInstance(Context context){
        if (helper == null){
            helper = new MySQLHelper(context);
        }
        return helper;
    }

    /**
     * select cursor for sql
     * @param db database used
     * @param sql selection sql
     * @param selectionArgs arguments of sql
     * @return cursor of result
     */
    public static Cursor query(SQLiteDatabase db, String sql, String[] selectionArgs){
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(sql, selectionArgs);
        }
        return cursor;
    }
    public void cursorToList(Cursor cursor, Class<?> clazz){
        List<Object> list = new ArrayList<>();
        if(User.class.equals(clazz)) {
            while (cursor.moveToNext()) {
                int code = cursor.getInt(cursor.getColumnIndex(Constant.USER_CODE));
                String userAccount = cursor.getString(cursor.getColumnIndex(Constant.USER_USERACCOUNT));
                String userType = cursor.getString(cursor.getColumnIndex(Constant.USER_USERTYPE));
                String password = cursor.getString(cursor.getColumnIndex(Constant.USER_PASSWORD));
                User user = new User(code, userAccount, userType, password);
                list.add(user);
            }
        }else if(DoctorInformation.class.equals(clazz)){
            while(cursor.moveToNext()){
                int code = cursor.getInt(cursor.getColumnIndex(Constant.DOCTORINFO_CODE));

            }

        }

    }
}
