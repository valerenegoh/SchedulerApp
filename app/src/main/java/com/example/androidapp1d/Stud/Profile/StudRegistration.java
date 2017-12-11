package com.example.androidapp1d.Stud.Profile;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.androidapp1d.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/9/2017.
 */

public class StudRegistration extends AppCompatActivity {

    private EditText username, email, studentID, aboutme, capacity;
    private RadioGroup year;
    private Button modulesButton, profsButton, register;
    private String usernameInput, emailInput, studentIDInput, aboutmeInput, yearInput, capacityInput;
    private String[] modulesListItems, profsListItems;
    private boolean[] checkedModules, checkedProfs;
    private ArrayList<String> selectedmodulesList = new ArrayList<>();
    private ArrayList<String> profsList = new ArrayList<>();
    private ArrayList<String> selectedProfList = new ArrayList<>();
    private AlertDialog.Builder modsbuilder, profsbuilder;
    private FirebaseDatabase database;
    private DatabaseReference profRef, studentRef;
    private StudItem studItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.stud_registration);

            username = (EditText) findViewById(R.id.username);
            email = (EditText) findViewById(R.id.sutd_email);
            studentID = (EditText) findViewById(R.id.studentID);
            aboutme = (EditText) findViewById(R.id.student_description);
            capacity = (EditText) findViewById(R.id.student_capacity);
            year = (RadioGroup) findViewById(R.id.student_year);
            modulesButton = (Button) findViewById(R.id.select_modules);
            profsButton = (Button) findViewById(R.id.select_profs);
            register = (Button) findViewById(R.id.register);

            database = FirebaseDatabase.getInstance();
            profRef = database.getReference().child("Professors");
            studentRef = database.getReference().child("Students");

            //register button
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        usernameInput = username.getText().toString().trim();
                        emailInput = email.getText().toString().trim();
                        studentIDInput = studentID.getText().toString().trim();
                        aboutmeInput = aboutme.getText().toString().trim();
                        capacityInput = capacity.getText().toString().trim();
                        if (usernameInput.trim().isEmpty() || emailInput.isEmpty() || aboutmeInput.isEmpty() ||
                                capacity.getText().toString().trim().isEmpty()) {
                            Toast.makeText(StudRegistration.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                        } else if (selectedmodulesList.isEmpty() || selectedProfList.isEmpty()) {
                            Toast.makeText(StudRegistration.this, "Please select your modules", Toast.LENGTH_SHORT).show();
                        } else {
                            studItem = new StudItem(yearInput, aboutmeInput, emailInput, studentIDInput,
                                    Integer.parseInt(capacityInput), selectedProfList, selectedmodulesList);
                            studentRef.child(usernameInput).setValue(studItem);
                        }
                    } catch (Exception e){
                        Toast.makeText(StudRegistration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //get chosen module
            year.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.freshmore:
                            modulesListItems = getResources().getStringArray(R.array.freshmore_modules);
                            yearInput = "Freshmore";
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
                    Toast.makeText(StudRegistration.this, yearInput, Toast.LENGTH_SHORT).show();
                }
            });

            //alert dialog to choose module
            modulesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (year.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(StudRegistration.this, "Select Freshmore/your Pillar", Toast.LENGTH_SHORT).show();
                    } else {
                        checkedModules = new boolean[modulesListItems.length];
                        modsbuilder = new AlertDialog.Builder(StudRegistration.this);
                        modsbuilder.setTitle("Select some modules that you consult often about")
                            .setMultiChoiceItems(modulesListItems, checkedModules, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    if (isChecked) {
                                        if (!selectedmodulesList.contains(modulesListItems[which])){
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
                                        Toast.makeText(StudRegistration.this, "Please select some modules", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //get profs under the selected mods
                                        for (int i = 0; i < selectedmodulesList.size(); i++) {
                                            profRef.orderByChild("mods").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    try {
                                                        for (DataSnapshot profs : dataSnapshot.getChildren()) {
                                                            for (int i = 0; i < selectedmodulesList.size(); i++) {
                                                                for(DataSnapshot mod: profs.child("mods").getChildren()){
                                                                    if(mod.getValue(String.class).equals(selectedmodulesList.get(i))){
                                                                        if(!profsList.contains(profs.getKey())){
                                                                            profsList.add(profs.getKey());
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch(Exception e){
                                                        Toast.makeText(StudRegistration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                }
                                            });
                                        }
                                        Toast.makeText(StudRegistration.this, "modules selected: \n"
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
                }
            });

            profsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedmodulesList.isEmpty()){
                        Toast.makeText(StudRegistration.this, "Please select your modules first", Toast.LENGTH_SHORT).show();
                    } else if (profsList.isEmpty()) {
                        Toast.makeText(StudRegistration.this, "There are no professors registered under any of the modules you have selected", Toast.LENGTH_SHORT).show();
                    } else {
                        checkedProfs = new boolean[profsList.size()];
                        profsListItems = profsList.toArray(new String[profsList.size()]);
                        //begin profs dialog
                        profsbuilder = new AlertDialog.Builder(StudRegistration.this);
                        profsbuilder.setTitle("Select some professors whom you often consult")
                                .setMultiChoiceItems(profsListItems, checkedProfs, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if (isChecked) {
                                            if (!selectedProfList.contains(profsListItems[which])) {
                                                selectedProfList.add(profsListItems[which]);
                                            }
                                        } else {
                                            selectedProfList.remove(profsListItems[which]);
                                        }
                                    }
                                })
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(selectedProfList.isEmpty()){
                                            Toast.makeText(StudRegistration.this, "No professors selected", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(StudRegistration.this, "professors selected: "
                                                    + selectedProfList.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("CLEAR ALL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        for (int i = 0; i < checkedProfs.length; i++) {
                                            checkedProfs[i] = false;
                                            selectedProfList.clear();
                                        }
                                        dialog.dismiss();
                                    }
                                });
                        profsbuilder.create().show();
                    }
                }
            });
        } catch(Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}