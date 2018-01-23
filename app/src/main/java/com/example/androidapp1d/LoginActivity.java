package com.example.androidapp1d;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.androidapp1d.Prof.Feed.ProfFeedActivity;
import com.example.androidapp1d.Prof.Profile.ProfRegistration;
import com.example.androidapp1d.Stud.Feed.StudFeedActivity;
import com.example.androidapp1d.Stud.Profile.StudRegistration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private RadioButton student;
    private RadioButton prof;
    private Button enter;
    private Button register;
    private EditText username, pw;
    public static ArrayList<String> list2; //list of modules

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference profRef, studRef;

    public static boolean accept1 = false; // accept button for consultations in prof feed
    public static boolean reject1 = false; // reject button for consultations in prof feed
    public static ArrayList<String> list; // list of blocked students

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        enter = (Button) findViewById(R.id.enterapp);
        register = (Button) findViewById(R.id.signupbtn);
        student = (RadioButton) findViewById(R.id.student);
        prof = (RadioButton) findViewById(R.id.prof);
        username = (EditText) findViewById(R.id.enterUsername);
        pw = (EditText) findViewById(R.id.enterPassword);

        list2 = new ArrayList<String>(); //DO NOT DELETE (for prof modules page)
        list = new ArrayList<String>(); // DO NOT DELETE (for prof reporting students page)

        firebaseDatabase = FirebaseDatabase.getInstance();
        profRef = firebaseDatabase.getReference().child("Professors");
        studRef = firebaseDatabase.getReference().child("Students");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student.isChecked()) {
                    Intent intent = new Intent(LoginActivity.this, StudRegistration.class);
                    startActivity(intent);
                } else if (prof.isChecked()) {
                    Intent intent = new Intent(LoginActivity.this, ProfRegistration.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(LoginActivity.this, "Please check either student or prof", Toast.LENGTH_SHORT).show();
                }
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student.isChecked()) {
                    studRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(pw.getText().toString().equals(
                                    dataSnapshot.child(username.getText().toString()).child("password").getValue(String.class))){
                                Intent intent = new Intent(LoginActivity.this, StudFeedActivity.class);
                                intent.putExtra("creator", username.getText().toString());
                                startActivity(intent);
                            } else{
                                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                } else if (prof.isChecked()) {
                    profRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(pw.getText().toString().equals(
                                    dataSnapshot.child(username.getText().toString()).child("password").getValue(String.class))){
                                Intent intent = new Intent(LoginActivity.this, ProfFeedActivity.class);
                                intent.putExtra("creator", username.getText().toString());
                                startActivity(intent);
                            } else{
                                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                } else{
                    Toast.makeText(LoginActivity.this, "Please check either student or prof", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

