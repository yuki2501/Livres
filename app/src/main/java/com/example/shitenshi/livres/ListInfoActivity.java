package com.example.shitenshi.livres;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
        TextView name = findViewById(R.id.textView4);
        TextView price = findViewById(R.id.textView5);

        time.setText(String.valueOf(info.time));
        category.setText(info.category);
        name.setText(info.productname);
        price.setText(String.valueOf(info.price));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item_delete) {
            DbContainer info = (DbContainer) getIntent().getSerializableExtra("DbContainer" );
            Outgodbhelper outgodbhelper = new Outgodbhelper(this);
            outgodbhelper.deleteItem(info.time);
            finish();


        }

        return super.onOptionsItemSelected(item);
    }



}
