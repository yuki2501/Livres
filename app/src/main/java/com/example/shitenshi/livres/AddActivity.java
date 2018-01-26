package com.example.shitenshi.livres;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnItemSelected;

import static android.widget.Toast.LENGTH_LONG;
public class AddActivity extends AppCompatActivity {
    private static final int MAIN_ACTIVITY = 101;
    Outgodbhelper outgodbhelper = new Outgodbhelper(this);
    DatePickerDialogFragment datePickerDialogFragment;
    private static final String PREFS_FILE = "HMPrefs";
    private static final String Havemoney = "Havemoney";
    private static final String Inittime = "Inittime";
    static long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Theme theme = new Theme();
        theme.themeset(this);
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
                    Outgodbhelper outgodbhelper = new Outgodbhelper(AddActivity.this);
                    List<DbContainer> l = outgodbhelper.getContainers(havemoney);
                    Integer zandaka;
                    if (l.size() == 0) {
                        String nedan = (Objects.equals(category, "income") ? "+" : "-") + price;
                        remainingmoney = havemoney + Integer.valueOf(nedan);
                        outgodbhelper.insertValues(new DbContainer(
                                spinner.getSelectedItem().toString(),
                                productname.toString(),
                                Integer.valueOf(price.toString()),
                                remainingmoney,
                                new Date().getTime()
                        ));
                    } else {

                        outgodbhelper.insertValues(new DbContainer(
                                spinner.getSelectedItem().toString(),
                                productname.toString(),
                                Integer.valueOf(price.toString()),
                                remainingmoney,
                                new Date().getTime()
                        ));
                        outgodbhelper.replacedb(havemoney);
                    }
                    csvoutput();
                    finish();
                } else {
                    long innittime = inittime.getLong(Inittime, 0);
                    if (innittime >= time) {

                        Toast.makeText(AddActivity.this, "日付が古すぎます。", LENGTH_LONG).show();
                    } else {

                        Integer havemoney = prefs.getInt(Havemoney, 0);
                        Integer remainingmoney = 0;
                        ;
                        Outgodbhelper outgodbhelper = new Outgodbhelper(AddActivity.this);
                        List<DbContainer> l = outgodbhelper.getContainers(havemoney);

                        Integer zandaka;
                        if (l.size() == 0) {
                            String nedan = (Objects.equals(category, "income") ? "+" : "-") + price;
                            remainingmoney = havemoney + Integer.valueOf(nedan);
                            outgodbhelper.insertValues(new DbContainer(
                                    spinner.getSelectedItem().toString(),
                                    productname.toString(),
                                    Integer.valueOf(price.toString()),
                                    remainingmoney,
                                    time
                            ));
                        } else {
                            outgodbhelper.insertValues(new DbContainer(
                                    spinner.getSelectedItem().toString(),
                                    productname.toString(),
                                    Integer.valueOf(price.toString()),
                                    remainingmoney,
                                    time
                            ));
                            outgodbhelper.replacedb(havemoney);
                        }
                        csvoutput();
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
    public void csvoutput(){
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, Activity.MODE_PRIVATE);
        Boolean toggleswitch = PreferenceManager.getDefaultSharedPreferences(AddActivity.this).getBoolean("switch_preference", false);
        if (toggleswitch == Boolean.TRUE) {
            int havemoney = prefs.getInt(Havemoney,0);
            StringBuilder stringBuilder = new StringBuilder();
            List<DbContainer> l = outgodbhelper.getContainers(havemoney);
            String[] column = new String[l.size()];
           for (int i = 0; i < l.size() - 1; i++) {
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
                } catch (IOException ignored) {}
                try {
                    new ProcessBuilder("touch", "/sdcard/Livres/csv.csv").start();
                } catch (IOException ignored) {}
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
    @Override
    protected void onResume(){
        super.onResume();
        Theme theme = new Theme();
        theme.themeset(this);

    }
    public static class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
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
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat();
                    format.applyPattern("yyyy/MM/dd/HH:mm");
                    try {
                        String aaa = String.valueOf(year) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(day) + "/" + String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                        Date d = format.parse(aaa);
                        AddActivity.time = d.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, hour, minute, true);
            timePickerDialog.show();
        }
    }
}

