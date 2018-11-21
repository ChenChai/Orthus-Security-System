package com.example.orthusapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ShowUidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_uid);

        EditText uidEditText = (EditText) findViewById(R.id.uidEditText);

        uidEditText.setText(getIntent().getStringExtra("uid"));

    }
}
