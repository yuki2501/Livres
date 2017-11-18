package com.example.shitenshi.livres;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shitenshi on 17/10/24.
 */

public class Outgodbhelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "shyushi_table";
    public static final String CATEGORY_KEY = "category";
    public static final String PRODUCTNAME_KEY = "productname";
    public static final String PRICE_KEY = "price";
    public static final String REMAININGMONEY_KEY = "remainingmoney";
    private static final String CREATE_TABLE_SQL = "" +  "create table " + TABLE_NAME + "(" +
            "rowid integer primary key autoincrement," +
            CATEGORY_KEY + " string," +
            PRODUCTNAME_KEY + " string," +
            PRICE_KEY + " integer," +
            REMAININGMONEY_KEY + " integer" +
            

            ")";
    private static final String DROP_TABLE_SQL = "drop table if exists" + TABLE_NAME;




    public Outgodbhelper(Context context) {
        super(context, "syushidb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_SQL);

    }
    //挿入用メソッド
    public void insertValues(DbContainer dbContainer){
        ContentValues values = new ContentValues();
        values.put(Outgodbhelper.CATEGORY_KEY,dbContainer.category);
        values.put(Outgodbhelper.PRODUCTNAME_KEY,dbContainer.productname);
        values.put(Outgodbhelper.PRICE_KEY,dbContainer.price);
        values.put(Outgodbhelper.REMAININGMONEY_KEY,dbContainer.remainingmoney);
        getWritableDatabase().insert(Outgodbhelper.TABLE_NAME,null,values);


    }

    public List<DbContainer> getContainers(){
        //db
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        List<DbContainer> list = new ArrayList<>();

        //select
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME,null);
        while(cursor.moveToNext()){
            list.add(new DbContainer(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4)
            ));
        }
        return list;
    }
}
