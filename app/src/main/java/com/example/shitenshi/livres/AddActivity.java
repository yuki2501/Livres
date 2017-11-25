package com.example.shitenshi.livres;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

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
                SharedPreferences prefs = getSharedPreferences(PREFS_FILE, Activity.MODE_PRIVATE);
                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                int number = spinner.getSelectedItemPosition();
                EditText n = (EditText) findViewById(R.id.editText2);
                Editable productname = n.getText();
                EditText p = (EditText) findViewById(R.id.editText);
                Editable price = p.getText();
                Integer havemoney = prefs.getInt(Havemoney, 0);
                Integer remainingmoney = 0;
                if (number == 0) {
                    remainingmoney = havemoney + Integer.valueOf(price.toString());
                }
                if (number == 1) {
                    remainingmoney = havemoney - Integer.valueOf(price.toString());
                }
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("Havemoney");
                editor.putInt("Havemoney", remainingmoney);
                editor.commit();


                outgodbhelper.insertValues(new DbContainer(
                        spinner.getSelectedItem().toString(),
                        productname.toString(),
                        Integer.valueOf(price.toString()),
                        remainingmoney));
                Boolean toggleswitch = PreferenceManager.getDefaultSharedPreferences(AddActivity.this).getBoolean("switch_preference", false);
                if (toggleswitch == Boolean.TRUE) {
                    Outgodbhelper outgodbhelper = new Outgodbhelper(AddActivity.this);
                    List<DbContainer> l = outgodbhelper.getContainers();
                    String fuckexele = "category,product,price,remainingmoney\n";
                    StringBuilder stringBuilder = new StringBuilder(fuckexele);
                    String[] column = new String[l.size()];
                    for (int i = 0; i < l.size(); i++) {
                        column[i] = l.get(i).category + "," + l.get(i).productname + "," + l.get(i).price + "," + l.get(i).remainingmoney + "\n";
                        stringBuilder.append(column[i]);


                    }
                    try {
                        File file = getCSVDir("csv.csv");
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write(fuckexele);
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (FileNotFoundException e) {

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                finish();
            }






        });

    }

    public File getCSVDir(String CSV) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), CSV);
        return file;
    }

    }

