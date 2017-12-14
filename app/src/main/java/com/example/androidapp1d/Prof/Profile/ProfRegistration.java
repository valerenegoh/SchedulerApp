package com.example.androidapp1d.Prof.Profile;


import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.androidapp1d.R;
import com.example.androidapp1d.Stud.Feed.StudFeedActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by ASUS on 12/9/2017.
 */

public class ProfRegistration extends AppCompatActivity {

    private EditText username, email, office, aboutme, venue, timeslot, password;
    private RadioGroup year;
    private CheckBox showhidepassword;
    private Button modulesButton, availsButton, register;
    private String usernameInput, emailInput, officeInput, aboutmeInput, yearInput, venueInput, pwInput, timeslotS;
    private String[] modulesListItems, availListItems;
    private boolean[] checkedModules, checkedAvail;
    private ArrayList<String> selectedmodulesList = new ArrayList<>();
    private ArrayList<String> selectedavailList = new ArrayList<>();
    private HashMap<String, ArrayList<String>> selecteddays = new HashMap<>();
    private AlertDialog.Builder modsbuilder, availsbuilder, timebuilder;
    private TimePickerDialog timestartbuilder, timeendbuilder;
    private FirebaseDatabase database;
    private DatabaseReference profRef;
    private ProfItem profItem;
    private PrefItem prefItem;
    private String starttime, endtime, clickedDay;
    private int clickedposition;
    private Calendar calendar = Calendar.getInstance();
    private int hour = calendar.get(Calendar.HOUR);
    private int min = calendar.get(Calendar.MINUTE);
    private ArrayList<String> current;
    private String timeslottotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.prof_registration);

            username = (EditText) findViewById(R.id.username);
            email = (EditText) findViewById(R.id.sutd_email);
            office = (EditText) findViewById(R.id.office);
            aboutme = (EditText) findViewById(R.id.student_description);
            venue = (EditText) findViewById(R.id.student_capacity);
            timeslot = (EditText) findViewById(R.id.timeslot);
            year = (RadioGroup) findViewById(R.id.student_year);
            modulesButton = (Button) findViewById(R.id.select_modules);
            availsButton = (Button) findViewById(R.id.availabilityIndicator);
            register = (Button) findViewById(R.id.register);
            showhidepassword = (CheckBox) findViewById(R.id.cbShowPwd);
            password = (EditText) findViewById(R.id.student_password);

            database = FirebaseDatabase.getInstance();
            profRef = database.getReference().child("Professors");

            showhidepassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // checkbox status is changed from uncheck to checked.
                    if (!isChecked) {
                        // show password
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    } else {
                        // hide password
                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                }
            });

            //register button
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        usernameInput = username.getText().toString().trim();
                        emailInput = email.getText().toString().trim();
                        officeInput = office.getText().toString().trim();
                        aboutmeInput = aboutme.getText().toString().trim();
                        venueInput = venue.getText().toString().trim();
                        timeslotS = venue.getText().toString().trim();
                        pwInput = password.getText().toString().trim();
                        if (usernameInput.trim().isEmpty() || emailInput.isEmpty() || aboutmeInput.isEmpty() ||
                                venueInput.isEmpty() || pwInput.isEmpty() || timeslotS.isEmpty()) {
                            Toast.makeText(ProfRegistration.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                        } else if (selectedmodulesList.isEmpty()) {
                            Toast.makeText(ProfRegistration.this, "Please select the module(s) that you teach", Toast.LENGTH_SHORT).show();
                        } else if (selectedavailList.isEmpty()){
                            Toast.makeText(ProfRegistration.this, "Please select default time availability (office hours)", Toast.LENGTH_SHORT).show();
                        } else {
                            profItem = new ProfItem(pwInput, yearInput, aboutmeInput, emailInput, officeInput, selectedmodulesList);
                            prefItem = new PrefItem(Integer.parseInt(timeslotS), venueInput, selecteddays);
                            profRef.child(usernameInput).setValue(profItem);
                            profRef.child(usernameInput).child("Preferences").setValue(prefItem);
                            Toast.makeText(ProfRegistration.this, "Welcome to Plandular, "
                                    + usernameInput + "!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ProfRegistration.this, StudFeedActivity.class);
                            i.putExtra("creator", usernameInput);
                            startActivity(i);

                        }
                    } catch (Exception e){
                        Toast.makeText(ProfRegistration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //get chosen module
            year.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    try {
                        switch (checkedId) {
                            case R.id.freshmore:
                                modulesListItems = getResources().getStringArray(R.array.HASS_modules);
                                yearInput = "HASS Faculty";
                                break;
                            case R.id.ISTDpillar:
                                modulesListItems = getResources().getStringArray(R.array.ISTD_modules);
                                yearInput = "ISTD Pillar";
                                break;
                            case R.id.EPDpillar:
                                modulesListItems = getResources().getStringArray(R.array.EPD_modules);
                                yearInput = "EPD Pillar";
                                break;
                            case R.id.ESDpillar:
                                modulesListItems = getResources().getStringArray(R.array.ESD_modules);
                                yearInput = "ESD Pillar";
                                break;
                            case R.id.ASDpillar:
                                modulesListItems = getResources().getStringArray(R.array.ASD_modules);
                                yearInput = "ASD Pillar";
                                break;
                        }
                        Toast.makeText(ProfRegistration.this, yearInput, Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        Toast.makeText(ProfRegistration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            availListItems = getResources().getStringArray(R.array.Availabilities);

            //alert dialog to choose module
            modulesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (year.getCheckedRadioButtonId() == -1) {
                            Toast.makeText(ProfRegistration.this, "Select your faculty", Toast.LENGTH_SHORT).show();
                        } else {
                            checkedModules = new boolean[modulesListItems.length];
                            modsbuilder = new AlertDialog.Builder(ProfRegistration.this);
                            modsbuilder.setTitle("Select some modules that you teach")
                                    .setMultiChoiceItems(modulesListItems, checkedModules, new DialogInterface.OnMultiChoiceClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                            if (isChecked) {
                                                if (!selectedmodulesList.contains(modulesListItems[which])) {
                                                    selectedmodulesList.add(modulesListItems[which]);
                                                }
                                            } else {
                                                selectedmodulesList.remove(modulesListItems[which]);
                                            }
                                        }
                                    })
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (selectedmodulesList.isEmpty()) {
                                                Toast.makeText(ProfRegistration.this, "Please select some modules", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(ProfRegistration.this, "modules selected: \n"
                                                        + selectedmodulesList.toString(), Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    })
                                    .setNegativeButton("CLEAR ALL", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            for (int i = 0; i < checkedModules.length; i++) {
                                                checkedModules[i] = false;
                                                selectedmodulesList.clear();
                                            }
                                            dialog.dismiss();
                                        }
                                    });
                            modsbuilder.create().show();
                        }
                    } catch (Exception e){
                        Toast.makeText(ProfRegistration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //alert dialog to choose availability
            availsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        checkedAvail = new boolean[availListItems.length];
                        availsbuilder = new AlertDialog.Builder(ProfRegistration.this);
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
                                            Toast.makeText(ProfRegistration.this, "Please select your availabilities", Toast.LENGTH_SHORT).show();
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
                    }catch (Exception e){
                        Toast.makeText(ProfRegistration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            });
        } catch(Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void timeBuilderDialog(){
        try {
            timebuilder = new AlertDialog.Builder(ProfRegistration.this);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.prof_timepicker, null);
            timebuilder.setView(convertView);
            timebuilder.setTitle("Click on the days to add an available time slot");
            ListView lv = (ListView) convertView.findViewById(R.id.daysList);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfRegistration.this, android.R.layout.simple_list_item_1, selectedavailList);
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
                            Toast.makeText(ProfRegistration.this, "Office hours: " + selecteddays.toString(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void timePickerDialog(){
        try {
            timestartbuilder = new TimePickerDialog(ProfRegistration.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String am_pm = (hourOfDay < 12) ? "AM" : "PM";
                    starttime = hourOfDay + ":" + minute + am_pm;
                    timeslottotal = starttime;
                }
            }, hour, min, true);
            timestartbuilder.setTitle("Choose a start time");
            timestartbuilder.setButton(DialogInterface.BUTTON_POSITIVE, "Next", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    timeendbuilder = new TimePickerDialog(ProfRegistration.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String am_pm = (hourOfDay < 12) ? "AM" : "PM";
                            endtime = hourOfDay + ":" + minute + am_pm;
                            timeslottotal += "-" + endtime;
                        }
                    }, hour, min, true);
                    timeendbuilder.setTitle("Choose an end time");
                    timeendbuilder.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {

                                current = selecteddays.get(clickedDay);
                                current.add(clickedposition, timeslottotal);
                                selecteddays.put(clickedDay, current);
                                selectedavailList.set(clickedposition, clickedDay + " " + timeslot);
                                timeBuilderDialog();
                            } catch (Exception e){
                                Toast.makeText(ProfRegistration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
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
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}