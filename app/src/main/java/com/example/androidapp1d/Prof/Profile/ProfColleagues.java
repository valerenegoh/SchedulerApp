package com.example.androidapp1d.Prof.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidapp1d.R;
import com.example.androidapp1d.Stud.Profile.StudPersonalDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/13/2017.
 */

public class ProfColleagues extends AppCompatActivity {

    private ListView lv;
    private ArrayList<String> friendsList = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference friendsRef;
    private String CREATOR, clickedFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stud_profilefriends);

        Intent i = this.getIntent();
        CREATOR = i.getStringExtra("profName");

        firebaseDatabase = FirebaseDatabase.getInstance();
        friendsRef = firebaseDatabase.getReference().child("Professors");

        friendsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot aFriend: dataSnapshot.getChildren()){
                    if(!aFriend.getKey().equals(CREATOR)) {
                        friendsList.add(aFriend.getKey());
                    }
                }
                display();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void display(){
        lv = (ListView)findViewById(R.id.friendsList);
        final ArrayAdapter<String> lAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friendsList);
        lv.setAdapter(lAdapter2);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                clickedFriend = friendsList.get(position);
                Intent i = new Intent(ProfColleagues.this, StudPersonalDetails.class);
                i.putExtra("studentName", clickedFriend);
                startActivity(i);
            }
        });

        SearchView searchFriends = (SearchView) findViewById(R.id.searchFriends);
        searchFriends.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
