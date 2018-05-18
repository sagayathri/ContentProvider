package com.gayathri.contentproviderdemo;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class SaveInfo extends Activity{
    EditText pid, pname, pquantity, pprice;
    String id, name;
    int quantity, price;
    Button btnsave,btnupate;
    static ContentResolver resolver;
    boolean isUpdated = false;
    Product product, rcvproduct;
    ProductAdapter adapter;
    List list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saveinfo);

        pid = (EditText)findViewById(R.id.pid);
        pname = (EditText)findViewById(R.id.pname);
        pquantity=(EditText)findViewById(R.id.pquantity);
        pprice = (EditText)findViewById(R.id.pprice);
        btnsave = (Button)findViewById(R.id.btnSave);
        btnupate = (Button)findViewById(R.id.btnUpdate);
        btnupate.setVisibility(View.INVISIBLE);
        resolver = getContentResolver();

        Intent intent = getIntent();
        isUpdated = intent.hasExtra("updateitem");
        if(isUpdated){
            rcvproduct = (Product)intent.getSerializableExtra("updateitem");

            id = rcvproduct.getId().toString();
            name = rcvproduct.getName().toString();
            price = rcvproduct.getPrice();
            quantity = rcvproduct.getQuantity();

            pid.setText(id);
            pname.setText(name);
            pprice.setText(""+price);
            pquantity.setText(""+quantity);
            btnupate.setVisibility(View.VISIBLE);
            btnsave.setVisibility(View.INVISIBLE);

            btnupate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    id = pid.getText().toString();
                    name = pname.getText().toString();
                    price = Integer.parseInt(pprice.getText().toString());
                    quantity = Integer.parseInt(pquantity.getText().toString());
                    updateitem(id, name, price, quantity);
                }
            });
        }
    }

    public void SaveData(View view){
        String id, name, price, quantity;
        id = pid.getText().toString();
        name= pname.getText().toString();
        price = pprice.getText().toString();
        quantity = pquantity.getText().toString();

        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("add_info", id, name, price, quantity);

        finish();
    }

    public void addInfo(SQLiteDatabase db, String id, String name, int price, int quantity){
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.ID, id);
        values.put(ProductContract.ProductEntry.NAME, name);
        values.put(ProductContract.ProductEntry.PRICE, price);
        values.put(ProductContract.ProductEntry.QUANTITY, quantity);
        //db.insert(ProductContract.ProductEntry.TABLE_NAME, null, values);
        resolver.insert(MyContentProvider.URI_Product, values);
        Log.d("Database Operation", "One Row Inserted");
    }

    public void updateitem(String id, String name, int price, int quantity){
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.ID, id);
        values.put(ProductContract.ProductEntry.NAME, name);
        values.put(ProductContract.ProductEntry.PRICE, price);
        values.put(ProductContract.ProductEntry.QUANTITY, quantity);

        String selection = ProductContract.ProductEntry.ID+" = " +id;
        int count = resolver.update(MyContentProvider.URI_Product, values, selection , null);
        if(count>0){
            Toast.makeText(this,name+" is Updated!", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        else
            Toast.makeText(this,name+" is Not Updated!", Toast.LENGTH_SHORT).show();
    }
}
