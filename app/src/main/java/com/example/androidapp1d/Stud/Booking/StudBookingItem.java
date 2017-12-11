package com.example.androidapp1d.Stud.Booking;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/5/2017.
 */

public class StudBookingItem {
    private String title;
    private String prof;
    private String venue;
    private String date;
    private ArrayList<String> students = new ArrayList<>();
    private String description;
    private String mod;
    private Integer capacity;
    private long timestamp;
    private String timing;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference bookingsDatabaseReference;

    public StudBookingItem() {
    }

    public StudBookingItem(Context mcontext, String id) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        bookingsDatabaseReference = firebaseDatabase.getReference().child("Bookings").child(id);
        final Context context = mcontext;

        bookingsDatabaseReference.child("title").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                title = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        bookingsDatabaseReference.child("prof").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                prof = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        bookingsDatabaseReference.child("venue").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                venue = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        bookingsDatabaseReference.child("date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                date = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        bookingsDatabaseReference.child("description").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                description = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        bookingsDatabaseReference.child("mod").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mod = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        bookingsDatabaseReference.child("capacity").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                capacity = dataSnapshot.getValue(Integer.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        bookingsDatabaseReference.child("timestamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                timestamp = dataSnapshot.getValue(Long.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        bookingsDatabaseReference.child("timing").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                timing = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        bookingsDatabaseReference.child("students").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot aStud: dataSnapshot.getChildren()){
                    students.add(aStud.getValue(String.class));
//                    Toast.makeText(context, title + ", " + prof + ", " +
//                            venue + ", " + date + ", " + description + ", " + mod + ", " +
//                            String.valueOf(capacity) + ", " + Long.toString(timestamp) + ", " + timing + ", " +
//                            students.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public String getTitle() {
        return title;
    }

    public String getProf() {
        return prof;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getMod() {
        return mod;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTiming() {
        return timing;
    }

    public ArrayList<String> getStudents() {
        return students;
    }
}
