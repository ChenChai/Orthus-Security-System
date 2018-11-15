package com.example.orthusapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

// TODO implement recyclerView

public class MainActivity extends AppCompatActivity {

    // these will hold instances of the Firebase auth variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    Switch armedSwitch;
    TextView statusTextView;

    private String userUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        armedSwitch = findViewById(R.id.armedSwitch);
        statusTextView = (TextView) findViewById(R.id.statusTextView);
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

            // listen for any alerts happening

            mDatabaseReference.child("alert").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if((long) dataSnapshot.getValue() == 0){
                        handleAlarm();
                    } else {
                        turnOffAlarm();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    statusTextView.setText(R.string.status_offline_text);
                    statusTextView.setTextColor(getResources().getColor(R.color.colorOffline));
                }
            });

        }
    }

    @Override
    public void onPause(){
        super.onPause();

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    // called when logout button is clicked
    public void logoutMe(View view){
        mFirebaseAuth.signOut();

        // send user back to login activity
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    // called when value changes in database
    private void handleAlarm(){

        // if the system is armed then launch an entirely new activity
        if(armedSwitch.isChecked()) {
            Intent alarmIntent = new Intent(this, AlertActivity.class);
            alarmIntent.putExtra("alert_type", 1);
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // makes sure user doesn't have to deal with a bunch of instances of AlertActivity
            startActivity(alarmIntent);
        }

        // otherwise simply alert them through text.
        statusTextView.setText(R.string.status_alarm_text);
        statusTextView.setTextColor(getResources().getColor(R.color.colorAlarm));


    }

    private void turnOffAlarm(){
        statusTextView.setText(R.string.status_clear_text);
        statusTextView.setTextColor(getResources().getColor(R.color.colorClear));
        mDatabaseReference.child("alert").setValue(0);
    }
}
