package com.example.androidapp1d;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

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

public class StudBookingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar actionBar;
    private NavigationView navigationView;
    private TabHost tabHost;
    private FloatingActionButton addBooking;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference allBookingsDatabaseReference;
    private Query upcomingBookingsQuery;
    private Query previousBookingsQuery;
    private DatabaseReference favBookingsDatabaseReference;
    private ArrayList<Integer> rawUpcomingBookings;
    private ArrayList<Integer> rawPreviousBookings;
    private ArrayList<Integer> rawFavouriteBookings;
    private static final String KEY = "User1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studactivity_booking);

        getSupportActionBar().setTitle("Your Bookings");

        tabHost = (TabHost) findViewById(R.id.bookingsTab);
        tabHost.setup();
        TabHost.TabSpec upcomingTab = tabHost.newTabSpec("UPCOMING");
        upcomingTab.setIndicator("UPCOMING");
        upcomingTab.setContent(R.id.Upcoming);
        tabHost.addTab(upcomingTab);

        TabHost.TabSpec prevTab = tabHost.newTabSpec("PREVIOUS");
        prevTab.setIndicator("PREVIOUS");
        prevTab.setContent(R.id.Previous);
        tabHost.addTab(prevTab);

        TabHost.TabSpec favTab = tabHost.newTabSpec("FAVOURITES");
        favTab.setIndicator("FAVOURITES");
        favTab.setContent(R.id.Favourites);
        tabHost.addTab(favTab);

        addBooking = (FloatingActionButton) findViewById(R.id.addBookingsButton);
        addBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudBookingActivity.this, StudnewBookingActivity.class);
                startActivity(i);
            }
        });

        //fetch booking details (by instantiation id) from firebase & store its details in ArrayList
        firebaseDatabase = FirebaseDatabase.getInstance();
        allBookingsDatabaseReference = firebaseDatabase.getReference().child("Students").child(KEY).child("allBookings");
        favBookingsDatabaseReference =  firebaseDatabase.getReference().child("Students").child(KEY).child("favBookings");

        //================================UPCOMING TAB================================
        upcomingBookingsQuery = allBookingsDatabaseReference.orderByChild("timestamp").startAt(new Date().getTime());
        upcomingBookingsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(StudBookingActivity.this, "You have " +
                        dataSnapshot.getChildrenCount() + " upcomingBookings", Toast.LENGTH_SHORT).show();
                Map<String, Integer> td = (HashMap<String,Integer>) dataSnapshot.getValue();
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
            final StudBookingDetailsAdapter adapter = new StudBookingDetailsAdapter(this, upcomingBookingItems);
            rv1.setAdapter(adapter);

            SearchView searchFavs = (SearchView) findViewById(R.id.searchUpcoming);
            searchFavs.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    //filter as you type
                    adapter.getFilter().filter(query);
                    return false;
                }
            });
        }

        //================================PREVIOUS TAB================================
        previousBookingsQuery = allBookingsDatabaseReference.orderByChild("timestamp").endAt(new Date().getTime());
        previousBookingsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(StudBookingActivity.this, "You have " +
                        dataSnapshot.getChildrenCount() + " previousBookings", Toast.LENGTH_SHORT).show();
                Map<String, Integer> td = (HashMap<String,Integer>) dataSnapshot.getValue();
                rawPreviousBookings = new ArrayList<Integer>(td.values());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ArrayList<StudBookingItem> PreviousBookingItems = getBookingItems(rawPreviousBookings);

        RecyclerView rv2 = (RecyclerView) findViewById(R.id.recyclerviewPrevious);
        rv2.setLayoutManager(new LinearLayoutManager(this));
        rv2.setItemAnimator(new DefaultItemAnimator());

        if (rawPreviousBookings != null) {
            final StudBookingDetailsAdapter adapter = new StudBookingDetailsAdapter(this, PreviousBookingItems);
            rv2.setAdapter(adapter);

            SearchView searchFavs = (SearchView) findViewById(R.id.searchPrevious);
            searchFavs.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    //filter as you type
                    adapter.getFilter().filter(query);
                    return false;
                }
            });
        }

        //================================FAVOURITES TAB================================
        favBookingsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(StudBookingActivity.this, "You have " +
                        dataSnapshot.getChildrenCount() + " favouriteBookings", Toast.LENGTH_SHORT).show();
                Map<String, Integer> td = (HashMap<String,Integer>) dataSnapshot.getValue();
                rawFavouriteBookings = new ArrayList<Integer>(td.values());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ArrayList<StudBookingItem> FavouriteBookingItems = getBookingItems(rawFavouriteBookings);

        RecyclerView rv3 = (RecyclerView) findViewById(R.id.recyclerviewFavourites);
        rv3.setLayoutManager(new LinearLayoutManager(this));
        rv3.setItemAnimator(new DefaultItemAnimator());

        if (rawFavouriteBookings != null) {
            final StudBookingDetailsAdapter adapter = new StudBookingDetailsAdapter(this, FavouriteBookingItems);
            rv3.setAdapter(adapter);

            SearchView searchFavs = (SearchView) findViewById(R.id.searchFavourites);
            searchFavs.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    //filter as you type
                    adapter.getFilter().filter(query);
                    return false;
                }
            });
        }

        //=============================================================================

        drawerLayout =(DrawerLayout)findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.opendrawer, R.string.closedrawer);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView)findViewById(R.id.nav_view);
        final Menu menu1 =navigationView.getMenu();
        MenuItem dBooking = menu1.getItem(1);
        dBooking.setChecked(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case (R.id.side_feed):
                        Intent h= new Intent(StudBookingActivity.this,StudFeedActivity.class);
                        startActivity(h);
                        break;
                    case (R.id.side_booking):
                        drawerLayout.closeDrawer(navigationView);
                        break;
                    case (R.id.side_profile):
                        Intent i= new Intent(StudBookingActivity.this,StudProfileActivity.class);
                        startActivity(i);
                        break;
                    case(R.id.ic_search):
                        Intent j= new Intent(StudBookingActivity.this,StudSearchActivity.class);
                        startActivity(j);
                        break;
                }
                return  false;
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem bBooking = menu.getItem(1);
        bBooking.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case (R.id.ic_feed):
                        Intent h= new Intent(StudBookingActivity.this,StudFeedActivity.class);
                        startActivity(h);
                        break;
                    case (R.id.ic_booking):
                        break;
                    case (R.id.ic_profile):
                        Intent i= new Intent(StudBookingActivity.this,StudProfileActivity.class);
                        startActivity(i);
                        break;
                    case(R.id.ic_search):
                        Intent j= new Intent(StudBookingActivity.this,StudSearchActivity.class);
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
            StudBookingItem bookingItem = new StudBookingItem(this, rawBookingItems.get(i));
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
