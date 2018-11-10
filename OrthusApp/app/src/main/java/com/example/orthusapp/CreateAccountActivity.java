package com.example.orthusapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void createAccount(View view){
        EditText emailEditText = (EditText) findViewById(R.id.createEmailEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.createPasswordEditText);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!isEmailValid(email)){
            emailEditText.setError("Please enter a valid email.");
        }

        if (!isPasswordValid(password)){
            passwordEditText.setError("Password must be at least 4 characters long.");
        }


    }

    // checks if an email is valid
    boolean isEmailValid(String email){
        if (email == null) return false;

        return Patterns.EMAIL_ADDRESS.matcher(email).matches(); // check if email address is valid
    }

    // check if a password is longer than 4 digits.
    boolean isPasswordValid(String password) {
        if (password == null) return false;
        return password.length() >= 4;
    }
}
