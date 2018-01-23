package com.example.androidapp1d.Stud.Booking;

import android.content.Context;
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

import com.example.androidapp1d.R;
import com.example.androidapp1d.Stud.Feed.StudFeedActivity;
import com.example.androidapp1d.Stud.Feed.StudNotificationActivity;
import com.example.androidapp1d.Stud.Profile.StudProfileActivity;
import com.example.androidapp1d.Stud.StudSearchActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudBookingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar actionBar;
    private NavigationView navigationView;
    private TabHost tabHost;
    private FloatingActionButton addBooking;
    private RecyclerView rv1, rv2, rv3, rv4;
    private SearchView searchUpcoming, searchPrev, searchFavs, searchPending;
    private StudBookingDetailsAdapter adapter1 = null, adapter2 = null, adapter3 = null, adapter4 = null;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference referredBookingsRef, allBookingsRef, favRef, pendingRef, allPendingRef;
    private ArrayList<StudBookingItem> upcomingBookingItems = new ArrayList<>();
    private ArrayList<StudBookingItem> previousBookingItems = new ArrayList<>();
    private ArrayList<StudBookingItem> favouriteBookingItems = new ArrayList<>();
    private ArrayList<StudBookingItem> pendingBookingsItems = new ArrayList<>();
    private long timestamp;
    private int numAll, numFav, numPend;
    private String CREATOR;

    private Context context = StudBookingActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studactivity_booking);

        getSupportActionBar().setTitle("Your Bookings");

        Intent i = this.getIntent();
        CREATOR = i.getStringExtra("creator");

        tabHost = (TabHost) findViewById(R.id.bookingsTab);

        tabHost.setup();
        TabHost.TabSpec upcomingTab = tabHost.newTabSpec("FUTURE");
        upcomingTab.setIndicator("FUTURE");
        upcomingTab.setContent(R.id.Future);
        tabHost.addTab(upcomingTab);

        TabHost.TabSpec prevTab = tabHost.newTabSpec("PAST");
        prevTab.setIndicator("PAST");
        prevTab.setContent(R.id.Past);
        tabHost.addTab(prevTab);

        TabHost.TabSpec favTab = tabHost.newTabSpec("FAVS");
        favTab.setIndicator("FAVS");
        favTab.setContent(R.id.Favs);
        tabHost.addTab(favTab);

        TabHost.TabSpec pendingTab = tabHost.newTabSpec("PENDIN");
        pendingTab.setIndicator("PENDIN");
        pendingTab.setContent(R.id.Pending);
        tabHost.addTab(pendingTab);

        //fab button
        addBooking = (FloatingActionButton) findViewById(R.id.addBookingsButton);
        addBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudBookingActivity.this, StudnewBookingActivity.class);
                i.putExtra("studentName", CREATOR);
                startActivity(i);
            }
        });

        rv1 = (RecyclerView) findViewById(R.id.recyclerviewUpcoming);
        searchUpcoming = (SearchView) findViewById(R.id.searchUpcoming);
        rv2 = (RecyclerView) findViewById(R.id.recyclerviewPrevious);
        searchPrev = (SearchView) findViewById(R.id.searchPrevious);
        rv3 = (RecyclerView) findViewById(R.id.recyclerviewFavourites);
        searchFavs = (SearchView) findViewById(R.id.searchFavourites);
        rv4 = (RecyclerView) findViewById(R.id.recyclerviewPending);
        searchPending = (SearchView) findViewById(R.id.searchPending);

        //fetch booking details (by instantiation id) from firebase & store its details in ArrayList
        firebaseDatabase = FirebaseDatabase.getInstance();
        referredBookingsRef = firebaseDatabase.getReference().child("Bookings");
        allBookingsRef = firebaseDatabase.getReference().child("Students").child(CREATOR).child("allBookings");
        favRef =  firebaseDatabase.getReference().child("Students").child(CREATOR).child("favBookings");
        pendingRef =  firebaseDatabase.getReference().child("Students").child(CREATOR).child("pendingBookings");
        allPendingRef = firebaseDatabase.getReference().child("pendingBookings");

        //================================UPCOMING & PREVIOUS TAB===============================

        allBookingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numAll = (int) dataSnapshot.getChildrenCount();
                try {
                    for (DataSnapshot aBooking : dataSnapshot.getChildren()) {
                        referredBookingsRef.child(aBooking.getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    String id = dataSnapshot.getKey();
                                    String title = dataSnapshot.child("title").getValue(String.class);
                                    String prof = dataSnapshot.child("prof").getValue(String.class);
                                    String venue = dataSnapshot.child("venue").getValue(String.class);
                                    String date = dataSnapshot.child("date").getValue(String.class);
                                    String description = dataSnapshot.child("description").getValue(String.class);
                                    timestamp = dataSnapshot.child("timestamp").getValue(Long.class);
                                    if (timestamp >= System.currentTimeMillis()/1000) {
                                        upcomingBookingItems.add(new StudBookingItem(id, CREATOR, title, prof, venue, date, description));
                                    } else {
                                        previousBookingItems.add(new StudBookingItem(id, CREATOR, title, prof, venue, date, description));
                                    }
                                    if((upcomingBookingItems.size() + previousBookingItems.size()) == numAll){
                                        displayUpcoming();
                                        displayPrevious();
                                    }
                                } catch (Exception e){
                                    Toast.makeText(StudBookingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                } catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //================================FAVOURITES TAB===============================

        try {
            favRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    numFav = (int) dataSnapshot.getChildrenCount();
                    try {
                        for (DataSnapshot aBooking : dataSnapshot.getChildren()) {
                            referredBookingsRef.child(aBooking.getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String id = dataSnapshot.getKey();
                                    String title = dataSnapshot.child("title").getValue(String.class);
                                    String prof = dataSnapshot.child("prof").getValue(String.class);
                                    String venue = dataSnapshot.child("venue").getValue(String.class);
                                    String date = dataSnapshot.child("date").getValue(String.class);
                                    String description = dataSnapshot.child("description").getValue(String.class);
                                    favouriteBookingItems.add(new StudBookingItem(id, CREATOR, title, prof, venue, date, description));
                                    if(favouriteBookingItems.size() == numFav) {
                                        displayFavourites();
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }
                    } catch(Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //================================PENDING TAB================================

        pendingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numPend = (int) dataSnapshot.getChildrenCount();
                try {
                    for (DataSnapshot aBooking : dataSnapshot.getChildren()) {
                        allPendingRef.child(aBooking.getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String id = dataSnapshot.getKey();
                                String title = dataSnapshot.child("title").getValue(String.class);
                                String prof = dataSnapshot.child("prof").getValue(String.class);
                                String venue = dataSnapshot.child("venue").getValue(String.class);
                                String date = dataSnapshot.child("date").getValue(String.class);
                                String description = dataSnapshot.child("description").getValue(String.class);
                                pendingBookingsItems.add(new StudBookingItem(id, CREATOR, title, prof, venue, date, description));
                                if(pendingBookingsItems.size() == numPend) {
                                    displayPending();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                }catch(Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Toast.makeText(context, "Loading contents...", Toast.LENGTH_SHORT).show();
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
                        h.putExtra("creator", CREATOR);
                        startActivity(h);
                        break;
                    case (R.id.side_booking):
                        drawerLayout.closeDrawer(navigationView);
                        break;
                    case (R.id.side_profile):
                        Intent i= new Intent(StudBookingActivity.this,StudProfileActivity.class);
                        i.putExtra("creator", CREATOR);
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
                        h.putExtra("creator", CREATOR);
                        startActivity(h);
                        break;
                    case (R.id.ic_booking):
                        break;
                    case (R.id.ic_profile):
                        Intent i= new Intent(StudBookingActivity.this,StudProfileActivity.class);
                        i.putExtra("creator", CREATOR);
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

    public void displayUpcoming(){
        try {
            if(upcomingBookingItems == null){
                Toast.makeText(context, "No upcoming booking items", Toast.LENGTH_SHORT).show();
            } else {
                rv1.setLayoutManager(new LinearLayoutManager(this));
                rv1.setItemAnimator(new DefaultItemAnimator());
                adapter1 = new StudBookingDetailsAdapter(context, upcomingBookingItems);
                rv1.setAdapter(adapter1);
                searchUpcoming.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String query) {
                        //filter as you type
                        adapter1.getFilter().filter(query);
                        return false;
                    }
                });
            }
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void displayPrevious(){
        try {
            if(previousBookingItems == null){
                Toast.makeText(context, "No past booking items", Toast.LENGTH_SHORT).show();
            } else {
                rv2.setLayoutManager(new LinearLayoutManager(this));
                rv2.setItemAnimator(new DefaultItemAnimator());
                adapter2 = new StudBookingDetailsAdapter(context, previousBookingItems);
                rv2.setAdapter(adapter2);
                searchPrev.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String query) {
                        //filter as you type
                        adapter2.getFilter().filter(query);
                        return false;
                    }
                });
            }
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void displayFavourites(){
        try {
            if(favouriteBookingItems == null){
                Toast.makeText(context, "No favourite booking items", Toast.LENGTH_SHORT).show();
            } else {
                rv3.setLayoutManager(new LinearLayoutManager(this));
                rv3.setItemAnimator(new DefaultItemAnimator());
                adapter3 = new StudBookingDetailsAdapter(context, favouriteBookingItems);
                rv3.setAdapter(adapter3);
                searchFavs.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String query) {
                        //filter as you type
                        adapter3.getFilter().filter(query);
                        return false;
                    }
                });
            }
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void displayPending(){
        try {
            if(pendingBookingsItems == null){
                Toast.makeText(context, "No pending booking requests", Toast.LENGTH_SHORT).show();
            } else {
                rv4.setLayoutManager(new LinearLayoutManager(this));
                rv4.setItemAnimator(new DefaultItemAnimator());
                adapter4 = new StudBookingDetailsAdapter(context, pendingBookingsItems);
                rv4.setAdapter(adapter4);
                searchPending.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String query) {
                        //filter as you type
                        adapter4.getFilter().filter(query);
                        return false;
                    }
                });
            }
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
