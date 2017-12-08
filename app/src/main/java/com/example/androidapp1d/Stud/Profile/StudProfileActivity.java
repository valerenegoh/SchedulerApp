package com.example.androidapp1d.Stud.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.androidapp1d.R;
import com.example.androidapp1d.Stud.Feed.StudFeedActivity;
import com.example.androidapp1d.Stud.Booking.StudBookingActivity;
import com.example.androidapp1d.Stud.StudNotificationActivity;
import com.example.androidapp1d.Stud.StudSearchActivity;
import com.example.androidapp1d.Stud.Booking.StudnewBookingActivity;

public class StudProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar actionBar;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studactivity_profile);

        getSupportActionBar().setTitle("Your Profile");

        drawerLayout =(DrawerLayout)findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.opendrawer, R.string.closedrawer);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView)findViewById(R.id.nav_view);
        final Menu menu1 =navigationView.getMenu();
        MenuItem dProfile = menu1.getItem(2);
        dProfile.setChecked(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case (R.id.side_feed):
                        Intent h= new Intent(StudProfileActivity.this,StudFeedActivity.class);
                        startActivity(h);
                        break;
                    case (R.id.side_booking):
                        Intent i= new Intent(StudProfileActivity.this,StudnewBookingActivity.class);
                        startActivity(i);
                        break;
                    case (R.id.side_profile):
                        drawerLayout.closeDrawer(navigationView);
                        break;
                }
                return  false;
            }
        });



        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        final Menu menu = bottomNavigationView.getMenu();
        MenuItem bProfile = menu.getItem(2);
        bProfile.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case (R.id.ic_feed):
                        Intent h= new Intent(StudProfileActivity.this,StudFeedActivity.class);
                        startActivity(h);
                        break;
                    case (R.id.ic_booking):
                        Intent i= new Intent(StudProfileActivity.this,StudBookingActivity.class);
                        startActivity(i);
                        break;
                    case (R.id.ic_profile):
                        break;
                    case(R.id.ic_search):
                        Intent j= new Intent(StudProfileActivity.this,StudSearchActivity.class);
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
