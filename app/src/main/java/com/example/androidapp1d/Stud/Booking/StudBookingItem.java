package com.example.androidapp1d.Stud.Booking;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

/**
 * Created by ASUS on 12/5/2017.
 */

public class StudBookingItem {
    private String title;
    private String prof;
    private String venue;
    private String date;
//    private ArrayList<String> students = new ArrayList<>();
    private String description;
    private String mod;
    private Integer capacity;
    private long timestamp;
    private String timing;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference bookingsDatabaseReference;

    public StudBookingItem() {
    }

    public StudBookingItem(Integer id) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        bookingsDatabaseReference = firebaseDatabase.getReference().child("Bookings").child(id.toString());
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

    public String getMod() {
        bookingsDatabaseReference.child("mod").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mod = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return mod;
    }

    public Integer getCapacity() {
        bookingsDatabaseReference.child("capacity").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                capacity = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return capacity;
    }

    public long getTimestamp() {
        bookingsDatabaseReference.child("timestamp").startAt(new Date().getTime()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                timestamp = dataSnapshot.getValue(Long.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return timestamp;
    }

    public String getTiming() {
//        DateFormat formatter = new SimpleDateFormat("HH:mm");
//        time = formatter.format(timestamp);
        bookingsDatabaseReference.child("timing").startAt(new Date().getTime()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                timing = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return timing;
    }
}
