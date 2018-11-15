package com.example.orthusapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AlertActivity extends AppCompatActivity {

    TextView alertTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        int alertType = getIntent().getIntExtra("alert_type",0);

        alertTextView = (TextView) findViewById(R.id.alertTextView);

        alertTextView.setText(Integer.toString(alertType));
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish(); // once the user navigates out, we have no more need for this activity.
    }
}
