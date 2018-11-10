package com.example.orthusapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TODO: Let user login with FirebaseAuth

    }

    @Override
    protected void onStart(){
        super.onStart();

        // if the user is already signed in, they don't need the login screen
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            finish();
        }

    }

    public void launchCreateAccount(View view){
        startActivity(new Intent(this, CreateAccountActivity.class));
    }

    // called when login button is pressed.
    public void loginMe(View view){
        // TODO: implement authentication
    }

}
