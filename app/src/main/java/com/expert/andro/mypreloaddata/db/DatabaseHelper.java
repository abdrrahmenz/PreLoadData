package com.expert.andro.mypreloaddata.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adul on 20/09/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbmahasiswa";
    public static String TABLE_NAME = "table_mahasiswa";
    public static String FIELD_ID = "id";
    public static String FIELD_NAMA = "nama";
    public static String FIELD_NIM = "nim";


    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_MAHASISWA = "create table " +TABLE_NAME + " (" + FIELD_ID + " integer primary key autoincrement, "+
            FIELD_NAMA + " text not null, "+
            FIELD_NIM + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MAHASISWA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
