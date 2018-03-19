package com.example.shitenshi.livres;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import java.io.IOException;

/**
 * Created by masuken111 on 2017/11/24.
 */

public class PrefsFragment extends PreferenceFragmentCompat{

    private OnFragmentInteractionListener mListener;
    CSVUtil csvUtil = new CSVUtil();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.preferences, rootKey);
        findPreference("import").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                try {
                    csvUtil.csv_to_sqlite(getContext(),Environment.getExternalStorageDirectory().getPath()+"/Livres");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;


            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {}
}