package com.example.shitenshi.livres;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.example.shitenshi.livres.R.id.editText;
import static com.example.shitenshi.livres.R.id.textView2;

public class AddActivity extends AppCompatActivity {
    private static final int MAIN_ACTIVITY = 101;
    Outgodbhelper outgodbhelper;
    private  static  final String PREFS_FILE = "HMPrefs";
    private  static  final String Havemoney = "Havemoney";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcard);
        outgodbhelper = new Outgodbhelper(this);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences(PREFS_FILE , Activity.MODE_PRIVATE);
                Spinner spinner =(Spinner)findViewById(R.id.spinner);
                int number = spinner.getSelectedItemPosition();
                EditText n = (EditText) findViewById(R.id.editText2);
                Editable productname = n.getText();
                EditText p = (EditText) findViewById(R.id.editText);
                Editable price = p.getText();
                Integer havemoney = prefs.getInt(Havemoney,0);
                Integer remainingmoney = 0;
                if (number == 0){
                    remainingmoney = havemoney + Integer.valueOf(price.toString());
                }if (number==1){remainingmoney = havemoney - Integer.valueOf(price.toString());
                }
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("Havemoney");
                editor.putInt("Havemoney",remainingmoney);
                editor.commit();


                outgodbhelper.insertValues(new DbContainer(
                        spinner.getSelectedItem().toString(),
                        productname.toString(),
                        Integer.valueOf(price.toString()),
                        remainingmoney));
                Toast.makeText(AddActivity.this, "ok", Toast.LENGTH_LONG).show();

                finish();

             }



            });
    }
}
