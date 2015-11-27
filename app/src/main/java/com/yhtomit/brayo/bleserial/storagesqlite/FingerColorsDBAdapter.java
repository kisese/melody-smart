package com.yhtomit.brayo.bleserial.storagesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Brayo on 5/29/2015.
 */
public class FingerColorsDBAdapter {

    SQLiteDatabase database_ob;
    DBOpenHelper openHelper_ob;
    Context context;
    String[] cols = {openHelper_ob.KEY_ID, openHelper_ob.KEY_FINGER_ID, openHelper_ob.KEY_COLOR_1
            , openHelper_ob.KEY_COLOR_2, openHelper_ob.KEY_COLOR_3, openHelper_ob.KEY_COLOR_4,
            openHelper_ob.KEY_COLOR_5, openHelper_ob.KEY_COLOR_6, openHelper_ob.KEY_CREATED_AT,
            openHelper_ob.KEY_UPDATED_AT};

    public FingerColorsDBAdapter(Context context) {
        this.context = context;
    }

    public FingerColorsDBAdapter opnToRead() {
        openHelper_ob = new DBOpenHelper(context,
                openHelper_ob.DATABASE_NAME, null, openHelper_ob.VERSION);
        database_ob = openHelper_ob.getReadableDatabase();
        return this;

    }


    public FingerColorsDBAdapter opnToWrite() {
        openHelper_ob = new DBOpenHelper(context,
                openHelper_ob.DATABASE_NAME, null, openHelper_ob.VERSION);
        database_ob = openHelper_ob.getWritableDatabase();
        return this;
    }

    public void Close() {
        database_ob.close();
    }
    public long insertMessage(String color_id, String color_1, String color_2, String color_3,
                              String color_4, String color_5, String color_6,
                              String created_at, String updated_at) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(openHelper_ob.KEY_FINGER_ID, color_id);
        contentValues.put(openHelper_ob.KEY_COLOR_1, color_1);
        contentValues.put(openHelper_ob.KEY_COLOR_2, color_2);
        contentValues.put(openHelper_ob.KEY_COLOR_3, color_3);
        contentValues.put(openHelper_ob.KEY_COLOR_4, color_4);
        contentValues.put(openHelper_ob.KEY_COLOR_5, color_5);
        contentValues.put(openHelper_ob.KEY_COLOR_6, color_6);
        contentValues.put(openHelper_ob.KEY_CREATED_AT, created_at);
        contentValues.put(openHelper_ob.KEY_UPDATED_AT, updated_at);
        opnToWrite();
        long val = database_ob.insert(openHelper_ob.TABLE_NAME, null,
                contentValues);
        Close();
        return val;
    }

    public Cursor queryAll() {

        opnToWrite();

        Cursor c = database_ob.query(openHelper_ob.TABLE_NAME, cols, null,
                null, null, null, openHelper_ob.KEY_CREATED_AT + " ASC");

        return c;

    }

    public Cursor queryFilter(String filter) {
        String[] selectionArgs = {"%" + filter + "%"};
        opnToWrite();
        Cursor c = database_ob.query(openHelper_ob.TABLE_NAME, cols, openHelper_ob.KEY_FINGER_ID + " LIKE ?",
                selectionArgs, null, null, null);

        return c;

    }

    public Cursor querySingle(String filter) {
        opnToWrite();
        Cursor c = database_ob.query(openHelper_ob.TABLE_NAME, cols,
                openHelper_ob.KEY_FINGER_ID + "=" + filter, null, null, null, null);

        return c;

    }

    public long updateMessage(String color_id, String color_1, String color_2, String color_3,
                              String color_4, String color_5, String color_6,
                              String created_at, String updated_at){
        ContentValues contentValues = new ContentValues();
        contentValues.put(openHelper_ob.KEY_FINGER_ID, color_id);
        contentValues.put(openHelper_ob.KEY_COLOR_1, color_1);
        contentValues.put(openHelper_ob.KEY_COLOR_2, color_2);
        contentValues.put(openHelper_ob.KEY_COLOR_3, color_3);
        contentValues.put(openHelper_ob.KEY_COLOR_4, color_4);
        contentValues.put(openHelper_ob.KEY_COLOR_5, color_5);
        contentValues.put(openHelper_ob.KEY_COLOR_6, color_6);
        contentValues.put(openHelper_ob.KEY_CREATED_AT, created_at);
        contentValues.put(openHelper_ob.KEY_UPDATED_AT, updated_at);
        opnToWrite();
        long val = database_ob.update(openHelper_ob.TABLE_NAME, contentValues,
                openHelper_ob.KEY_FINGER_ID + " = ?", new String[]{color_id});
        Close();
        return val;
    }

    public long updateKey(String index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", openHelper_ob.TABLE_NAME);
        contentValues.put("seq", index);
        opnToWrite();
        long val = database_ob.update("sqlite_sequence", contentValues,
                null, null);
        Close();
        return val;
    }

    public int deletOneRecord(int id) {
        // TODO Auto-generated method stub
        opnToWrite();
        int val = database_ob.delete(openHelper_ob.TABLE_NAME,
                openHelper_ob.KEY_ID + " = " + id, null);
        Close();
        return val;
    }

    public int deletAll() {
        // TODO Auto-generated method stub
        opnToWrite();
        int val = database_ob.delete(openHelper_ob.TABLE_NAME,
                null, null);
        Close();
        return val;
    }


    public int getRowCount() {
        opnToWrite();
        Cursor c = database_ob.query(openHelper_ob.TABLE_NAME, cols, null,
                null, null, null, openHelper_ob.KEY_CREATED_AT + " desc");
        int count = c.getCount();
        return count;

    }

}
