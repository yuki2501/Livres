package com.example.shitenshi.livres;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by shitenshi on 17/10/24.
 */

public class OutgoDbHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "shyushi_table";
    private static final String CATEGORY_KEY = "category";
    private static final String PRODUCTNAME_KEY = "productname";
    private static final String PRICE_KEY = "price";
    private static final String REMAININGMONEY_KEY = "remainingmoney";
    private static String TIME_KEY = "time";
    private static final String PREFS_FILE = "HMPrefs";
    private static final String Havemoney = "Havemoney";
    private static final String CREATE_TABLE_SQL = "" + "create table " + TABLE_NAME + "(" +
            "rowid integer primary key autoincrement," +
            CATEGORY_KEY + " string," +
            PRODUCTNAME_KEY + " string," +
            PRICE_KEY + " integer," +
            REMAININGMONEY_KEY + " integer," +
            TIME_KEY + " integer" +


            ")";
    private static final String DROP_TABLE_SQL = "drop table if exists" + TABLE_NAME;


    public OutgoDbHelper(Context context) {
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
    void insertValues(DbContainer dbContainer) {
        ContentValues values = new ContentValues();
        values.put(OutgoDbHelper.CATEGORY_KEY, dbContainer.category);
        values.put(OutgoDbHelper.PRODUCTNAME_KEY, dbContainer.productname);
        values.put(OutgoDbHelper.PRICE_KEY, dbContainer.price);
        values.put(OutgoDbHelper.REMAININGMONEY_KEY, dbContainer.remainingmoney);
        values.put(OutgoDbHelper.TIME_KEY, dbContainer.time);
        getWritableDatabase().insert(OutgoDbHelper.TABLE_NAME, null, values);


    }


    List<DbContainer> getContainers(int havemoney) {
        //db
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        List<DbContainer> list = new ArrayList<>();

        //select
        Integer i = 0;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " ORDER BY " + OutgoDbHelper.TIME_KEY + " ASC ", null);
        while (cursor.moveToNext()) {
            Integer remainingmoney;
            if (cursor.getPosition() == 0) {
                String nedan = (Objects.equals(cursor.getString(1), "income") ? "+" : "-") + cursor.getInt(3);
                remainingmoney = havemoney + Integer.valueOf(nedan);
            } else {
                String nedan = (Objects.equals(cursor.getString(1), "income") ? "+" : "-") + cursor.getInt(3);
                remainingmoney = list.get(i - 1).remainingmoney + Integer.valueOf(nedan);
            }

            list.add(new DbContainer(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    remainingmoney,
                    cursor.getLong(5)
            ));
            i++;
        }
        cursor.close();
        return list;
    }

    void deleteItem(long time) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + " time = "+ time );
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    void replacedb(int havemoney) {
        List<DbContainer> l1 = getContainers(havemoney);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME );
            for (int i = 0; i < l1.size(); i++) {
                insertValues(new DbContainer(l1.get(i).category, l1.get(i).productname, l1.get(i).price, l1.get(i).remainingmoney, l1.get(i).time));
            }
            sqLiteDatabase.setTransactionSuccessful();
        }finally{
            sqLiteDatabase.endTransaction();
        }
    }
}




