package com.example.sasha.tlumach_dictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLClientInfoException;

/**
 * Created by Sasha on 05.05.2017.
 */
public class DBHelper1 extends SQLiteOpenHelper {

   public static final int DATABASE_VERSION = 1;
   public static final String DATABASE_NAME = "ukrTlymSlov";
   public static final String TABLE_TLUMACH = "tlumach";

   public static final String KEY_ID = "_id";
   public static final String KEY_WORD = "word";
   public static final String KEY_ZNACHENNA = "znachenna";


    public DBHelper1(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
// создаем таблицу с полями
        db.execSQL("create table " + TABLE_TLUMACH + "(" + KEY_ID + " integer primary key autoincrement," + KEY_WORD + " text,"
                + KEY_ZNACHENNA + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists" + TABLE_TLUMACH);

        onCreate(db);
    }


}
