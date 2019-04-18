package com.elmirapervakova.makeup_studio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME= "contactDb";
    public static final String TABLE_CONTACTS= "contacts";

    public static final String KEY_ARTIST = "Artist";
    public static final String KEY_DATA = "Data";
    public static final String KEY_TIME = "Time";
    public static final String KEY_TEL = "Tel";
    public static final String KEY_NAME = "Name";

    public DBHelper(Context context) {

        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ARTIST  +
                " text," + KEY_DATA + " text," + KEY_TIME + " text," +
                KEY_NAME + " text," + KEY_TEL + " text" + ")" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);

        onCreate(db);

    }
}
