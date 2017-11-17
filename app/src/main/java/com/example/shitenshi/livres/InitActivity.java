package com.example.shitenshi.livres;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

;        Button okbutton = (Button)findViewById(R.id.button2);
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText havemoney = (EditText) findViewById(R.id.editText3);
                Editable hm = havemoney.getText();
                setState(Integer.valueOf(hm.toString()));
                finish();
            }
        });


    }
    private void setState (int state){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putInt("InitState",state).commit();
    }
}
