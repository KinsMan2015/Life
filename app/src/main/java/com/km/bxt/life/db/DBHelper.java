package com.km.bxt.life.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xStringong on 2""15/7/6.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String ID = "_id"; // 自动增长的ID
    public static final String UUID = "uid";
    public static final String TIME = "time"; // 记录的时间
    public static final String DB_NAME = "LifeAndDeath.db";
    public static final String TBL_NAME1 = "userdata";
    public static final int DB_VERSION = 1;
   public static final String BIRTHYEAR = "birthyear";
   public static final String BIRTHMONTH = "birthmonth";
   public static final String BIRTHDAY = "birthday";
   public static final String DEATHOLD = "deathold";
    /*****************/
   public static final String OLD = "old";
   public static final String YEAR = "year";
   public static final String MONTH = "month";
   public static final String WEEK = "week";
   public static final String DAY = "day";
   public static final String HOUR = "hour";
   public static final String MINUTE = "minute";
    /*******************/
   public static final String DEATH = "death";
   public static final String EAT = "eat";
   public static final String MAKELOVE = "makelove";
   public static final String WEEKENDS = "weekends";
   public static final String HOLIDAY = "holiday";
   public static final String SUM_COUNT = "sum_count";
   public static final String DAY_COUNT = "day_count";
   public static final String FIRSTSTART = "false";
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TBL_NAME1 + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + UUID
                + " TEXT DEFAULT NULL," + TIME + " TEXT DEFAULT NULL," + BIRTHYEAR + " TEXT DEFAULT NULL," +
                BIRTHMONTH + " TEXT DEFAULT NULL," + BIRTHDAY + " TEXT DEFAULT NULL," + DEATHOLD + " TEXT DEFAULT NULL,"
                + OLD + " TEXT DEFAULT NULL," + YEAR + " TEXT DEFAULT NULL," + MONTH + " TEXT DEFAULT NULL,"
                + WEEK + " TEXT DEFAULT NULL," + DAY + " TEXT DEFAULT NULL," + HOUR + " TEXT DEFAULT NULL,"
                + MINUTE + " TEXT DEFAULT NULL," + DEATH + " TEXT DEFAULT NULL," + EAT + " TEXT DEFAULT NULL," +
                MAKELOVE + " TEXT DEFAULT NULL," + WEEKENDS + " TEXT DEFAULT NULL," + HOLIDAY + " TEXT DEFAULT NULL," +
                SUM_COUNT + " TEXT DEFAULT NULL," + DAY_COUNT + " TEXT DEFAULT NULL," + FIRSTSTART + " TEXT DEFAULT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
