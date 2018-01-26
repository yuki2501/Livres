package com.example.shitenshi.livres;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.TextView;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.myListView)
    ListView myListView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    public static final int PREFERENCE_INIT = 0;
    public static final int PREFERENCE_BOOTED  = 1;
    private static final String PREFS_FILE = "HMPrefs";
    private static final String Havemoney = "Havemoney";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Theme theme = new Theme();
        theme.themeset(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListView = findViewById(R.id.myListView);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent1 = getIntent();
        String text1 = intent1.getStringExtra("Data");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    @OnItemClick(R.id.myListView)
    public  void onListItemClick(int position){
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, Activity.MODE_PRIVATE);
        int havemoney = prefs.getInt(Havemoney,0);

        Intent intent = new Intent(getApplication(), ListInfoActivity.class);
        Outgodbhelper outgodbhelper = new Outgodbhelper(this);
        List<DbContainer> list = outgodbhelper.getContainers(havemoney);
        DbContainer info =list.get(position);
        intent.putExtra("DbContainer", (Serializable) info);
        startActivity(intent);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, PreferenceActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        Theme theme = new Theme();
        theme.themeset(this);
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, Activity.MODE_PRIVATE);
        int havemoney = prefs.getInt(Havemoney,0);
        Outgodbhelper outgodbhelper = new Outgodbhelper(this);
        List<DbContainer> l = outgodbhelper.getContainers(havemoney);

        TextView nokori = findViewById(R.id.textView2);
        if (l.size()>0){
            nokori.setText(String.valueOf(l.get(l.size()-1).remainingmoney + "円"));
        }else{
           nokori.setText(String.valueOf(havemoney) + "円");
        }
        //adapter prepare
        String[] column = new String[l.size()];
        String hugou = null;
        for (int i = 0; i < l.size(); i++) {
            if ("income".equals(l.get(i).category)){
                hugou = "+";
            } if ("outgo".equals(l.get(i).category)){
                hugou = "-";
            }
            Boolean toggleswitch = PreferenceManager
                    .getDefaultSharedPreferences(MainActivity.this)
                    .getBoolean("switch_preference_1", false);
            if(!toggleswitch){
                column[i] ="品目： "
                        + l.get(i).productname + "\n"
                        +hugou
                        +l.get(i).price
                        + "¥" + "\n"
                        + "残金："
                        +l.get(i).remainingmoney;
            }else{long time = l.get(i).time;
                Date data = new Date(time);
                String listdata = data.toString();
                String [] spdata = listdata.split(" ",0);
                Date d = null;
                String viewdata = spdata[5]
                                  +" "
                                  +spdata[1]
                                  +" "
                                  +spdata[2]
                                  +" "
                                  +spdata[0]
                                  +" "
                                  +spdata[3];
                column[i] ="品目： "
                        + l.get(i).productname + "\n"
                        +hugou
                        +l.get(i).price
                        + "¥" + "\n"
                        + "残金："
                        + l.get(i).remainingmoney + "\n"
                        + viewdata;
            }
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, column);

        myListView.setAdapter(adapter);
        if(PREFERENCE_INIT == getState()){
            Intent intent1 = new Intent(MainActivity.this, InitActivity.class);
            startActivity(intent1);
        }
    }
}
