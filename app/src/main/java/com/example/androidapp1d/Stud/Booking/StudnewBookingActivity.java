package com.example.androidapp1d.Stud.Booking;

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

public class StudnewBookingActivity extends AppCompatActivity{

        private ArrayList<String> profList = new ArrayList<>();
        private FirebaseDatabase firebaseDatabase;
        private DatabaseReference profNamesRef;
        private ListView lv2;
        private String CREATOR;
        private String clickedProf;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.stud_newbooking);

            getSupportActionBar().setTitle("New Booking");

            Toast.makeText(this, "Select a professor to commence new booking", Toast.LENGTH_SHORT).show();

            Intent i = this.getIntent();
//            CREATOR = i.getStringExtra("studentName");
            CREATOR = "Valerene Goh";

            firebaseDatabase = FirebaseDatabase.getInstance();

            lv2 = (ListView)findViewById(R.id.professorsList);

            profNamesRef = firebaseDatabase.getReference().child("Students").child(CREATOR).child("favProfs");

            profNamesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot professor: dataSnapshot.getChildren()){
                        profList.add(professor.getValue(String.class));
                    }
                    setProfView();
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
                Toast.makeText(StudnewBookingActivity.this, clickedProf, Toast.LENGTH_SHORT).show();
                clickedProf = profList.get(position);
                Intent i = new Intent(StudnewBookingActivity.this, StudBookingProfSlot.class);
                i.putExtra("creator", CREATOR);
                i.putExtra("profName", clickedProf);
                startActivity(i);
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
