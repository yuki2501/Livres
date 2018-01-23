package com.example.shitenshi.livres;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public  class  PreferenceActivity extends AppCompatActivity implements PrefsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        Theme theme = new Theme();
        theme.themeset(this);

    }
    @Override
    protected void onResume(){
        super.onResume();
        Theme theme = new Theme();
        theme.themeset(this);

    }
}
