package com.example.androidapp1d.Stud.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp1d.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ASUS on 12/7/2017.
 */

public class StudPersonalDetails extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference studRef;
    private TextView studentID, name, pillaryear, biblio, modules, email;
    private String studentIDS, pillaryearS, biblioS, modulesS, emailS;
    private String studName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.stud_profdetails);

            getSupportActionBar().setTitle("Personal Details");

            Intent i = this.getIntent();
            studName = i.getStringExtra("studentName");

            firebaseDatabase = FirebaseDatabase.getInstance();
            studRef = firebaseDatabase.getReference().child("Students").child(studName);

            name = (TextView) findViewById(R.id.display_name);
            pillaryear = (TextView) findViewById(R.id.studentPillar);
            biblio = (TextView) findViewById(R.id.bibliography_details);
            modules = (TextView) findViewById(R.id.modules_taught_details);
            studentID = (TextView) findViewById(R.id.studentID);
            email = (TextView) findViewById(R.id.email_details);

            //get details about prof
            studRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        studentIDS = dataSnapshot.child("studentID").getValue(Integer.class).toString();
                        pillaryearS = dataSnapshot.child("year").getValue(String.class);
                        biblioS = dataSnapshot.child("description").getValue(String.class);
                        modulesS = dataSnapshot.child("mods").getValue(String.class);
                        emailS = dataSnapshot.child("email").getValue(String.class);

                        studentID.setText(studentIDS);
                        name.setText(studName);
                        pillaryear.setText(pillaryearS);
                        biblio.setText(biblioS);
                        modules.setText(modulesS);
                        email.setText(emailS);
                    } catch (Exception e){
                        Toast.makeText(StudPersonalDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}