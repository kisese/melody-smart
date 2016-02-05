package com.yhtomit.brayo.bleserial.storagesqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Brayo on 2/24/2015.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BLE_SERIAL_DB";
    public static final String TABLE_NAME = "finger_colors";


    public static final int VERSION = 4;
    public static final String KEY_ID = "_id";
    public static final String KEY_FINGER_ID = "finger_id";
    public static final String KEY_COLOR_1 = "color_1";
    public static final String KEY_COLOR_2 = "color_2";
    public static final String KEY_COLOR_3 = "color_3";
    public static final String KEY_COLOR_4 = "color_4";
    public static final String KEY_COLOR_5 = "color_5";
    public static final String KEY_COLOR_6 = "color_6";
    public static final String KEY_CREATED_AT = "created_at";
    public static final String KEY_UPDATED_AT = "updated_at";


    public static final String SCRIPT = "create table " + TABLE_NAME + " ("
            + KEY_ID + " integer primary key autoincrement not null, " +
            KEY_FINGER_ID + " text null, " +
            KEY_COLOR_1 + " text null, " +
            KEY_COLOR_2 + " text null, " +
            KEY_COLOR_3 + " text null, " +
            KEY_COLOR_4 + " text null, " +
            KEY_COLOR_5 + " text null, " +
            KEY_COLOR_6 + " text null, " +
            KEY_CREATED_AT + " text null, " +
            KEY_UPDATED_AT + " text null);";



    public DBOpenHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }
}
