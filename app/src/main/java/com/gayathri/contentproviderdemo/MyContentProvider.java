package com.gayathri.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.gayathri.contentproviderdemo.DbHelper;
import com.gayathri.contentproviderdemo.ProductContract;

public class MyContentProvider extends ContentProvider{
    public static DbHelper dbHelper;
    public static final int ThisProduct = 100;
    public static final int ThisProduct_ID = 101;
    public static final String AUTHORITY_NAME = "com.gayathri.contentproviderdemo";
    public static final Uri URI_Product = Uri.parse("content://"+AUTHORITY_NAME+"/"+ ProductContract.ProductEntry.TABLE_NAME);
    public static final Uri URI_Product_ID = Uri.parse("content://"+AUTHORITY_NAME+"/"+ ProductContract.ProductEntry.ID);
    public static UriMatcher URI_Matcher = buildUriMatcher();

    /*public MyContentProvider(Context context) {
      context= this.getContext();
    }*/

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY_NAME, ProductContract.ProductEntry.TABLE_NAME, ThisProduct);
        matcher.addURI(AUTHORITY_NAME, ProductContract.ProductEntry.ID, ThisProduct_ID);;
        return matcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new DbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int match = URI_Matcher.match(uri);
        switch (match){
            case ThisProduct: {
               // String tableName = uri.getLastPathSegment();
                cursor = db.query(ProductContract.ProductEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case ThisProduct_ID:{
               //String tableName = uri.getLastPathSegment();
                cursor = db.query(ProductContract.ProductEntry.TABLE_NAME+"/"+ThisProduct_ID,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        Uri turi = null;
        if(dbHelper == null)
            dbHelper = new DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (URI_Matcher.match(uri)){
            case ThisProduct:
                long res = db.insert(ProductContract.ProductEntry.TABLE_NAME, null, contentValues);
                if(res>0){
                    turi = ContentUris.withAppendedId(URI_Product, res);
                    getContext().getContentResolver().notifyChange(turi, null);
                }
                else
                    Toast.makeText(getContext(), "Not Inserted", Toast.LENGTH_SHORT).show();
                break;
            default:
                throw new SQLException("SQLiteException at "+uri);
        }
        return turi;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        if(dbHelper == null)
            dbHelper = new DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (URI_Matcher.match(uri)){
            case ThisProduct:
                count = db.update(ProductContract.ProductEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ThisProduct_ID:
                count = db.update(ProductContract.ProductEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
        }
        return count;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        if(dbHelper == null)
            dbHelper = new DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (URI_Matcher.match(uri)){
            case ThisProduct:
                count = db.delete(ProductContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ThisProduct_ID:
                count = db.delete(ProductContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
        }
        return count;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
