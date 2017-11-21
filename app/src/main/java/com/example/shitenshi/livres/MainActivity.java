package com.example.shitenshi.livres;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView myListView;

    DrawerLayout myDrawerLayout;
    public static final int PREFERENCE_INIT = 0;
    public static final int PREFERENCE_BOOTED  = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListView = (ListView)findViewById(R.id.myListView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent1 = getIntent();
        String text1 = intent1.getStringExtra("Data");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private int getState (){
        int state;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        state =sp.getInt("InitState",PREFERENCE_INIT);
        return state;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Outgodbhelper outgodbhelper = new Outgodbhelper(this);
        List<DbContainer> l = outgodbhelper.getContainers();

        //adapter prepare
        String[] column = new String[l.size()];
        Collections.reverse(l);
        String hugou = null;


        for (int i = 0; i < l.size(); i++) {

            if ("income".equals(l.get(i).category)){

                hugou = "+";

            } if ("outgo".equals(l.get(i).category)){

                hugou = "-";

            }

            column[i] ="品目： " + l.get(i).productname + "\n" +hugou+ l.get(i).price + "¥" + "\n" + "残金：" + l.get(i).remainingmoney;
        }

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, column);

        myListView.setAdapter(adapter);
        if(PREFERENCE_INIT == getState()){
            Intent intent1 = new Intent(MainActivity.this, InitActivity.class);
            startActivity(intent1);

        }
    }
}
