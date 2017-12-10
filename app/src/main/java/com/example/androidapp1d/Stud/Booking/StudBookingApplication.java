package com.example.androidapp1d.Stud.Booking;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp1d.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/8/2017.
 */

public class StudBookingApplication extends AppCompatActivity {

    private static final String PROF = "uniqueID";
    private static final String TIME = "2:00PM-2:30PM";
    private static final String CREATOR = "User1";
    private static final String DATE = "Friday, 8 December 2017";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference capacityRef;
    private DatabaseReference modulesRef;
    private DatabaseReference venueRef;
    private DatabaseReference addToPendingRef;
    private DatabaseReference addtoCreatorRef;
    private DatabaseReference addtoProfRef;

    private EditText title, description, numSeats;
    private Spinner chooseModule;
    private RadioGroup chooseSize;
    private String titleInput, descriptionInput;
    private Integer numSeatsInput;
    private Integer defaultCap;
    private String chosenMod;
    private ArrayList<String> mods = new ArrayList<String>(){{add("Choose module");}};
    private String venue;
    private StudBookingCreateItem newbooking;
    private Button apply;
    private Button cancel;
    private String key;
    private TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.stud_bookingapplication);

            title = (EditText) findViewById(R.id.bookingTitleText);
            description = (EditText) findViewById(R.id.bookingDescriptionText);
            numSeats = (EditText) findViewById(R.id.varySizeText);
            chooseModule = (Spinner) findViewById(R.id.choose_modules);
            chooseSize = (RadioGroup) findViewById(R.id.consultationSize);
            location = (TextView) findViewById(R.id.location_details);
            apply = (Button) findViewById(R.id.applybooking_button);
            cancel = (Button) findViewById(R.id.cancelbooking_button);

            chooseModule.setSelection(0);

            firebaseDatabase = FirebaseDatabase.getInstance();
            capacityRef = firebaseDatabase.getReference().child("Students").child(CREATOR).child("Preferences/capacity");
            modulesRef = firebaseDatabase.getReference().child("Students").child(CREATOR).child("mods");
            venueRef = firebaseDatabase.getReference().child("Professors").child(PROF).child("Preferences/venue");
            addToPendingRef = firebaseDatabase.getReference().child("pendingBookings");
            addtoCreatorRef = firebaseDatabase.getReference().child("Students").child(CREATOR).child("allBookings");
            addtoProfRef = firebaseDatabase.getReference().child("Professors").child(PROF).child("pendingBookings");

            //get all mods of student
            modulesRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    mods.add(dataSnapshot.getValue(String.class));
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            //set mod drop down view
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mods);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chooseModule.setAdapter(adapter);

            //get default capacity preference of student
            capacityRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    defaultCap = dataSnapshot.getValue(Integer.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            //choose flexible or default capacity
            chooseSize.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        switch (checkedId) {
                            case R.id.defaultSize:
                                numSeats.setEnabled(false);
                                numSeats.setVisibility(View.INVISIBLE);
                                break;
                            case R.id.flexibleSize:
                                numSeats.setEnabled(true);
                                numSeats.setVisibility(View.VISIBLE);
                                numSeats.requestFocus();
                                break;
                        }
                    }
                });

            //get venue
            venueRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    venue = dataSnapshot.getValue(String.class);
                    location.setText(venue);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            //apply booking button
            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    titleInput = title.getText().toString();
                    descriptionInput = description.getText().toString();
                    chosenMod = chooseModule.getSelectedItem().toString().trim();
                    if(chosenMod.equals("Choose module")){
                        Toast.makeText(StudBookingApplication.this, "Select a module", Toast.LENGTH_SHORT).show();
                    } else {
                        if (chooseSize.getCheckedRadioButtonId() == R.id.flexibleSize) {
                            String capacity = numSeats.getText().toString().trim();
                            if (capacity.isEmpty()) {
                                Toast.makeText(StudBookingApplication.this, "Set capacity", Toast.LENGTH_SHORT).show();
                            } else {
                                numSeatsInput = Integer.parseInt(capacity);
                                addToDatabase();
                            }
                        } else if(chooseSize.getCheckedRadioButtonId() == R.id.defaultSize){
                            numSeatsInput = defaultCap;
                            addToDatabase();
                        }
                    }
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: go to previous page
                }
            });
        } catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void addToDatabase(){
        newbooking = new StudBookingCreateItem(titleInput, descriptionInput, TIME, DATE,
                chosenMod, PROF, numSeatsInput, CREATOR, venue);

        key = addToPendingRef.push().getKey();
        addToPendingRef.child(key).setValue(newbooking);
        addtoCreatorRef.push().setValue(key);
        addtoProfRef.push().setValue(key);
    }
}