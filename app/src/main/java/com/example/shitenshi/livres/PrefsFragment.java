package com.example.shitenshi.livres;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by masuken111 on 2017/11/24.
 */

public class PrefsFragment extends PreferenceFragmentCompat{

    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.preferences, rootKey);
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