package com.WallE.TCMK.Database;

/**
 * Created by Jinffee on 2017/9/9.
 */

public class Constant {
    /**
     * database information
     */
    public static final String DATABASE_NAME = "WallE.db";
    public static final int DATABASE_VERSION = 1;
    /**
     * user table information
     */
    public static final String TABLE_USER_NAME = "user";
    public static final String USER_CODE = "_id";
    public static final String USER_USERACCOUNT = "userAccount";
    public static final String USER_USERTYPE = "userType";
    public static final String USER_PASSWORD = "password";
    /**
     * person table information
     */
    public static final String TABLE_PERSONINFO_NAME = "PersonalInfo";
    public static final String PERSONINFO_CODE = "_id";
    public static final String PERSONINFO_SEX = "sex";
    public static final String PERSONINFO_EMAIL = "email";
    public static final String PERSONINFO_ICON = "icon";
    public static final String PERSONINFO_BIRTHDATE = "birthDate";
    public static final String PERSONINFO_NICKNAME = "nickname";
    /**
     * doctorInfo table information
     */
    public static final String TABLE_DOCTORINFO_NAME = "DoctorInfo";
    public static final String DOCTORINFO_CODE = "_id";
    public static final String DOCTORINFO_DEP = "dep";
    /**
     *HealthInfo table information
     */
    public static final String TABLE_HEALTHINFO_NAME = "healthInfo";
    public static final String HEALTHINFO_ID = "_id";
    public static final String HEALTHINFO_CODE = "code";
    public static final String HEALTHINFO_DATE = "date";
    public static final String HEALTHINFO_BLOODHIGH = "bloodHigh";
    public static final String HEALTHINFO_BLOODLOW = "bloodLow";
    public static final String HEALTHINFO_WEIGHT = "weight";
    public static final String HEALTHINFO_HEIGHT = "height";
    /**
     *Message table information
     */
    public static final String TABLE_MESSAGE_NAME = "message";
    public static final String MESSAGE_ID = "_id";
    public static final String MESSAGE_SENDERCODE = "senderCode";
    public static final String MESSAGE_RECEIVERCODE = "receiverCode";
    public static final String MESSAGE_MESSAGE = "message";
    public static final String MESSAGE_DATE = "date";
 }
