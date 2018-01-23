package com.example.androidapp1d.Stud.Profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.androidapp1d.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudeditProfModActivity extends AppCompatActivity {

    private TabHost tabHost;
    private ArrayList<String> profList = new ArrayList<>();
    private ArrayList<String> modList = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference profNamesRef, modsRef;
    private ListView lv1, lv2;
    private String CREATOR;
    private FloatingActionButton addProf, addMod;
    private AlertDialog.Builder modBuilder, profBuilder;
    private String clickedMod, clickedProf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stud_profileprofmod);

        getSupportActionBar().setTitle("Your Modules and Professors");

        Intent i = this.getIntent();
        CREATOR = i.getStringExtra("studentName");

        tabHost = (TabHost) findViewById(R.id.newbookingsTab);
        tabHost.setup();
        TabHost.TabSpec profsTab = tabHost.newTabSpec("PROF TAB");
        profsTab.setIndicator("PROF TAB");
        profsTab.setContent(R.id.Professors);
        tabHost.addTab(profsTab);

        TabHost.TabSpec modsTab = tabHost.newTabSpec("MODS TAB");
        modsTab.setIndicator("MODS TAB");
        modsTab.setContent(R.id.Modules);
        tabHost.addTab(modsTab);

        firebaseDatabase = FirebaseDatabase.getInstance();

        //================================PROFESSORS TAB================================

        lv2 = (ListView)findViewById(R.id.professorsList);
        addProf = (FloatingActionButton) findViewById(R.id.addProfButton);
        addProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudeditProfModActivity.this, StudallProfs.class);
                i.putExtra("studName", CREATOR);
                startActivity(i);
            }
        });

        profNamesRef = firebaseDatabase.getReference().child("Students").child(CREATOR).child("favProfs");

        profNamesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot professor: dataSnapshot.getChildren()){
                    profList.add(professor.getKey());
                }
                setProfView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //================================MODULES TAB================================

        lv1 = (ListView)findViewById(R.id.modulesList);
        addMod = (FloatingActionButton) findViewById(R.id.addModButton);
        addMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudeditProfModActivity.this, StudallMods.class);
                i.putExtra("studName", CREATOR);
                startActivity(i);
            }
        });

        modsRef = firebaseDatabase.getReference().child("Students").child(CREATOR).child("mods");

        modsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot mod: dataSnapshot.getChildren()){
                    modList.add(mod.getValue(String.class));
                }
                setModView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setProfView(){
        final ArrayAdapter<String> lAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, profList);
        lv2.setAdapter(lAdapter2);

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                clickedProf = profList.get(position);
                profBuilder = new AlertDialog.Builder(StudeditProfModActivity.this);
                profBuilder.setTitle("Delete professor?");
                profBuilder.setMessage("Consultations with this professor will no longer appear on your feed.");
                profBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        profNamesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot aProf: dataSnapshot.getChildren()){
                                    if(aProf.getValue(String.class).equals(clickedProf)){
                                        aProf.getRef().removeValue();
                                        Toast.makeText(StudeditProfModActivity.this, clickedProf +
                                                " deleted from your list of professors", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        dialog.dismiss();
                    }
                });
                profBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                profBuilder.create().show();
            }
        });

        SearchView searchProfs = (SearchView) findViewById(R.id.searchProfessors);
        searchProfs.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lAdapter2.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void setModView(){
        final ArrayAdapter<String> lAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, modList);
        lv1.setAdapter(lAdapter1);

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                clickedMod = modList.get(position);
                modBuilder = new AlertDialog.Builder(StudeditProfModActivity.this);
                modBuilder.setTitle("Delete module?");
                modBuilder.setMessage("Consultations about this module will no longer appear on your feed.");
                modBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        modsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot aMod: dataSnapshot.getChildren()){
                                    if(aMod.getValue(String.class).equals(clickedMod)){
                                        aMod.getRef().removeValue();
                                        Toast.makeText(StudeditProfModActivity.this, clickedMod +
                                                " deleted from your list of modules", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        dialog.dismiss();
                    }
                });
                modBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                modBuilder.create().show();
            }
        });

        SearchView searchMods = (SearchView) findViewById(R.id.searchModules);
        searchMods.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lAdapter1.getFilter().filter(newText);
                return false;
            }
        });
    }
}
