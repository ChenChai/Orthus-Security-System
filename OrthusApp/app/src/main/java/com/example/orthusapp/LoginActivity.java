package com.example.orthusapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TODO: Let user login with FirebaseAuth

    }

    public void launchCreateAccount(View view){
        startActivity(new Intent(this, CreateAccountActivity.class));
    }

    // called when login button is pressed.
    public void loginMe(View view){
        // TODO: implement authentication
    }

}
