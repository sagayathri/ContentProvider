package com.gayathri.contentproviderdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gayathri.contentproviderdemo.MyContentProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class DisplayProduct extends AppCompatActivity{

    static ContentResolver resolver;
    Product product;
    int position;
    ProductAdapter adapter;
    Activity activity;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product);
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("get_info");
        resolver = getContentResolver();
    }

    public Cursor getInfo(SQLiteDatabase db) {

        String sortOrder = ProductContract.ProductEntry.ID + " ASC ";
        String[] projection = {ProductContract.ProductEntry.ID, ProductContract.ProductEntry.NAME,
                ProductContract.ProductEntry.PRICE, ProductContract.ProductEntry.QUANTITY};
       // Cursor cursor = db.query(ProductContract.ProductEntry.TABLE_NAME, projection, null, null,null,null, sortOrder);
        Cursor cursor = resolver.query(MyContentProvider.URI_Product, projection, null, null, sortOrder);
        return cursor;
    }


    public void deleteProduct(String id) {
        String where = ProductContract.ProductEntry.ID + " = " + id;
        int count = resolver.delete(MyContentProvider.URI_Product, where, null);
        if (count > 0)
            Toast.makeText(context, product.getName() + " is Deleted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, product.getName() + " is Not Deleted", Toast.LENGTH_SHORT).show();
    }
}
