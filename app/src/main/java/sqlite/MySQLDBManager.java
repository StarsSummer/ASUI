package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import POJO.DoctorInformation;
import POJO.HealthInfo;
import POJO.HealthInfoId;
import POJO.Message;
import POJO.PersonInfo;
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
     *query database to List
     * @param db
     * @param sql
     * @param selectionArgs
     * @param clazz
     * @return
     */

    public static List<?> query(SQLiteDatabase db, String sql, String[] selectionArgs,  Class<?> clazz){
        Cursor cursor = null;
        List<Object> list = new ArrayList<>();
        if(db != null){
            /**
             * select cursor
             */
            cursor = db.rawQuery(sql, selectionArgs);
            /**
             * insert to list
             */
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
                    String dep = cursor.getString(cursor.getColumnIndex(Constant.DOCTORINFO_DEP));
                    DoctorInformation doctorInformation = new DoctorInformation(code, dep);
                    list.add(doctorInformation);
                }
            }
            else if(PersonInfo.class.equals(clazz)){
                while(cursor.moveToNext()){
                    int code = cursor.getInt(cursor.getColumnIndex(Constant.PERSONINFO_CODE));
                    String sex = cursor.getString(cursor.getColumnIndex(Constant.PERSONINFO_SEX));
                    String email = cursor.getString(cursor.getColumnIndex(Constant.PERSONINFO_EMAIL));
                    byte[] icon = cursor.getBlob(cursor.getColumnIndex(Constant.PERSONINFO_ICON));
                    Date birthDate = new Date(cursor.getLong(cursor.getColumnIndex(Constant.PERSONINFO_BIRTHDATE)));
                    String nickname = cursor.getString(cursor.getColumnIndex(Constant.PERSONINFO_NICKNAME));
                    list.add(new PersonInfo(code, sex, email, icon, birthDate, nickname));
                }
            }else if(HealthInfo.class.equals(clazz)){
                while(cursor.moveToNext()){
                    int code = cursor.getInt(cursor.getColumnIndex(Constant.HEALTHINFO_CODE));
                    Date date = new Date(cursor.getLong(cursor.getColumnIndex(Constant.HEALTHINFO_DATE)));
                    BigDecimal bloodHigh = new BigDecimal(cursor.getDouble(cursor.getColumnIndex(Constant.HEALTHINFO_BLOODHIGH)));
                    BigDecimal bloodLow = new BigDecimal(cursor.getDouble(cursor.getColumnIndex(Constant.HEALTHINFO_BLOODLOW)));
                    BigDecimal height = new BigDecimal(cursor.getDouble(cursor.getColumnIndex(Constant.HEALTHINFO_HEIGHT)));
                    BigDecimal weight = new BigDecimal(cursor.getDouble(cursor.getColumnIndex(Constant.HEALTHINFO_WEIGHT)));
                    list.add(new HealthInfo(new HealthInfoId(code, date),bloodHigh, bloodLow, weight, height));
                }
            }else if(Message.class.equals(clazz)){
                int senderCode = cursor.getInt(cursor.getColumnIndex(Constant.MESSAGE_SENDERCODE));
                int receiverCode = cursor.getInt(cursor.getColumnIndex(Constant.MESSAGE_RECEIVERCODE));
                Date date = new Date(cursor.getLong(cursor.getColumnIndex(Constant.MESSAGE_DATE)));
                String message = cursor.getString(cursor.getColumnIndex(Constant.MESSAGE_MESSAGE));
                list.add(new Message(senderCode, receiverCode, message, date));
            }
        }
        return list;
    }

    /**
     * insert pojo to database
     * @param db database
     * @param pojo pojo
     * @param clazz class of pojo
     * @return
     */
    public static long insert(SQLiteDatabase db, Object pojo, Class<?> clazz)  {
        ContentValues values = new ContentValues();
        long result = -1;
        try {
            if (db != null) {
                if (User.class.equals(clazz)) {
                    values.put(Constant.USER_CODE, ((User) pojo).getCode());
                    values.put(Constant.USER_USERACCOUNT, ((User) pojo).getUserAccount());
                    values.put(Constant.USER_PASSWORD, ((User) pojo).getPassword());
                    values.put(Constant.USER_USERTYPE, ((User) pojo).getUserType());
                    result = db.insertOrThrow(Constant.TABLE_USER_NAME, null, values);
                } else if (PersonInfo.class.equals(clazz)) {
                    values.put(Constant.PERSONINFO_CODE, ((PersonInfo) pojo).getCode());
                    values.put(Constant.PERSONINFO_SEX, ((PersonInfo) pojo).getSex());
                    values.put(Constant.PERSONINFO_EMAIL, ((PersonInfo) pojo).getEmail());
                    values.put(Constant.PERSONINFO_ICON, ((PersonInfo) pojo).getIcon());
                    values.put(Constant.PERSONINFO_BIRTHDATE, ((PersonInfo) pojo).getBirthDate().getTime());
                    values.put(Constant.PERSONINFO_NICKNAME, ((PersonInfo) pojo).getNickname());
                    result = db.insertOrThrow(Constant.TABLE_PERSONINFO_NAME, null, values);
                } else if (DoctorInformation.class.equals(clazz)) {
                    values.put(Constant.DOCTORINFO_CODE, ((DoctorInformation) pojo).getCode());
                    values.put(Constant.DOCTORINFO_DEP, ((DoctorInformation) pojo).getDep());
                    result = db.insertOrThrow(Constant.TABLE_DOCTORINFO_NAME, null, values);
                } else if (HealthInfo.class.equals(clazz)) {
                    values.put(Constant.HEALTHINFO_CODE, ((HealthInfo) pojo).getId().getCode());
                    values.put(Constant.HEALTHINFO_DATE, ((HealthInfo) pojo).getId().getDate().getTime());
                    values.put(Constant.HEALTHINFO_BLOODHIGH, ((HealthInfo) pojo).getBloodHigh().doubleValue());
                    values.put(Constant.HEALTHINFO_BLOODLOW, ((HealthInfo) pojo).getBloodLow().doubleValue());
                    values.put(Constant.HEALTHINFO_WEIGHT, ((HealthInfo) pojo).getWeight().doubleValue());
                    values.put(Constant.HEALTHINFO_HEIGHT, ((HealthInfo) pojo).getHeight().doubleValue());
                    result = db.insertOrThrow(Constant.TABLE_HEALTHINFO_NAME, null, values);
                } else if (Message.class.equals(clazz)) {
                    values.put(Constant.MESSAGE_SENDERCODE, ((Message) pojo).getSenderCode());
                    values.put(Constant.MESSAGE_RECEIVERCODE, ((Message) pojo).getReceiverCode());
                    values.put(Constant.MESSAGE_DATE, ((Message) pojo).getDate().getTime());
                    values.put(Constant.MESSAGE_MESSAGE, ((Message) pojo).getMessage());
                    result = db.insertOrThrow(Constant.TABLE_MESSAGE_NAME, null, values);
                } else
                    throw new Exception("Wrong Type");
            }
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
