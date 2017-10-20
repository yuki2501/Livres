package com.example.shitenshi.livres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

public class AddActivity extends AppCompatActivity {
    private static final int MAIN_ACTIVITY = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcard);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText)findViewById(R.id.editText);
                Editable text1 = editText.getText();

                Intent intent1 = new Intent(getApplication(), MainActivity.class);
                //get
                intent1.putExtra("number", text1);
                startActivityForResult(intent1,MAIN_ACTIVITY);

             }



            });
    }
}
