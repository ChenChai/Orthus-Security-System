package com.example.orthusapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// TODO implement recyclerView

public class MainActivity extends AppCompatActivity {

    // these will hold instances of the Firebase auth variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private String userUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    @Override
    public void onStart() {
        super.onStart();

        // check if current user is authenticated
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) { // send user to login activity
            startActivity(new Intent(this, LoginActivity.class));
            finish(); // stop this activity so the user can't return to it
        } else {
            userUid = mFirebaseUser.getUid();
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        if(userUid != null) {
            mDatabaseReference = mFirebaseDatabase.getReference("Users/" + userUid);
            mDatabaseReference.child("testChild").setValue("Online!");
        }
    }
    // called when logout button is clicked
    public void logoutMe(View view){
        mFirebaseAuth.signOut();

        // send user back to login activity
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void playAlarm(){

    }

}
