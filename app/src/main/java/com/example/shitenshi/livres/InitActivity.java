package com.example.shitenshi.livres;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class InitActivity extends AppCompatActivity {
    private  static  final String PREFS_FILE = "HMPrefs";
    private  static  final String Havemoney = "Havemoney";
    private  static  final String Inittime = "Inittime";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Theme theme = new Theme();
        theme.themeset(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);


;        Button okbutton = (Button)findViewById(R.id.button2);
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText havemoney = (EditText) findViewById(R.id.editText3);
                Editable hm = havemoney.getText();
                setState(Integer.valueOf(hm.toString()));
                SharedPreferences prefs = getSharedPreferences(PREFS_FILE , Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("Havemoney", Integer.valueOf(hm.toString()));
                Date date = new Date();
                long now = date.getTime();
                SharedPreferences inittime = getSharedPreferences(Inittime,Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = inittime.edit();
                editor1.putLong("Inittime", now);
                editor.apply();
                editor1.apply();
                editor.commit();
                editor1.commit();
                finish();
            }
        });


    }
    @Override
    protected void onResume(){
        super.onResume();
        Theme theme = new Theme();
        theme.themeset(this);

    }
    private void setState (int state){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putInt("InitState",state).apply();
    }


}
