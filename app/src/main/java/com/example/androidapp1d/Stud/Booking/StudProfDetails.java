package com.example.androidapp1d.Stud.Booking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidapp1d.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ASUS on 12/7/2017.
 */

public class StudProfDetails extends AppCompatActivity {

    private Button consult;
    private FirebaseDatabase firebaseDatabase;
    private DataSnapshot officehoursRef;
    private DatabaseReference profRef;
    private TextView name, pillaryear, biblio, modules, officeloc, officehours, email;
    private String pillaryearS, biblioS, modulesS, officelocS, officehoursS = "", emailS, profName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stud_profdetails);

        getSupportActionBar().setTitle("Personal Details");

        Intent i = this.getIntent();
        profName = i.getStringExtra("profName");

        name = (TextView) findViewById(R.id.display_name);
        pillaryear = (TextView) findViewById(R.id.profPillar);
        biblio = (TextView) findViewById(R.id.bibliography_details);
        modules = (TextView) findViewById(R.id.modules_taught_details);
        officeloc = (TextView) findViewById(R.id.office_location_details);
        officehours = (TextView) findViewById(R.id.office_hours_details);
        email = (TextView) findViewById(R.id.email_details);

        //go to prof booking slot page
        consult = (Button) findViewById(R.id.consultButton);
        consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudProfDetails.this, StudBookingProfSlot.class);
                i.putExtra("profName", profName);
                startActivity(i);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        profRef = firebaseDatabase.getReference().child("Professors").child(profName);

        //get details about prof
        profRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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

                name.setText(profName);
                pillaryear.setText(pillaryearS);
                biblio.setText(biblioS);
                modules.setText(modulesS);
                officeloc.setText(officelocS);
                officehours.setText(officehoursS);
                email.setText(emailS);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}