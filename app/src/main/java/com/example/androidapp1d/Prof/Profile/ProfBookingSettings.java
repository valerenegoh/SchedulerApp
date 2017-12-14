package com.example.androidapp1d.Prof.Profile;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.androidapp1d.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by ASUS on 12/13/2017.
 */

public class ProfBookingSettings extends AppCompatActivity {

    private ImageButton venue, timeslot, avail, biblio;
    private TextView venuedetails, timeslotdetails, availdetails, bibliodetails;
    private AlertDialog.Builder venuebuilder, timeslotbuilder, availsbuilder, bibliobuilder, timebuilder;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference profRef;
    private String[] availListItems;
    private String CREATOR, officehoursS;
    private String starttime, endtime, clickedDay;
    private int clickedposition;
    private Calendar calendar = Calendar.getInstance();
    private int hour = calendar.get(Calendar.HOUR);
    private int min = calendar.get(Calendar.MINUTE);
    private boolean[] checkedAvail;
    private ArrayList<String> current;
    private TimePickerDialog timestartbuilder, timeendbuilder;
    private ArrayList<String> selectedavailList = new ArrayList<>();
    private HashMap<String, ArrayList<String>> selecteddays = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stud_profilefriends);

        availListItems = getResources().getStringArray(R.array.Availabilities);

        venue = (ImageButton) findViewById(R.id.editvenue);
        timeslot = (ImageButton) findViewById(R.id.edittimeslot);
        avail = (ImageButton) findViewById(R.id.editavailability);
        biblio = (ImageButton) findViewById(R.id.editbiblio);
        venuedetails = (TextView) findViewById(R.id.venue_details);
        timeslotdetails = (TextView) findViewById(R.id.timeslot_details);
        availdetails = (TextView) findViewById(R.id.avail_details);
        bibliodetails = (TextView) findViewById(R.id.biblio_details);

        Intent i = this.getIntent();
        CREATOR = i.getStringExtra("creator");

        firebaseDatabase = FirebaseDatabase.getInstance();
        profRef = firebaseDatabase.getReference().child("Professors").child(CREATOR);

        profRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    venuedetails.setText(dataSnapshot.child("Preferences/venue").getValue(String.class));
                    timeslotdetails.setText(String.valueOf(dataSnapshot.child("Preferences/venue").getValue(Integer.class)));
                    bibliodetails.setText(dataSnapshot.child("description").getValue(String.class));
                    for (DataSnapshot day : dataSnapshot.child("Preferences/availability").getChildren()) {
                        officehoursS += day.getKey() + " ";
                        for (DataSnapshot time : day.getChildren()) {
                            officehoursS += time.getValue(String.class) + " ";
                        }
                        officehoursS += "\n";
                    }
                    availdetails.setText(officehoursS);
                } catch(Exception e){
                    Toast.makeText(ProfBookingSettings.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        venue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setup layout
                LinearLayout layout = new LinearLayout(ProfBookingSettings.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setGravity(Gravity.CENTER_HORIZONTAL);
                layout.setPadding(50, 50, 50, 50);
                final EditText inputVen = new EditText(ProfBookingSettings.this);
                inputVen.setHint("Enter new default consultation venue");
                inputVen.setPadding(20, 30, 20, 30);
                layout.addView(inputVen);

                venuebuilder = new AlertDialog.Builder(ProfBookingSettings.this);
                venuebuilder.setCancelable(false)
                        .setView(layout)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                profRef.child("Preferences/venue").setValue(inputVen.getText());
                                Toast.makeText(ProfBookingSettings.this, "default venue changed to " +
                                        inputVen.getText().toString(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                venuebuilder.create().show();
            }
        });

        timeslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setup layout
                LinearLayout layout = new LinearLayout(ProfBookingSettings.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setGravity(Gravity.CENTER_HORIZONTAL);
                layout.setPadding(50, 50, 50, 50);
                final EditText inputVen = new EditText(ProfBookingSettings.this);
                inputVen.setHint("Enter new default consultation time slot (in min)");
                inputVen.setInputType(InputType.TYPE_CLASS_NUMBER);
                inputVen.setPadding(20, 30, 20, 30);
                layout.addView(inputVen);

                timeslotbuilder = new AlertDialog.Builder(ProfBookingSettings.this);
                timeslotbuilder.setCancelable(false)
                        .setView(layout)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                profRef.child("Preferences/timeslotsize").setValue(inputVen.getText());
                                Toast.makeText(ProfBookingSettings.this, "default timeslot size changed to " +
                                        inputVen.getText().toString(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                timeslotbuilder.create().show();
            }
        });

        try {
            //alert dialog to choose availability
            avail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkedAvail = new boolean[availListItems.length];
                    availsbuilder = new AlertDialog.Builder(ProfBookingSettings.this);
                    availsbuilder.setTitle("Select your availability preferences")
                            .setMultiChoiceItems(availListItems, checkedAvail, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    if (isChecked) {
                                        if (!selectedavailList.contains(availListItems[which])) {
                                            selectedavailList.add(availListItems[which]);
                                            selecteddays.put(availListItems[which], new ArrayList<String>());
                                        }
                                    } else {
                                        selectedavailList.remove(availListItems[which]);
                                        selecteddays.remove(availListItems[which]);
                                    }
                                }
                            })
                            // .setView(dialogView)
                            .setCancelable(false)
                            .setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (selectedavailList.isEmpty()) {
                                        Toast.makeText(ProfBookingSettings.this, "Please select your availabilities", Toast.LENGTH_SHORT).show();
                                    } else {
                                        timeBuilderDialog();
                                    }
                                }
                            })
                            .setNegativeButton("CLEAR ALL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for (int i = 0; i < checkedAvail.length; i++) {
                                        checkedAvail[i] = false;
                                        selectedavailList.clear();
                                        selecteddays.clear();
                                    }
                                    dialog.dismiss();
                                }
                            });
                    availsbuilder.create().show();
                }

            });
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        biblio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setup layout
                LinearLayout layout = new LinearLayout(ProfBookingSettings.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setGravity(Gravity.CENTER_HORIZONTAL);
                layout.setPadding(50, 50, 50, 50);
                final EditText inputBiblio = new EditText(ProfBookingSettings.this);
                inputBiblio.setHint("Enter new bibliography write-up");
                inputBiblio.setPadding(20, 30, 20, 30);
                layout.addView(inputBiblio);

                bibliobuilder = new AlertDialog.Builder(ProfBookingSettings.this);
                bibliobuilder.setCancelable(false)
                        .setView(layout)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                profRef.child("description").setValue(inputBiblio.getText());
                                Toast.makeText(ProfBookingSettings.this, "bibliography updated", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                bibliobuilder.create().show();
            }
        });

    }

    private void timePickerDialog(){
        try {
            timestartbuilder = new TimePickerDialog(ProfBookingSettings.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String am_pm = (hourOfDay < 12) ? "AM" : "PM";
                    starttime = hourOfDay + ":" + minute + am_pm;
                }
            }, hour, min, false);
            timestartbuilder.setTitle("Choose a start time");
            timestartbuilder.setButton(DialogInterface.BUTTON_POSITIVE, "Next", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        timeendbuilder = new TimePickerDialog(ProfBookingSettings.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String am_pm = (hourOfDay < 12) ? "AM" : "PM";
                                endtime = hourOfDay + ":" + minute + am_pm;
                            }
                        }, hour, min, false);
                        timeendbuilder.setTitle("Choose an end time");
                        timeendbuilder.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String timeslot = starttime + "-" + endtime;
                                current = selecteddays.get(clickedDay);
                                current.add(clickedposition + 1, timeslot);
                                selecteddays.put(clickedDay, current);
                                selectedavailList.set(clickedposition, clickedDay + " " + timeslot);
                            }
                        });
                        timeendbuilder.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                timeBuilderDialog();
                                dialog.dismiss();
                            }
                        });
                        timeendbuilder.setCancelable(false);
                        timeendbuilder.show();
                    } catch(Exception e){
                        Toast.makeText(ProfBookingSettings.this, "time-end-builder: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            timestartbuilder.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    timeBuilderDialog();
                    dialog.dismiss();
                }
            });
            timestartbuilder.setCancelable(false);
            timestartbuilder.show();
        } catch(Exception e){
            Toast.makeText(this, "time-start-builder: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void timeBuilderDialog(){
        try {
            timebuilder = new AlertDialog.Builder(ProfBookingSettings.this);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.prof_timepicker, null);
            timebuilder.setView(convertView);
            timebuilder.setTitle("Click on the days to add an available time slot");
            ListView lv = (ListView) convertView.findViewById(R.id.daysList);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfBookingSettings.this, android.R.layout.simple_list_item_1, selectedavailList);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    clickedposition = position;
                    clickedDay = (String) selecteddays.keySet().toArray()[position];
                    timePickerDialog();
                }
            });
            timebuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                profRef.child("Preferences/availability").setValue(selecteddays);
                                Toast.makeText(ProfBookingSettings.this, "Office hours: " + selecteddays.toString(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } catch (Exception e) {
                                Toast.makeText(ProfBookingSettings.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("CLEAR ALL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (int i = 0; i < checkedAvail.length; i++) {
                                checkedAvail[i] = false;
                                selectedavailList.clear();
                                selecteddays.clear();
                            }
                            dialog.dismiss();
                        }
                    });
            timebuilder.create().show();
        } catch (Exception e){
            Toast.makeText(this, "time-listview: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
