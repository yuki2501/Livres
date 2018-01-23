package com.example.shitenshi.livres;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by masuken111 on 2017/12/31.
 */

class Theme {
    private static String getClassName(){
        return Thread.currentThread().getStackTrace()[2].getClassName();
    }
    void themeset(Context context){
         String listpre = PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString("list_preference_1","akari");
        switch (listpre){
            case "oumurasaki":
                if (getClassName().equals("AddActivity")) {
                    context.setTheme(R.style.AppTheme);
                }
                if (getClassName().equals("PreferenceActivity")) {
                    context.setTheme(R.style.AppTheme_PreferenceTheme);
                }else{
                context.setTheme(R.style.AppTheme_NoActionBar);
                }
            case "akari":
                if (getClassName().equals("AddActivity")) {
                    context.setTheme(R.style.AppTheme_Akari);
                }
                if (getClassName().equals("PreferenceActivity")) {
                    context.setTheme(R.style.AppTheme_PreferenceTheme_Akari);
                }else{
                    context.setTheme(R.style.AppTheme_Akari_NoActionBar);
                }
            case "kaeru":
                if (getClassName().equals("AddActivity")) {
                    context.setTheme(R.style.AppTheme_kaeru);
                }
                if (getClassName().equals("PreferenceActivity")) {
                    context.setTheme(R.style.AppTheme_PreferenceTheme_kaeru);
                }else{
                    context.setTheme(R.style.AppTheme_kaeru_NoActionBar);
                }
        }
    }

}
