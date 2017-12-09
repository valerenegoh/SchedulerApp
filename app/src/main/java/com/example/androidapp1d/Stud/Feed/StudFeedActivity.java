package com.example.androidapp1d.Stud.Feed;

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

import com.example.androidapp1d.R;
import com.example.androidapp1d.Stud.Booking.StudBookingActivity;
import com.example.androidapp1d.Stud.Booking.StudBookingItem;
import com.example.androidapp1d.Stud.StudNotificationActivity;
import com.example.androidapp1d.Stud.Profile.StudProfileActivity;
import com.example.androidapp1d.Stud.StudSearchActivity;
import com.example.androidapp1d.Stud.Booking.StudnewBookingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StudFeedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar actionBar;
    private NavigationView navigationView;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference allBookingsDatabaseReference;
    private Query upcomingBookingsQuery;
    private ArrayList<Integer> rawUpcomingBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.studactivity_feed);

            getSupportActionBar().setTitle("Feed");

            firebaseDatabase = FirebaseDatabase.getInstance();
            allBookingsDatabaseReference = firebaseDatabase.getReference().child("Bookings");
            Toast.makeText(this, "there are bookings", Toast.LENGTH_SHORT).show();

            upcomingBookingsQuery = allBookingsDatabaseReference.orderByChild("timestamp").startAt(new Date().getTime());
            upcomingBookingsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Toast.makeText(StudFeedActivity.this, "There are " +
                            dataSnapshot.getChildrenCount() + " upcomingBookings", Toast.LENGTH_SHORT).show();
                    Map<String, Integer> td = (HashMap<String, Integer>) dataSnapshot.getValue();
                    rawUpcomingBookings = new ArrayList<Integer>(td.values());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            ArrayList<StudBookingItem> upcomingBookingItems = getBookingItems(rawUpcomingBookings);

            RecyclerView rv1 = (RecyclerView) findViewById(R.id.recyclerviewUpcoming);
            rv1.setLayoutManager(new LinearLayoutManager(this));
            rv1.setItemAnimator(new DefaultItemAnimator());

            if (rawUpcomingBookings != null) {
                final StudFeedDetailsAdapter adapter = new StudFeedDetailsAdapter(this, upcomingBookingItems);
                rv1.setAdapter(adapter);
            }

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
                            Intent h = new Intent(StudFeedActivity.this, StudnewBookingActivity.class);
                            startActivity(h);
                            break;
                        case (R.id.side_profile):
                            Intent i = new Intent(StudFeedActivity.this, StudProfileActivity.class);
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
                            Intent h = new Intent(StudFeedActivity.this, StudBookingActivity.class);
                            startActivity(h);
                            break;
                        case (R.id.ic_profile):
                            Intent i = new Intent(StudFeedActivity.this, StudProfileActivity.class);
                            startActivity(i);
                            break;
                        case (R.id.ic_search):
                            Intent j = new Intent(StudFeedActivity.this, StudSearchActivity.class);
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

    public ArrayList<StudBookingItem> getBookingItems(ArrayList<Integer> rawBookingItems){
        ArrayList<StudBookingItem> bookingItems = new ArrayList<>();
        for (int i = 0; i < rawBookingItems.size(); i++) {
            StudBookingItem bookingItem = new StudBookingItem(rawBookingItems.get(i));
            bookingItems.add(bookingItem);
        }
        return bookingItems;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.studmenu_main,menu);
        return true;
    }

    public void goNotificationActivity(){
        startActivity(new Intent(this,StudNotificationActivity.class)) ;
    }

    public void goSearchActivity(){
        startActivity(new Intent(this,StudSearchActivity.class)) ;
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