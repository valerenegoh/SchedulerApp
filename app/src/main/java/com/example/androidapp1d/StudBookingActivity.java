package com.example.androidapp1d;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class StudBookingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar actionBar;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studactivity_feed);

        getSupportActionBar().setTitle("Your Bookings");

        ListView lv =(ListView)findViewById(R.id.profList);
        ArrayList<String> profList = new ArrayList<String>();
        profList.add("Oka Kurniawan");
        profList.add("Valerene Goh");
        profList.add("Cheryl Goh");
        profList.add("Nigel Chan");
        ArrayAdapter<String> lAdapter = new ArrayAdapter<String>(StudBookingActivity.this,android.R.layout.simple_list_item_1,profList);
        lv.setAdapter(lAdapter);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.studmenu_main,menu);
        return true;
    }

    public void goNextActivity(){
        startActivity(new Intent(this,StudNotificationActivity.class)) ;
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
            goNextActivity();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
