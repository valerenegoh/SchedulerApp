package com.example.androidapp1d;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ASUS on 12/5/2017.
 */

public class StudBookingItem {
    private Integer id;
    private String title;
    private String prof;
    private String venue;
    private String date;
    private String description;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference bookingsDatabaseReference;

    public StudBookingItem() {
    }

    //for testing purposes
    public StudBookingItem(Context context, Integer id) {
        this.id = id;
        firebaseDatabase = FirebaseDatabase.getInstance();
        bookingsDatabaseReference = firebaseDatabase.getReference().child("Bookings");
    }

    public String getTitle() {
        bookingsDatabaseReference.child("title").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                title = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return title;
    }

    public String getProf() {
        bookingsDatabaseReference.child("prof").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                prof = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return prof;
    }

    public String getVenue() {
        bookingsDatabaseReference.child("venue").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                venue = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return venue;
    }

    public String getDate() {
        bookingsDatabaseReference.child("date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                date = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return date;
    }

    public String getDescription() {
        bookingsDatabaseReference.child("description").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                description = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return description;
    }
}
