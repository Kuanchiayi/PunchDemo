package com.example.demo0505;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlDataBaseHelper extends SQLiteOpenHelper {

    /*
    * ID = 員編
    * id = 編號
    */
    private static final String CREATE_Employee = "create table if not exists Employee(ID integer not null primary key, name text, password text not null)";
    private  static final String CREATE_Bulletin = "create table if not exists Bulletin(id_bulletin integer primary key autoincrement, createTime text, content text, ID Integer)";
    private static final String CREATE_Punch = "create table if not exists Punch(id_punch integer primary key autoincrement, startTime text, endTime text, ID Integer)";


    public SqlDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_Employee);
        sqLiteDatabase.execSQL(CREATE_Bulletin);
        sqLiteDatabase.execSQL(CREATE_Punch);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
