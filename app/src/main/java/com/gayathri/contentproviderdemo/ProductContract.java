package com.gayathri.contentproviderdemo;


import android.net.Uri;

public final class ProductContract {
    ProductContract(){}
    public static abstract class ProductEntry{
        public static final String ID= "id";
        public static final String NAME = "name";
        public static final String PRICE = "price";
        public static final String QUANTITY = "quantity";
        public static final String TABLE_NAME = "product_table";
        public static final Uri URI_MY_PRODUCT = Uri.parse("content://com.gayathri.contentproviderdemo/"+ TABLE_NAME);
    }
}
