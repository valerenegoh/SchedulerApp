package com.example.androidapp1d.Stud.Booking;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp1d.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ASUS on 12/7/2017.
 */

public class StudBookingProfSlot extends AppCompatActivity{

    CalendarView calendarView;
    TextView dateView;
    ListView lv;
    private static final String KEY = "Oka Kurniawan";
    private int slotSize = 30;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference availabilityDatabaseReference;
    private DatabaseReference allBookingsDatabaseReference;
    private DatabaseReference queryBookingDatabaseReference;
    private Query queryBookingDatabase;
    private String day;
    private String time;
    private String bookingID;
    private int numStudents;
    private int slotsLeft;
    private String timeSlot;
    private ArrayList<String> timeArray = new ArrayList<>();
    private ArrayList<String> availabilityStatus = new ArrayList<>();
    private ArrayList<String> bookingIDs = new ArrayList<>();
    private ArrayList<Integer> colors = new ArrayList<>();
    private ArrayList<String> profBookings = new ArrayList<>();

    private DateTimeFormatter dtf1;
    private DateTimeFormatter dtf2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.stud_bookingprofslot);

            dateView = (TextView) findViewById(R.id.date);

            firebaseDatabase = FirebaseDatabase.getInstance();
            availabilityDatabaseReference = firebaseDatabase.getReference().child("Professors").child(KEY).child("Preferences").child("availability");
            allBookingsDatabaseReference = firebaseDatabase.getReference().child("Professors").child(KEY).child("allBookings");
            queryBookingDatabaseReference = firebaseDatabase.getReference().child("Bookings");
            queryBookingDatabase = firebaseDatabase.getReference().orderByChild("Bookings");

            //Get all bookings involving the prof
            allBookingsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot booking: dataSnapshot.getChildren()){
                        profBookings.add(booking.getValue(String.class));
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            //retrieve booking info for calendar date
            calendarView = (CalendarView) findViewById(R.id.calendarView);
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, dayOfMonth);
                    LocalDate clickedDate = new LocalDate(year, month, dayOfMonth);
                    day = clickedDate.dayOfWeek().getAsText(Locale.ENGLISH);
                    dtf1 = DateTimeFormat.forPattern("EEEE, d MMMM Y");
                    dateView.setText(dtf1.print(clickedDate));

                    //get arraylist of time slots of the prof for that day
                    availabilityDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(day)) {
                                for (DataSnapshot timeSlot : dataSnapshot.getChildren()) {
                                    time = timeSlot.getValue(String.class);           //a time slot, e.g. 3:00PM - 5:00PM
                                    String startTime = time.substring(0, time.indexOf("-"));
                                    String endTime = time.substring(time.indexOf("-") + 1, time.length());
                                    dtf2 = new DateTimeFormatterBuilder().appendPattern("h:mma").toFormatter();
                                    LocalTime localstartTime = LocalTime.parse(startTime, dtf2);
                                    LocalTime localendTime = LocalTime.parse(endTime, dtf2);
                                    timeArray = getTimeSlots(timeArray, localstartTime, localendTime, slotSize);        //according to prof's preferred interval
                                }
                                //for every time slot of the prof for that day, iterate through every booking involving the prof
                                for (int i = 0; i < timeArray.size(); i++) {
                                    timeSlot = timeArray.get(i);
                                    queryBookingDatabase.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot booking : dataSnapshot.getChildren()) {
                                                bookingID = booking.getValue(String.class);
                                                //check if booking time matches timeSlot
                                                queryBookingDatabaseReference.child(bookingID).orderByChild("time").equalTo(timeSlot).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        Toast.makeText(StudBookingProfSlot.this, dataSnapshot.getChildrenCount() +
                                                                "bookings from " + KEY + " with timeslot " + timeSlot, Toast.LENGTH_SHORT).show();
                                                        if (dataSnapshot.getChildrenCount() != 0) {
                                                            bookingIDs.add(bookingID);      //timeSlot is booked by bookingID
                                                            //compare capacity and numStudents of bookingID
                                                            queryBookingDatabaseReference.child(bookingID).child("students").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    numStudents = (int) dataSnapshot.getChildrenCount();    //how many already in this booking
                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {
                                                                }
                                                            });
                                                            //
                                                            queryBookingDatabaseReference.child(bookingID).child("capacity").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    slotsLeft = dataSnapshot.getValue(Integer.class) - numStudents;
                                                                    if (slotsLeft == 0) {
                                                                        availabilityStatus.add("Not Available");
                                                                        colors.add(R.color.fullSlot);
                                                                    } else {
                                                                        availabilityStatus.add(slotsLeft + " Slots Available");
                                                                        colors.add(R.color.halfEmptySlot);
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {
                                                                }
                                                            });
                                                        } else {
                                                            bookingIDs.add(null);
                                                            availabilityStatus.add("Available");
                                                            colors.add(R.color.emptySlot);
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            });

            lv = (ListView) findViewById(R.id.all_slots);
            lv.setAdapter(new StudBookingListViewAdapter(StudBookingProfSlot.this, timeArray, colors, availabilityStatus, bookingIDs));
        } catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static ArrayList<String> getTimeSlots(ArrayList<String> timeArray, LocalTime startTime, LocalTime endTime, int slotSizeInMinutes){
        for (LocalTime time = startTime, nextTime; time.isBefore(endTime); time = endTime) {
            nextTime = time.plusMinutes(slotSizeInMinutes);
            if (nextTime.isAfter(endTime)) {
                break; // time slot crosses end time
            }
            timeArray.add(time + "-" + nextTime);
        }
        return timeArray;
    }
}