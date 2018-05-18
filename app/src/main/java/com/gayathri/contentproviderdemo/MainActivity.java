package com.gayathri.contentproviderdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void AddProduct(View view) {
        Intent intent = new Intent(MainActivity.this, SaveInfo.class);
        startActivity(intent);
    }

    public void DisplayProducts(View view) {
        startActivity(new Intent(MainActivity.this, DisplayProduct.class));
    }
}
