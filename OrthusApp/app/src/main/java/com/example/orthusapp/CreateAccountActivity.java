package com.example.orthusapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class CreateAccountActivity extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    private boolean accountCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mFirebaseAuth = FirebaseAuth.getInstance();


    }

    // called when user presses button to create account.
    public void createAccount(View view){
        // get email and password
        EditText emailEditText = (EditText) findViewById(R.id.createEmailEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.createPasswordEditText);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // validate email and password
        if (!isEmailValid(email)){
            emailEditText.setError("Please enter a valid email.");
            return;
        }

        if (!isPasswordValid(password)){
            passwordEditText.setError("Password must be at least 6 characters long.");
            return;
        }


        // must have valid email and password.
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mFirebaseUser = mFirebaseAuth.getCurrentUser();

                    Toast.makeText(CreateAccountActivity.this, "Account creation successful! Hello, ", Toast.LENGTH_SHORT).show();
                    accountCreated = true;

                    startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
                    finish();
                } else {
                    accountCreated = false;
                    Toast.makeText(CreateAccountActivity.this, "Error: " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // TODO: remove legacy code
        if(accountCreated){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    // checks if an email is valid
    boolean isEmailValid(String email){
        if (email == null) return false;

        return Patterns.EMAIL_ADDRESS.matcher(email).matches(); // check if email address is valid
    }

    // check if a password is longer than 6 digits.
    boolean isPasswordValid(String password) {
        if (password == null) return false;
        return password.length() >= 6;
    }
}
