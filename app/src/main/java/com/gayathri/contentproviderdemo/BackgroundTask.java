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
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ragavan Raveendran on 20/03/2018.
 */

public class BackgroundTask extends AsyncTask<String, Product, String> implements AdapterView.OnItemClickListener {

    Context context;
    ProductAdapter adapter;
    Activity activity;
    ListView listView;
    SQLiteDatabase db;
    DbHelper dbHelper;
    SaveInfo saveInfo;
    DisplayProduct displayProduct;
    Product product;

    BackgroundTask( Context context){
        this.context = context;
        activity = (Activity) context;
        dbHelper = new DbHelper(context);
        saveInfo = new SaveInfo();
        displayProduct = new DisplayProduct();
    }

    @Override
    protected String doInBackground(String... strings) {

        String method = strings[0];
        if(method.equals("add_info")){
            String id = strings[1];
            String name = strings[2];
            int price = Integer.parseInt(strings[3]);
            int quantity = Integer.parseInt(strings[4]);
            db= dbHelper.getWritableDatabase();
            saveInfo.addInfo(db,id, name, price, quantity);
            return "One Row Inserted";
        }
        else if(method.equals("get_info")){
            String id, name;
            int price, quantity;
            listView = (ListView) activity.findViewById(R.id.listView);
            adapter = new ProductAdapter(context, R.layout.display_product_row);
            db = dbHelper.getReadableDatabase();
            Cursor cursor = displayProduct.getInfo(db);
            cursor.moveToFirst();
            do{
                    id = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.ID));
                    name = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.NAME));
                    price = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry.PRICE));
                    quantity = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry.QUANTITY));
                    Product product = new Product(id, name, price, quantity);
                    //publishProgress(product);
                    adapter.add(product);
            }while (cursor.moveToNext());
            return "get_info";
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Product... values) {
        adapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("get_info")){
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
        else
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int position = i;
        product = (Product) adapter.getItem(position);
        showOption();
    }

    public void showOption() {
        final String[] item = {"View Product", "Update Product", "Detele Product"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        showProduct();
                        break;
                    case 1: {
                        String id = product.getId();
                        String name = product.getName();
                        int price = product.getPrice();
                        int quantity = product.getQuantity();
                        updateProduct(id, name, price, quantity);
                        /*saveInfo.updateitem(id, name, price, quantity);*/
                    }
                    adapter.notifyDataSetChanged();
                    break;
                    case 2: {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setTitle("Are you Sure To Delete");
                        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String id = product.getId();
                                displayProduct.deleteProduct(id);
                            }
                        });
                        builder1.setNegativeButton("No", null);
                        builder1.create().show();
                    }
                    adapter.remove(i);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        });
        builder.create().show();
        adapter.notifyDataSetChanged();
    }

    public void showProduct(){
        AlertDialog.Builder builder =  new AlertDialog.Builder(context);
        builder.setTitle(ProductContract.ProductEntry.TABLE_NAME);
        builder.setMessage(product.toStringname());
        builder.setPositiveButton("Exit", null);
        builder.create().show();
        Toast.makeText(context, product.getName() + " is selected!", Toast.LENGTH_SHORT).show();
    }

    public void updateProduct(String id, String name, int price, int quantity){
        product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        Intent intent = new Intent(activity, SaveInfo.class);
        intent.putExtra("updateitem", product);
        activity.startActivity(intent);
    }
}
