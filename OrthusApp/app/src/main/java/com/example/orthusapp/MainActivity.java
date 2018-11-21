package com.example.orthusapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    // these will hold instances of the Firebase auth variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference userInfoRef;
    private DatabaseReference connectedRef;

    /* preferences are stored in a private file with a specific key. They are stored in a key-value format.*/
    SharedPreferences userPreferences;
    SharedPreferences.Editor userPreferencesEditor;

    Switch armedSwitch;
    TextView statusTextView;

    private String userUid;
    private boolean connected;

    private ListView alertListView;
    ArrayList<String> timestampList = new ArrayList<String>();
    ArrayList<String> sensorList = new ArrayList<String>();
    ArrayList<String> keyList = new ArrayList<String>();
    AlertAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        armedSwitch = (Switch) findViewById(R.id.armedSwitch);
        statusTextView = (TextView) findViewById(R.id.statusTextView);
        alertListView = (ListView) findViewById(R.id.alertListView);

        adapter = new AlertAdapter(this, sensorList, timestampList, keyList);
        alertListView.setAdapter(adapter);

        // get handle to user preferences through preference file key
        userPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), MainActivity.MODE_PRIVATE);
        userPreferencesEditor = userPreferences.edit();

        // send user to login screen if not logged in.
        authenticateUser();
        // adds listeners to see if connected to Firebase
        setupConnectedListener();
        // adds listener to see if Firebase alarm variable is activated
        setupAlertListener();
        // reads armed value from preferences and adds listener to switch
        setupArmedSwitch();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void authenticateUser() {
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
    }

    // called when logout button is clicked
    public void logoutUser(View view){
        mFirebaseAuth.signOut();

        // send user back to login activity
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


    private void setupAlertListener() {
        if(userUid != null) {
            userInfoRef = mFirebaseDatabase.getReference("users/" + userUid);

            userInfoRef.child("alert").setValue(-1);

            // listen for any alerts happening currently
            userInfoRef.child("alert").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if((long) dataSnapshot.getValue() == 1){
                        handleAlarm();
                    } else if ((long) dataSnapshot.getValue() == 0){
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

        // listener for the alert history nodes
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // TODO Handle null exceptions


                // convert firebase timestamp to string
                Date date = new Date((long) dataSnapshot.child("timestamp").getValue());
                String timestamp = date.toLocaleString();

                // Add alert to arraylist
                String sensorId = dataSnapshot.child("sensor").getValue().toString();
                adapter.add(sensorId, timestamp);
                keyList.add(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = keyList.indexOf(key);
                // if the alert exists in our lists, remove it.
                if (index != -1){
                    keyList.remove(index);
                    timestampList.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        // add the listener on to the timestampedAlerts node of Firebase
        FirebaseDatabase.getInstance().getReference("users/" + userUid + "/timeStampedAlerts").addChildEventListener(childEventListener);

    }

    private void setupArmedSwitch() {
        boolean armed = userPreferences.getBoolean(getString(R.string.preference_armed_key), false);

        armedSwitch.setChecked(armed);
        if (armed){
            armedSwitch.setText(R.string.armed_text);
        } else {
            armedSwitch.setText(R.string.disarmed_text);
        }

        // when the switch is changed, update text.
        armedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    armedSwitch.setText(R.string.armed_text);
                    userPreferencesEditor.putBoolean(getString(R.string.preference_armed_key), true);
                    userPreferencesEditor.commit();
                    userInfoRef.child("armed").setValue(1);
                } else {
                    armedSwitch.setText(R.string.disarmed_text);
                    userPreferencesEditor.putBoolean(getString(R.string.preference_armed_key), false);
                    userPreferencesEditor.commit();
                    userInfoRef.child("armed").setValue(0);
                }
            }
        });
    }

    // adds listener to update the status text
    private void setupConnectedListener(){
        connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                connected = dataSnapshot.getValue(Boolean.class);
                if(connected){
                    statusTextView.setText(R.string.status_clear_text);
                    statusTextView.setTextColor(getResources().getColor(R.color.colorClear));
                    armedSwitch.setClickable(true);
                } else {
                    statusTextView.setText(R.string.status_offline_text);
                    statusTextView.setTextColor(getResources().getColor(R.color.colorOffline));
                    armedSwitch.setClickable(false); // do not want the user changing armed status while offline.
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // called when alert value changes in database
    private void handleAlarm(){
        // otherwise simply alert them through text.
        statusTextView.setText(R.string.status_alarm_text);
        statusTextView.setTextColor(getResources().getColor(R.color.colorAlarm));
    }

    private void turnOffAlarm(){
        statusTextView.setText(R.string.status_clear_text); // TODO Refactor code that changes alarm status
        statusTextView.setTextColor(getResources().getColor(R.color.colorClear));
    }

    public void clearAlertHistory(View view){
        if (connected){
            userInfoRef.child("alerts").removeValue();
            userInfoRef.child("timeStampedAlerts").removeValue();
        } else {
            Toast.makeText(this,"Cannot clear cloud data while offline.", Toast.LENGTH_SHORT).show();
        }
    }
}
