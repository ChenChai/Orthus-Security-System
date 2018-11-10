package com.example.orthusapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    // these will hold instances of the Firebase auth variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onStart(){
        super.onStart();

        // check if current user is authenticated
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) { // send user to login activity
            startActivity(new Intent(this, LoginActivity.class));
            finish(); // stop this activity so the user can't return to it
        } else {
            username = mFirebaseUser.getDisplayName();
        }

    }

    // called when logout button is clicked
    public void logoutMe(View view){
        mFirebaseAuth.signOut();

        // send user back to login activity
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
