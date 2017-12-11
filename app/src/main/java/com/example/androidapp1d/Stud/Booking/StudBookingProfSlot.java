package com.example.androidapp1d.Stud.Booking;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp1d.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by ASUS on 12/7/2017.
 */

public class StudBookingProfSlot extends AppCompatActivity{

    CalendarView calendarView;
    TextView dateView;
    ListView lv;
    private static final String PROF = "Oka Kuniawaran";
    private int slotSize = 30;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference queryProfAvailabilityRef, queryProfBookingsRef,
            queryProfBookingsTimeRef, queryProfDateRef;
    private String day;
    private int numStudents, capacity;
    private String timeSlot, title;
    private String profBooking, formattedDate;
    private ImageButton dropDownButton;
    private ArrayList<String> orderedTimeArray = new ArrayList<>();
    private HashMap<String, String[]> timeArray = new HashMap<>();        //KEY: all time of the prod for the selected day; VALUE: slotsLeft. null if slot is available.
    private String[] availabilityStatus;
    private String[] bookingTitles;
    private Integer[] colors;
    private ArrayList<String> profBookings = new ArrayList<>();         //all bookings involving the prof
    private ArrayList<String> profTodayBookings = new ArrayList<>();    //all bookings involving the prof for the selected date

    private DateTimeFormatter dtf1;
    private DateTimeFormatter dtf2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.stud_bookingprofslot);

            dateView = (TextView) findViewById(R.id.date);
            dropDownButton = (ImageButton) findViewById(R.id.dropdownButton);
            lv = (ListView) findViewById(R.id.all_slots);

            firebaseDatabase = FirebaseDatabase.getInstance();
            queryProfAvailabilityRef = firebaseDatabase.getReference().child("Professors").child(PROF).child("Preferences").child("availability");
            queryProfBookingsRef = firebaseDatabase.getReference().child("Professors").child(PROF).child("allBookings");
            queryProfBookingsTimeRef = firebaseDatabase.getReference().child("Bookings");  //look under every booking of the prof with the specified timing
            queryProfDateRef = firebaseDatabase.getReference().child("Bookings");

            //Get all bookings involving the prof
            queryProfBookingsRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    profBooking = dataSnapshot.getValue(String.class);
                    profBookings.add(profBooking);
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

            //retrieve booking info for calendar date
            calendarView = (CalendarView) findViewById(R.id.calendarView);
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                timeArray.clear();
                lv.setAdapter(null);
                //setText date to the correct format
                LocalDate clickedDate = new LocalDate(year, month + 1, dayOfMonth);
                day = clickedDate.dayOfWeek().getAsText(Locale.ENGLISH);
                dtf1 = DateTimeFormat.forPattern("EEEE, d MMMM Y");
                formattedDate = dtf1.print(clickedDate);
                dateView.setText(formattedDate);

                //get arraylist of time slots of the prof for that day
                queryProfAvailabilityRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            if (!dataSnapshot.hasChild(day)) {
//                                    Toast.makeText(StudBookingProfSlot.this, "Prof " + KEY + " has no available time slots for that day", Toast.LENGTH_SHORT).show();
                            } else {
                                orderedTimeArray.clear();
                                for (DataSnapshot timeslot : dataSnapshot.child(day).getChildren()) {
                                    String time = timeslot.getValue(String.class);      //e.g. 9:00AM-12:00PM
                                    String startTime = time.substring(0, time.indexOf("-"));
                                    String endTime = time.substring(time.indexOf("-") + 1, time.length());
                                    dtf2 = new DateTimeFormatterBuilder().appendPattern("h:mma").toFormatter();
                                    LocalTime localstartTime = LocalTime.parse(startTime, dtf2);
                                    LocalTime localendTime = LocalTime.parse(endTime, dtf2);
                                    timeArray.putAll(getTimeSlots(localstartTime, localendTime, slotSize));  //chronological order of time slots of prof's preferred interval
                                }
                            }
                        } catch(Exception e){
                            Toast.makeText(StudBookingProfSlot.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                //get all prof bookings of that date
                queryProfDateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try{
                            for(int i = 0; i < profBookings.size(); i++) {
                                String date = dataSnapshot.child(profBookings.get(i)).child("date").getValue(String.class);
                                if (date.equals(formattedDate)) {
                                    profTodayBookings.add(profBookings.get(i));
                                } else{
                                    profTodayBookings.clear();
                                }
                            }
                        } catch(Exception e){
                            Toast.makeText(StudBookingProfSlot.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                //for every booking of the prof for that day, get their slotsLeft
                queryProfBookingsTimeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try{
                            for (int i = 0; i < profTodayBookings.size(); i++) {
                                timeSlot = dataSnapshot.child(profTodayBookings.get(i)).child("timing").getValue(String.class);
                                capacity = dataSnapshot.child(profTodayBookings.get(i)).child("capacity").getValue(Integer.class);
                                numStudents = (int) (dataSnapshot.child(profTodayBookings.get(i)).child("students").getChildrenCount());
                                title = dataSnapshot.child(profTodayBookings.get(i)).child("title").getValue(String.class);
                                String[] info = new String[2];
                                info[0] = String.valueOf(capacity - numStudents);
                                info[1] = title;
                                timeArray.put(timeSlot, info);
                            }
                        } catch(Exception e){
                            Toast.makeText(StudBookingProfSlot.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                }
            });

            dropDownButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                try {
                    if(timeArray.isEmpty()){
                        Toast.makeText(StudBookingProfSlot.this, "Prof " + PROF + " has no available time slots for that day", Toast.LENGTH_SHORT).show();
                    }
                    colors = new Integer[orderedTimeArray.size()];
                    bookingTitles = new String[orderedTimeArray.size()];
                    availabilityStatus = new String[orderedTimeArray.size()];
                    String slotsLeft;
                    for (int i = 0; i < orderedTimeArray.size(); i++) {
                        bookingTitles[i] = timeArray.get(orderedTimeArray.get(i))[1];
                        slotsLeft = timeArray.get(orderedTimeArray.get(i))[0];
                        if (slotsLeft == "NIL") {      //time slot not booked
                            colors[i] = R.color.emptySlot;
                            availabilityStatus[i] = "Available";
                        } else if (slotsLeft == "0") {  //time slot fully booked
                            colors[i] = R.color.fullSlot;
                            availabilityStatus[i] = "Not Available";
                        } else {
                            colors[i] = R.color.halfEmptySlot;
                            availabilityStatus[i] = slotsLeft + " Slots Available";
                        }
                    }
                    lv.setAdapter(new StudBookingListViewAdapter(StudBookingProfSlot.this, PROF, formattedDate,
                            orderedTimeArray.toArray(new String[orderedTimeArray.size()]), colors, availabilityStatus, bookingTitles));
                } catch (Exception e){
                    Toast.makeText(StudBookingProfSlot.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                }
            });
        } catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public HashMap<String, String[]> getTimeSlots(LocalTime startTime, LocalTime endTime, int slotSizeInMinutes) {
        HashMap<String, String[]> timeArray = new HashMap<>();
        String[] emptyInfo = new String[2];
        emptyInfo[0] = emptyInfo[1] = "NIL";
        int count = 0;
        for (LocalTime time = startTime, nextTime; time.isBefore(endTime); time = nextTime) {
            nextTime = time.plusMinutes(slotSizeInMinutes);
            if (nextTime.isAfter(endTime)) {
                break; // time slot crosses end time
            }
            DateTimeFormatter dtf1 = new DateTimeFormatterBuilder().appendPattern("h:mma").toFormatter();
            timeArray.put(dtf1.print(time) + "-" + dtf1.print(nextTime), emptyInfo);
            orderedTimeArray.add(dtf1.print(time) + "-" + dtf1.print(nextTime));
            count++;
        }
        return timeArray;
    }
}