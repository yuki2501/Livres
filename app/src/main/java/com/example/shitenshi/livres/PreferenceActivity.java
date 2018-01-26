package com.example.shitenshi.livres;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public  class  PreferenceActivity extends AppCompatActivity implements PrefsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Theme theme = new Theme();
        theme.themeset(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);


    }

}
