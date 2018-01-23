package com.example.androidapp1d.Stud.Profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidapp1d.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/12/2017.
 */

public class StudallProfs extends AppCompatActivity {

    private ListView lv;
    private ArrayList<String> profList = new ArrayList<>();
    private String CREATOR, clickedProf;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference profsRef, allprofsRef;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stud_profileallprof);

        Intent i = this.getIntent();
        CREATOR = i.getStringExtra("studentName");

        lv = (ListView)findViewById(R.id.professorsList);

        firebaseDatabase = FirebaseDatabase.getInstance();
        profsRef = firebaseDatabase.getReference().child("Students").child(CREATOR).child("favProfs");
        allprofsRef = firebaseDatabase.getReference().child("Professors");

        allprofsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot aProf: dataSnapshot.getChildren()){
                    profList.add(aProf.getKey());
                }
                display();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void display(){
        final ArrayAdapter<String> lAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, profList);
        lv.setAdapter(lAdapter2);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                clickedProf = profList.get(position);
                builder = new AlertDialog.Builder(StudallProfs.this);
                builder.setTitle("Add professor?");
                builder.setMessage("Any consultations with this professor will appear on your feed.");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        profsRef.push().setValue(clickedProf);
                        Toast.makeText(StudallProfs.this, clickedProf + " added to your list of professors", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
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
}
