package com.application.task;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {
    protected static final String DATABASE_NAME = "Task.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE task " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "u_id INTEGER, "+"task TEXT, " +
                "completed INTEGER ) ";
        sqLiteDatabase.execSQL("CREATE TABLE User(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT ,EMAIL TEXT UNIQUE,PASSWORD TEXT);");

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS task";
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS User;");

        sqLiteDatabase.execSQL(sql);

        onCreate(sqLiteDatabase);

    }
}
