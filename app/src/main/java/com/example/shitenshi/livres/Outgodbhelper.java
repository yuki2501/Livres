package com.example.shitenshi.livres;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shitenshi on 17/10/24.
 */

public class Outgodbhelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_SQL = "" +  "create table outgodb(" +
            "rowid integer primary key autoincrement," +
            "data text null" +
            ")";
    private static final String DROP_TABLE_SQL = "drop table if exists outgodb";




    public Outgodbhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_SQL);

    }
}
