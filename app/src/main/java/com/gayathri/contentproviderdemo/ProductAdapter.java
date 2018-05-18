package com.gayathri.contentproviderdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ragavan Raveendran on 20/03/2018.
 */

public class ProductAdapter extends ArrayAdapter{

    public ArrayList<Product> list = new ArrayList();
    Product product;
    int position;

    public ProductAdapter(@NonNull Context context, int resource ) {
        super(context, resource);
    }

    public void add(Product object) {
        list.add(object);
        super.add(object);
    }

    public void remove(int position){
        list.remove(position);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        Holder holder;
        if(row == null){
            holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.display_product_row, parent, false);
            holder.tid = (TextView)row.findViewById(R.id.itemid);
            holder.tname = (TextView)row.findViewById(R.id.itemname);
            holder.tprice = (TextView)row.findViewById(R.id.itemprice);
            holder.tquantity = (TextView)row.findViewById(R.id.itemquantity);
            row.setTag(holder);
        }
        else {
            holder = (Holder) row.getTag();
        }
        Product product = (Product) getItem(position);
        holder.tid.setText(product.getId().toString());
        holder.tname.setText(product.getName().toString());
        holder.tprice.setText(Integer.toString(product.getPrice()));
        holder.tquantity.setText(Integer.toString(product.getQuantity()));
        return row;
    }

    static class Holder{
        TextView tid, tname, tprice, tquantity;
    }
}
