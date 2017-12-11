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
import com.example.androidapp1d.Stud.Profile.StudProfileActivity;
import com.example.androidapp1d.Stud.StudNotificationActivity;
import com.example.androidapp1d.Stud.StudSearchActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class StudBookingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar actionBar;
    private NavigationView navigationView;
    private TabHost tabHost;
    private FloatingActionButton addBooking;
    private RecyclerView rv1, rv2, rv3, rv4;
    private SearchView searchUpcoming, searchPrev, searchFavs, searchPending;
    private StudBookingDetailsAdapter adapter = null;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference referredBookingsRef, allBookingsRef, favRef, pendingRef;
    private ArrayList<String> rawBookings = new ArrayList<>();
    private ArrayList<String> rawUpcomingBookings = new ArrayList<>();
    private ArrayList<String> rawPreviousBookings = new ArrayList<>();
    private ArrayList<String> rawFavouriteBookings = new ArrayList<>();
    private ArrayList<String> rawPendingBookings = new ArrayList<>();
    ArrayList<StudBookingItem> upcomingBookingItems, previousBookingItems, favouriteBookingItems, pendingBookingsItems;
    private long timestamp;

    private Context context = StudBookingActivity.this;

    private static final String KEY = "Valerene Goh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studactivity_booking);

        getSupportActionBar().setTitle("Your Bookings");

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

        TabHost.TabSpec pendingTab = tabHost.newTabSpec("PENDING");
        pendingTab.setIndicator("PENDING");
        pendingTab.setContent(R.id.Pending);
        tabHost.addTab(pendingTab);

        //fab button
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
        referredBookingsRef = firebaseDatabase.getReference().child("Bookings");
        allBookingsRef = firebaseDatabase.getReference().child("Students").child(KEY).child("allBookings");
        favRef =  firebaseDatabase.getReference().child("Students").child(KEY).child("favBookings");
        pendingRef =  firebaseDatabase.getReference().child("Students").child(KEY).child("pendingBookings");

        allBookingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot aBooking: dataSnapshot.getChildren()) {
                    rawBookings.add(aBooking.getValue(String.class));
                }
                runTimeComparison();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //================================UPCOMING TAB================================

        rv1 = (RecyclerView) findViewById(R.id.recyclerviewUpcoming);
        rv1.setLayoutManager(new LinearLayoutManager(this));
        rv1.setItemAnimator(new DefaultItemAnimator());
        searchUpcoming = (SearchView) findViewById(R.id.searchUpcoming);

        //================================PREVIOUS TAB================================

        rv2 = (RecyclerView) findViewById(R.id.recyclerviewPrevious);
        rv2.setLayoutManager(new LinearLayoutManager(this));
        rv2.setItemAnimator(new DefaultItemAnimator());
        searchPrev = (SearchView) findViewById(R.id.searchPrevious);

        //================================FAVOURITES TAB================================

        rv3 = (RecyclerView) findViewById(R.id.recyclerviewFavourites);
        rv3.setLayoutManager(new LinearLayoutManager(this));
        rv3.setItemAnimator(new DefaultItemAnimator());
        searchFavs = (SearchView) findViewById(R.id.searchFavourites);

        try {
            favRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot aBooking : dataSnapshot.getChildren()) {
                        rawFavouriteBookings.add(aBooking.getValue(String.class));
                    }
                    if (rawFavouriteBookings == null) {
                        Toast.makeText(context, "No favourite booking items", Toast.LENGTH_SHORT).show();
                    } else {
                        favouriteBookingItems = getBookingItems(rawFavouriteBookings, false);
                        adapter = new StudBookingDetailsAdapter(context, favouriteBookingItems);
                    }
                    try {
                        //TODO: think of another work around the asynchronousity. progress bar?
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    displayFavourites();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //================================PENDING TAB================================

        rv4 = (RecyclerView) findViewById(R.id.recyclerviewPending);
        rv4.setLayoutManager(new LinearLayoutManager(this));
        rv4.setItemAnimator(new DefaultItemAnimator());
        searchPending = (SearchView) findViewById(R.id.searchPending);

        pendingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot aBooking: dataSnapshot.getChildren()) {
                    rawPendingBookings.add(aBooking.getValue(String.class));
                }
                if (rawPendingBookings == null) {
                    Toast.makeText(context, "No pending booking items", Toast.LENGTH_SHORT).show();
                } else {
                    pendingBookingsItems = getBookingItems(rawPendingBookings, true);
                    adapter = new StudBookingDetailsAdapter(context, pendingBookingsItems);
                }
                try {
                    //TODO: think of another work around the asynchronousity. progress bar?
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                displayPending();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

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

    public void runTimeComparison(){
        //get the IDs
        referredBookingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(int i = 0; i < rawBookings.size(); i++){
                    timestamp = dataSnapshot.child(rawBookings.get(i)).child("timestamp").getValue(Long.class);
                    if(timestamp >= System.currentTimeMillis()/1000){
                        rawUpcomingBookings.add(rawBookings.get(i));
                    } else{
                        rawPreviousBookings.add(rawBookings.get(i));
                    }
                }

                //================upcoming tab================
                if (rawUpcomingBookings == null) {
                    Toast.makeText(context, "No upcoming booking items", Toast.LENGTH_SHORT).show();
                } else {
                    upcomingBookingItems = getBookingItems(rawUpcomingBookings, false);
                    adapter = new StudBookingDetailsAdapter(context, upcomingBookingItems);
                }
                try {
                    //TODO: think of another work around the asynchronousity. progress bar?
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                displayUpcoming();

                //================previous tab================
                if (rawPreviousBookings == null) {
                    Toast.makeText(context, "No previous booking items", Toast.LENGTH_SHORT).show();
                } else {
                    previousBookingItems = getBookingItems(rawPreviousBookings, false);
                    adapter = new StudBookingDetailsAdapter(context, previousBookingItems);
                }
                try {
                    //TODO: think of another work around the asynchronousity. progress bar?
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                displayPrevious();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void displayUpcoming(){
        try {
            rv1.setAdapter(adapter);
            searchUpcoming.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void displayPrevious(){
        try {
            rv2.setAdapter(adapter);
            searchPrev.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void displayFavourites(){
        try {
            rv3.setAdapter(adapter);
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
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void displayPending(){
        try {
            rv4.setAdapter(adapter);
            searchPending.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<StudBookingItem> getBookingItems(ArrayList<String> rawBookingItems, boolean isPending){
        ArrayList<StudBookingItem> bookingItems = new ArrayList<>();
        for (int i = 0; i < rawBookingItems.size(); i++) {
            StudBookingItem bookingItem = new StudBookingItem(context, rawBookingItems.get(i), isPending);
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
