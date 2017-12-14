package com.example.androidapp1d.Prof.Feed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.androidapp1d.Prof.Booking.ProfBookingActivity;
import com.example.androidapp1d.Prof.ProfSearchActivity;
import com.example.androidapp1d.Prof.Profile.ProfProfileActivity;
import com.example.androidapp1d.R;
import com.example.androidapp1d.Stud.Profile.StudProfileActivity;
import com.example.androidapp1d.Stud.Profile.StudeditProfModActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfFeedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar actionBar;
    private NavigationView navigationView;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ArrayList<String> mods = new ArrayList<>();
    private ArrayList<String> profs = new ArrayList<>();
    private ArrayList<ProfFeedItem> acceptedBookings = new ArrayList<>();
    private ArrayList<String> acceptedBookingNames = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference allBookingsDatabaseReference, StudRef;
    private RecyclerView rv1;
    private long timestamp, currentTime;
    private long dayFromNow = 259200000/1000;
    private String title, prof, description, mod, time, creator, id, USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.studactivity_feed);

            getSupportActionBar().setTitle("Feed");

            Intent i = this.getIntent();
            USER = i.getStringExtra("creator");

            firebaseDatabase = FirebaseDatabase.getInstance();
            allBookingsDatabaseReference = firebaseDatabase.getReference().child("Bookings");
            StudRef = firebaseDatabase.getReference().child("Students").child(USER);

            //get all mods & profs of student
            StudRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        for (DataSnapshot aMod : dataSnapshot.child("mods").getChildren()) {
                            mods.add(aMod.getValue(String.class));
                        }
                        for (DataSnapshot aProf : dataSnapshot.child("favProfs").getChildren()) {
                            profs.add(aProf.getValue(String.class));
                        }
                        uploadBookings();
                    } catch(Exception e){
                        Toast.makeText(ProfFeedActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            //add listener for updates
            allBookingsDatabaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    try {
                        for(DataSnapshot aBooking: dataSnapshot.getChildren()) {
                            //if it involves you
                            if (mods.contains(aBooking.child("mod").getValue(String.class)) ||
                                    profs.contains(aBooking.child("prof").getValue(String.class))) {
                                //if it is happening soon
                                timestamp = aBooking.child("timestamp").getValue(Long.class);
                                currentTime = System.currentTimeMillis() / 1000;
                                Toast.makeText(ProfFeedActivity.this, "new update available", Toast.LENGTH_SHORT).show();
                                if (currentTime <= timestamp && timestamp < (currentTime + dayFromNow)) {
                                    id = aBooking.getKey();
                                    title = aBooking.child("title").getValue(String.class);
                                    prof = aBooking.child("prof").getValue(String.class);
                                    creator = aBooking.child("creator").getValue(String.class);
                                    time = aBooking.child("timing").getValue(String.class);
                                    description = aBooking.child("description").getValue(String.class);
                                    mod = aBooking.child("mod").getValue(String.class);
                                    acceptedBookings.add(0, new ProfFeedItem(id, USER, title, prof, description, mod, time, creator));
                                    if(acceptedBookings.size() == 30){
                                        acceptedBookings.remove(29);
                                    }
                                    displayFeed();
                                }
                            }
                        }
                    } catch(Exception e){
                        Toast.makeText(ProfFeedActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
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
            //=============================================================================

            drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.opendrawer, R.string.closedrawer);
            drawerLayout.setDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            final Menu menu1 = navigationView.getMenu();
            MenuItem dFeed = menu1.getItem(0);
            dFeed.setChecked(true);

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case (R.id.side_feed):
                            drawerLayout.closeDrawer(navigationView);
                            break;
                        case (R.id.side_booking):
                            Intent h = new Intent(ProfFeedActivity.this, StudeditProfModActivity.class);
                            h.putExtra("creator", USER);
                            startActivity(h);
                            break;
                        case (R.id.side_profile):
                            Intent i = new Intent(ProfFeedActivity.this, StudProfileActivity.class);
                            i.putExtra("creator", USER);
                            startActivity(i);
                            break;
                    }
                    return false;
                }
            });

            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
            final Menu menu = bottomNavigationView.getMenu();
            MenuItem bFeed = menu.getItem(0);
            bFeed.setChecked(true);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case (R.id.ic_feed):
                            break;
                        case (R.id.ic_booking):
                            Intent h = new Intent(ProfFeedActivity.this, ProfBookingActivity.class);
                            h.putExtra("creator", USER);
                            startActivity(h);
                            break;
                        case (R.id.ic_profile):
                            Intent i = new Intent(ProfFeedActivity.this, ProfProfileActivity.class);
                            startActivity(i);
                            i.putExtra("creator", USER);
                            break;
                        case (R.id.ic_search):
                            Intent j = new Intent(ProfFeedActivity.this, ProfSearchActivity.class);
                            startActivity(j);
                            break;
                    }
                    return false;
                }
            });
        } catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadBookings(){
        //upload once
        allBookingsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for(DataSnapshot aBooking: dataSnapshot.getChildren()){
                        //if it involves you
                        if (mods.contains(aBooking.child("mod").getValue(String.class)) ||
                                profs.contains(aBooking.child("prof").getValue(String.class))) {
                            //if it is happening soon
                            timestamp = aBooking.child("timestamp").getValue(Long.class);
                            currentTime = System.currentTimeMillis() / 1000;
//                            Toast.makeText(StudFeedActivity.this, aBooking.child("title").getValue(String.class) + ": " +
//                                    Boolean.toString(currentTime <= timestamp && timestamp < (currentTime + dayFromNow)), Toast.LENGTH_SHORT).show();
                            if (currentTime <= timestamp && timestamp < (currentTime + dayFromNow)) {
                                id = aBooking.getKey();
                                title = aBooking.child("title").getValue(String.class);
                                prof = aBooking.child("prof").getValue(String.class);
                                creator = aBooking.child("creator").getValue(String.class);
                                time = aBooking.child("timing").getValue(String.class);
                                description = aBooking.child("description").getValue(String.class);
                                mod = aBooking.child("mod").getValue(String.class);
                                acceptedBookings.add(new ProfFeedItem(id, USER, title, prof, description, mod, time, creator));
                                acceptedBookingNames.add(title);
                            }
                        }
                    }
                    displayFeed();
                } catch(Exception e){
                    Toast.makeText(ProfFeedActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void displayFeed(){
        try {
            if(acceptedBookingNames.isEmpty()){
                Toast.makeText(this, "no feed for these three days", Toast.LENGTH_SHORT).show();
            }
            final ProfFeedDetailsAdapter adapterFeed = new ProfFeedDetailsAdapter(this, acceptedBookings);
            rv1 = (RecyclerView) findViewById(R.id.recyclerviewFeed);
            rv1.setLayoutManager(new LinearLayoutManager(this));
            rv1.setItemAnimator(new DefaultItemAnimator());
            adapterFeed.notifyDataSetChanged();
            rv1.setAdapter(adapterFeed);

        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.studmenu_main,menu);
        return true;
    }

    public void goNotificationActivity(){
        startActivity(new Intent(this,ProfNotificationActivity.class));
    }

    public void goSearchActivity(){
        startActivity(new Intent(this,ProfSearchActivity.class)) ;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id==android.R.id.home) {
            if(drawerLayout.isDrawerOpen(navigationView)){
                drawerLayout.closeDrawer(navigationView);
            }
            else{
                drawerLayout.openDrawer(navigationView);
            }
        }
        if (id==R.id.menu_item_add){
            goNotificationActivity();
        }
        if (id == R.id.ic_search){
            goSearchActivity();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}