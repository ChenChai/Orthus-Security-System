package com.example.firebase_vertical_prototype;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button plusButton;
    private Button minusButton;
    private TextView numberTextView;
    long num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get an instance of the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("testValue"); // this determines which value to read!

        // associate the buttons in the xml file with these buttons
        plusButton = findViewById(R.id.plusButton);
        minusButton = findViewById(R.id.minusButton);
        numberTextView = findViewById(R.id.numberTextView);


        // set listeners for when the plus and minus buttons are pressed
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = num + 1;
                myRef.setValue(num);
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = num - 1;
                myRef.setValue(num);
            }
        });


        // set a listener for when the realtime database changes
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                num = (long) dataSnapshot.getValue();
                numberTextView.setText((Long.toString(num)));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                numberTextView.setText("Failed to retrieve value!"); // TODO: figure out why this code doesn't run when there's no internet connection
            }
        });


    }
}
