package com.example.shitenshi.livres;

import android.content.Intent;
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
        Theme theme = new Theme();
        theme.themeset(this);
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
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item_delete) {
            DbContainer info = (DbContainer) getIntent().getSerializableExtra("DbContainer" );
            OutgoDbHelper outgoDbHelper = new OutgoDbHelper(this);
            outgoDbHelper.deleteItem(info.time);
            finish();
        }if (id == R.id.action_settings){
            Intent intent = new Intent(ListInfoActivity.this,PreferenceActivity.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume(){
        super.onResume();
        Theme theme = new Theme();
        theme.themeset(this);

    }



}
