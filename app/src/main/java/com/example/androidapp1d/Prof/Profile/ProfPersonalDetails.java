package com.example.androidapp1d.Prof.Profile;

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

public class ProfPersonalDetails extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference studRef;
    private DataSnapshot officehoursRef;
    private TextView name, pillaryear, biblio, modules, email, officehours, officeloc;
    private String pillaryearS, biblioS, modulesS, emailS, officehoursS, officelocS;
    private String CREATOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.prof_personaldetails);

            getSupportActionBar().setTitle("Personal Details");

            Intent i = this.getIntent();
            CREATOR = i.getStringExtra("studentName");

            firebaseDatabase = FirebaseDatabase.getInstance();
            studRef = firebaseDatabase.getReference().child("Professors").child(CREATOR);

            name = (TextView) findViewById(R.id.display_name);
            pillaryear = (TextView) findViewById(R.id.profPillar);
            biblio = (TextView) findViewById(R.id.bibliography_details);
            modules = (TextView) findViewById(R.id.modules_taught_details);
            email = (TextView) findViewById(R.id.email_details);
            officehours = (TextView) findViewById(R.id.office_hours_details);
            officeloc = (TextView) findViewById(R.id.office_location_details);

            //get details about prof
            studRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        pillaryearS = dataSnapshot.child("year").getValue(String.class);
                        biblioS = dataSnapshot.child("description").getValue(String.class);
                        modulesS = dataSnapshot.child("mods").getValue(String.class);
                        emailS = dataSnapshot.child("email").getValue(String.class);
                        officelocS = dataSnapshot.child("office").getValue(String.class);
                        officehoursRef = dataSnapshot.child("Preferences").child("availability");
                        for (DataSnapshot day : officehoursRef.getChildren()) {
                            officehoursS += day.getKey() + " ";
                            for (DataSnapshot time : day.getChildren()) {
                                officehoursS += time.getValue(String.class) + " ";
                            }
                            officehoursS += "\n";
                        }

                        name.setText(CREATOR);
                        pillaryear.setText(pillaryearS);
                        biblio.setText(biblioS);
                        modules.setText(modulesS);
                        email.setText(emailS);
                        officeloc.setText(officelocS);
                        officehours.setText(officehoursS);
                    } catch (Exception e){
                        Toast.makeText(ProfPersonalDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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