package com.example.androidapp1d.Stud.Booking;

import android.content.Context;
import android.widget.Toast;

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

    public StudBookingItem(Context mcontext, String id, boolean isPending) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        if(!isPending) {
            bookingsDatabaseReference = firebaseDatabase.getReference().child("Bookings").child(id);
        } else{
            bookingsDatabaseReference = firebaseDatabase.getReference().child("pendingBookings").child(id);
        }
        final Context context = mcontext;

        try {
            bookingsDatabaseReference.child("title").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        title = dataSnapshot.getValue(String.class);
                    } catch(Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            bookingsDatabaseReference.child("prof").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        prof = dataSnapshot.getValue(String.class);
                    } catch(Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            bookingsDatabaseReference.child("venue").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        venue = dataSnapshot.getValue(String.class);
                    }catch(Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            bookingsDatabaseReference.child("date").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        date = dataSnapshot.getValue(String.class);
                    } catch(Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            bookingsDatabaseReference.child("description").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        description = dataSnapshot.getValue(String.class);
                    }catch(Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            bookingsDatabaseReference.child("mod").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        mod = dataSnapshot.getValue(String.class);
                    }catch(Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            bookingsDatabaseReference.child("capacity").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        capacity = dataSnapshot.getValue(Integer.class);
                    }catch(Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            bookingsDatabaseReference.child("timestamp").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        timestamp = dataSnapshot.getValue(Long.class);
                    }catch(Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            bookingsDatabaseReference.child("timing").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        timing = dataSnapshot.getValue(String.class);
                    }catch(Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            bookingsDatabaseReference.child("students").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        for (DataSnapshot aStud : dataSnapshot.getChildren()) {
                            students.add(aStud.getValue(String.class));
//                    Toast.makeText(context, title + ", " + prof + ", " +
//                            venue + ", " + date + ", " + description + ", " + mod + ", " +
//                            String.valueOf(capacity) + ", " + Long.toString(timestamp) + ", " + timing + ", " +
//                            students.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
