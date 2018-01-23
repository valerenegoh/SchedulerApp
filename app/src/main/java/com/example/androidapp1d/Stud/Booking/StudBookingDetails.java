package com.example.androidapp1d.Stud.Booking;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp1d.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/5/2017.
 */

public class StudBookingDetails extends AppCompatActivity{

    private TabHost tabHost;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference bookingRef;
    private String bookingID, CREATOR;
    private String title, description, mod, date, time, prof, venue;
    private ArrayList<String> students = new ArrayList<>();
    private int cap, seatsLeft;
    private TextView descriptionView, modView, dateView, timeView, profView, venueView, availView;
    private Button crash;
    private AlertDialog.Builder crashbuilder;
    private ListView members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.stud_bookingdetails);

            descriptionView = (TextView) findViewById(R.id.description_details);
            modView = (TextView) findViewById(R.id.modules_taught_details);
            dateView = (TextView) findViewById(R.id.date_details);
            timeView = (TextView) findViewById(R.id.time_details);
            profView = (TextView) findViewById(R.id.prof_details);
            venueView = (TextView) findViewById(R.id.office_location_details);
            availView = (TextView) findViewById(R.id.avail_details);
            crash = (Button) findViewById(R.id.crashButton);
            members = (ListView) findViewById(R.id.membersList);


            Intent i = this.getIntent();
            CREATOR = i.getStringExtra("creator");

            tabHost = (TabHost) findViewById(R.id.bookingDetailsTab);

            tabHost.setup();
            TabHost.TabSpec detailsTab = tabHost.newTabSpec("DETAILS");
            detailsTab.setIndicator("DETAILS");
            detailsTab.setContent(R.id.Details);
            tabHost.addTab(detailsTab);

            final TabHost.TabSpec membersTab = tabHost.newTabSpec("MEMBERS");
            membersTab.setIndicator("MEMBERS");
            membersTab.setContent(R.id.Members);
            tabHost.addTab(membersTab);

            TabHost.TabSpec commentsTab = tabHost.newTabSpec("COMMENTS");
            commentsTab.setIndicator("COMMENTS");
            commentsTab.setContent(R.id.Comments);
            tabHost.addTab(commentsTab);

            Intent j = this.getIntent();
            bookingID = j.getStringExtra("bookingID");

            firebaseDatabase = FirebaseDatabase.getInstance();
            bookingRef = firebaseDatabase.getReference().child("Bookings");

            bookingRef.child(bookingID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        title = dataSnapshot.child("title").getValue(String.class);
                        description = dataSnapshot.child("description").getValue(String.class);
                        mod = dataSnapshot.child("mod").getValue(String.class);
                        date = dataSnapshot.child("date").getValue(String.class);
                        time = dataSnapshot.child("time").getValue(String.class);
                        for (DataSnapshot aStudent : dataSnapshot.child("students").getChildren()) {
                            students.add(aStudent.getValue(String.class));
                        }
                        prof = dataSnapshot.child("prof").getValue(String.class);
                        cap = dataSnapshot.child("time").getValue(Integer.class);
                        venue = dataSnapshot.child("time").getValue(String.class);

                        getSupportActionBar().setTitle(title);
                        //details tab
                        seatsLeft = cap - students.size();
                        descriptionView.setText(description);
                        modView.setText(mod);
                        dateView.setText(date);
                        timeView.setText(time);
                        profView.setText(prof);
                        venueView.setText(venue);
                        availView.setText(getString(R.string.seatsavail, seatsLeft));

                        if (seatsLeft == 0) {
                            crash.setText("");
                        } else {
                            crash.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    crashbuilder = new AlertDialog.Builder(StudBookingDetails.this);
                                    crashbuilder.setTitle("Confirm crash?");
                                    crashbuilder.setMessage("You will be added as a member to the consultation group and receive notifications.");
                                    crashbuilder.setCancelable(false);
                                    crashbuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            bookingRef.child(bookingID).child("students").push().setValue(CREATOR);
                                            Toast.makeText(StudBookingDetails.this, "You have been added to " + title, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    crashbuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    crashbuilder.create().show();
                                }
                            });
                        }

                        //members tab
                        final ArrayAdapter<String> lAdapter1 = new ArrayAdapter<>(StudBookingDetails.this, android.R.layout.simple_list_item_1, students);
                        members.setAdapter(lAdapter1);

                        //comments tab
                    } catch(Exception e){
                        Toast.makeText(StudBookingDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}