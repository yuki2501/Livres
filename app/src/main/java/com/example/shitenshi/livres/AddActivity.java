package com.example.shitenshi.livres;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnItemSelected;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.shitenshi.livres.Outgodbhelper.REMAININGMONEY_KEY;
import static com.example.shitenshi.livres.Outgodbhelper.TABLE_NAME;
import static com.example.shitenshi.livres.Outgodbhelper.TIME_KEY;


public class AddActivity extends AppCompatActivity {

    private static final int MAIN_ACTIVITY = 101;
    Outgodbhelper outgodbhelper = new Outgodbhelper(this);


    DatePickerDialogFragment datePickerDialogFragment;
    private static final String PREFS_FILE = "HMPrefs";
    private static final String Havemoney = "Havemoney";
    private static final String Inittime = "Inittime";
    Date date = new Date();
    long now = date.getTime();
    static long time = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcard);
        outgodbhelper = new Outgodbhelper(this);
        datePickerDialogFragment = new DatePickerDialogFragment();
        Button button = (Button) findViewById(R.id.button);
        ButterKnife.bind(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences(PREFS_FILE, Activity.MODE_PRIVATE);
                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                int number = spinner.getSelectedItemPosition();
                String category = spinner.getSelectedItem().toString();
                EditText n = (EditText) findViewById(R.id.editText2);
                Editable productname = n.getText();
                EditText p = (EditText) findViewById(R.id.editText);
                Editable price = p.getText();
                SharedPreferences inittime = getSharedPreferences(Inittime, Activity.MODE_PRIVATE);
                Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
                int position = spinner2.getSelectedItemPosition();
                if (position == 0) {
                    Integer havemoney = prefs.getInt(Havemoney, 0);
                    Integer remainingmoney = 0;
                    SQLiteDatabase sqLiteDatabase = null;
                    Outgodbhelper outgodbhelper = new Outgodbhelper(AddActivity.this);
                    List<DbContainer> l = outgodbhelper.getContainers();
                    Integer zandaka ;
                    if (l.size()==0){
                        String nedan = price.toString();
                        nedan = category == "income" ? "+" + nedan : "-"+nedan;
                        remainingmoney = havemoney - Integer.valueOf(nedan);
                        outgodbhelper.insertValues(new DbContainer(
                                spinner.getSelectedItem().toString(),
                                productname.toString(),
                                Integer.valueOf(price.toString()),
                                remainingmoney,
                                (time == -1) ? new Date().getTime() : time
                        ));


                    }else {

                        outgodbhelper.insertValues(new DbContainer(
                                spinner.getSelectedItem().toString(),
                                productname.toString(),
                                Integer.valueOf(price.toString()),
                                remainingmoney,
                                (time == -1) ? new Date().getTime() : time


                        ));
                        List<DbContainer> l1 = outgodbhelper.getContainers();
                        for (int i = 1; i <= l1.size() -1  ; i++) {

                            int nowmoney = l1.get(i-1).remainingmoney ;
                            SQLiteDatabase sqLiteDatabase1 = new Outgodbhelper(AddActivity.this).getWritableDatabase();
                            int j = i+1;





                            if (Objects.equals(l1.get(i).category, "income")) {
                                zandaka = nowmoney + l1.get(i).price;
                                sqLiteDatabase1.execSQL("UPDATE " + TABLE_NAME + " SET " + REMAININGMONEY_KEY + "=" + zandaka.toString() + " WHERE " + " rowid = " + j + ";");

                            }else if (Objects.equals(l1.get(i).category, "outgo")) {
                                zandaka = nowmoney - l1.get(i).price;
                                sqLiteDatabase1.execSQL("UPDATE " + TABLE_NAME + " SET " + REMAININGMONEY_KEY + "=" + zandaka.toString() + " WHERE " +" rowid = " + j + ";");


                            }
                        }
                    }




                    Boolean toggleswitch = PreferenceManager.getDefaultSharedPreferences(AddActivity.this).getBoolean("switch_preference", false);
                    if (toggleswitch == Boolean.TRUE) {
                        StringBuilder stringBuilder = new StringBuilder();
                        String[] column = new String[l.size()];
                        for (int i = 0; i < l.size()-1; i++) {
                            if (i == 0) {
                                stringBuilder.append("category,product,price,remainingmoney\n");
                            } else {
                                column[i] = l.get(i).category + "," + l.get(i).productname + "," + l.get(i).price + "," + l.get(i).remainingmoney + "\n";
                                stringBuilder.append(column[i]);
                            }
                        }
                        try {
                            File file = getCSVDir("csv.csv");
                            FileWriter fileWriter = new FileWriter(file);
                            fileWriter.write(stringBuilder.toString());
                            fileWriter.flush();
                            fileWriter.close();
                        } catch (FileNotFoundException e) {
                            try {
                                new ProcessBuilder("mkdir", "/sdcard/Livres").start();


                            } catch (IOException ignored) {
                            }
                            try {
                                new ProcessBuilder("touch", "/sdcard/Livres/csv.csv").start();
                            } catch (IOException ignored) {
                            }
                            File file = getCSVDir("csv.csv");
                            FileWriter fileWriter = null;
                            try {
                                fileWriter = new FileWriter(file);
                                fileWriter.write(stringBuilder.toString());
                                fileWriter.flush();
                                fileWriter.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    finish();
                } else {
                    Long innittime = inittime.getLong(Inittime,0);
                    AddActivity.DatePickerDialogFragment datePickerDialogFragment1 = new AddActivity.DatePickerDialogFragment();
                    if (Long.valueOf(innittime) >= datePickerDialogFragment1.gettime()) {

                        Toast.makeText(AddActivity.this,"日付が古すぎます。",LENGTH_LONG).show();
                    } else {

                        Integer havemoney = prefs.getInt(Havemoney, 0);
                        Integer remainingmoney = 0;
                        Outgodbhelper outgodbhelper = new Outgodbhelper(AddActivity.this);
                        List<DbContainer> l = outgodbhelper.getContainers();

                        Integer zandaka = 0;
                        if (l.size()==0){
                            String nedan = price.toString();
                            nedan = category == "income" ? "+" + nedan : "-"+nedan;
                            remainingmoney = havemoney - Integer.valueOf(nedan);
                            outgodbhelper.insertValues(new DbContainer(
                                    spinner.getSelectedItem().toString(),
                                    productname.toString(),
                                    Integer.valueOf(price.toString()),
                                    remainingmoney,
                                    (time == -1) ? new Date().getTime() : time
                            ));
                        }
                        else{
                            outgodbhelper.insertValues(new DbContainer(
                                    spinner.getSelectedItem().toString(),
                                    productname.toString(),
                                    Integer.valueOf(price.toString()),
                                    remainingmoney,
                                    (time == -1) ? new Date().getTime() : time


                            ));


                            List<DbContainer> l1 = outgodbhelper.getContainers();
                            for (int i = 1; i <= l1.size() -1  ; i++) {

                                int nowmoney = l1.get(i-1).remainingmoney ;
                                SQLiteDatabase sqLiteDatabase1 = new Outgodbhelper(AddActivity.this).getWritableDatabase();
                                int j = i+1;





                                if (Objects.equals(l1.get(i).category, "income")) {
                                    zandaka = nowmoney + l1.get(i).price;
                                    sqLiteDatabase1.execSQL("UPDATE " + TABLE_NAME + " SET " + REMAININGMONEY_KEY + "=" + zandaka.toString() + " WHERE " + " rowid = " + j + ";");

                                }else if (Objects.equals(l1.get(i).category, "outgo")) {
                                    zandaka = nowmoney - l1.get(i).price;
                                    sqLiteDatabase1.execSQL("UPDATE " + TABLE_NAME + " SET " + REMAININGMONEY_KEY + "=" + zandaka.toString() + " WHERE " +" rowid = " + j + ";");


                                }
                            }
                        }




                        Boolean toggleswitch = PreferenceManager.getDefaultSharedPreferences(AddActivity.this).getBoolean("switch_preference", false);
                        if (toggleswitch == Boolean.TRUE) {
                            StringBuilder stringBuilder = new StringBuilder();
                            String[] column = new String[l.size()];
                            for (int i = 0; i < l.size(); i++) {
                                if (i == 0) {
                                    stringBuilder.append("category,product,price,remainingmoney\n");
                                } else {
                                    column[i] = l.get(i).category + "," + l.get(i).productname + "," + l.get(i).price + "," + l.get(i).remainingmoney + "\n";
                                    stringBuilder.append(column[i]);
                                }
                            }
                            try {
                                File file = getCSVDir("csv.csv");
                                FileWriter fileWriter = new FileWriter(file);
                                fileWriter.write(stringBuilder.toString());
                                fileWriter.flush();
                                fileWriter.close();
                            } catch (FileNotFoundException e) {
                                try {
                                    new ProcessBuilder("mkdir", "/sdcard/Livres").start();


                                } catch (IOException ignored) {
                                }
                                try {
                                    new ProcessBuilder("touch", "/sdcard/Livres/csv.csv").start();
                                } catch (IOException ignored) {
                                }
                                File file = getCSVDir("csv.csv");
                                FileWriter fileWriter = null;
                                try {
                                    fileWriter = new FileWriter(file);
                                    fileWriter.write(stringBuilder.toString());
                                    fileWriter.flush();
                                    fileWriter.close();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        finish();
                    }
                }
            }
        });
    }


            public File getCSVDir(String CSV) {
                // Get the directory for the user's public pictures directory.
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        "Livres"), CSV);
                return file;
            }

            @OnItemSelected(R.id.spinner2)
            public void onItemSelectedHogeSpinner(Spinner spinner) {

                // リストの何番目が選択されたか
                int position = spinner.getSelectedItemPosition();
                // 選択されたアイテム名
                String item = (String) spinner.getSelectedItem();
                if (position == 1) {
                    DatePickerDialogFragment datePicker = new DatePickerDialogFragment();
                    datePicker.show(getSupportFragmentManager(), "datePicker");



                }

            }

            public static class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

                long time = 1000000000;
                @Override
                public Dialog onCreateDialog(Bundle savedInstanceState) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);

                    return datePickerDialog;
                }


                public void onDateSet(DatePicker view, final int year, final int month, final int day) {
                    final Calendar c = Calendar.getInstance();
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int minute = c.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {


                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            SimpleDateFormat format = new SimpleDateFormat();
                            format.applyPattern("yyyy/MM/dd/HH:mm");
                            try {

                                Date d = format.parse(String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day) + "/" + String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
                                time = d.getTime() / 1000;



                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                        }


                    }, hour, minute, true);
                    timePickerDialog.show();

                }
                public long gettime(){
                    return this.time;
                }


            }




    }

