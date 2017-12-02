package com.example.shitenshi.livres;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class ListInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DbContainer info = (DbContainer) getIntent().getSerializableExtra("DbContainer" );
        TextView time = findViewById(R.id.textView6);
        TextView category = findViewById(R.id.textView3);
        //id textView1をt2に当てはめている
        TextView name = findViewById(R.id.textView4);
        //id textView1をt3に当てはめている
        TextView price = findViewById(R.id.textView5);

        time.setText(String.valueOf(info.time));
        category.setText(info.category);
        name.setText(info.productname);
        price.setText(String.valueOf(info.price));



    }
   


}
