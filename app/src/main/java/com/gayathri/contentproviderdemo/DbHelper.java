package com.gayathri.contentproviderdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ragavan Raveendran on 20/03/2018.
 */

public class DbHelper extends SQLiteOpenHelper {
    private  static final int DB_VERSION = 1;
    private static final String DB_NAME = "product_info.db";
    private static String CREATE_TABLE = "CREATE TABLE "+ ProductContract.ProductEntry.TABLE_NAME+
            "( "+ ProductContract.ProductEntry.ID+ " TEXT, "+ ProductContract.ProductEntry.NAME+" TEXT, "
            +ProductContract.ProductEntry.PRICE+ " INTEGER, "+ ProductContract.ProductEntry.QUANTITY+ " INTEGER )";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d("Database Operation", "Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE);
        onCreate(sqLiteDatabase);
    }
    public void addInfo(SQLiteDatabase db, String id, String name, int price, int quantity){
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.ID, id);
        values.put(ProductContract.ProductEntry.NAME, name);
        values.put(ProductContract.ProductEntry.PRICE, price);
        values.put(ProductContract.ProductEntry.QUANTITY, quantity);
        db.insert(ProductContract.ProductEntry.TABLE_NAME, null, values);
        Log.d("Database Operation", "One Row Inserted");
    }
}
