package com.example.androidapp1d;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

public class StudnewBookingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar actionBar;
    private NavigationView navigationView;
    private TabHost tabHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stud_newbooking);

        getSupportActionBar().setTitle("Your Bookings");

        tabHost = (TabHost) findViewById(R.id.newbookingsTab);
        tabHost.setup();
        TabHost.TabSpec profsTab = tabHost.newTabSpec("PROF TAB");
        profsTab.setIndicator("PROF TAB");
        profsTab.setContent(R.id.Professors);
        tabHost.addTab(profsTab);

        TabHost.TabSpec modsTab = tabHost.newTabSpec("MODS TAB");
        modsTab.setIndicator("MODS TAB");
        modsTab.setContent(R.id.Modules);
        tabHost.addTab(modsTab);

        //================================PROFESSORS TAB================================

        ListView lv2 = (ListView)findViewById(R.id.professorsList);
        ArrayList<String> profList = new ArrayList<>();
        profList.add("Oka Kurniawan");
        profList.add("Ngai-Man Cheung");
        profList.add("Jit Biswas");
        profList.add("Norman Lee");
        final ArrayAdapter<String> lAdapter2 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,profList);
        lv2.setAdapter(lAdapter2);

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

        //================================MODULES TAB================================

        ListView lv1 = (ListView)findViewById(R.id.modulesList);
        ArrayList<String> modList = new ArrayList<>();
        modList.add("50.001 Introduction to Information Systems and Programming");
        modList.add("50.002 Computational Structures");
        modList.add("50.004 Introduction to Algorithms");
        modList.add("02.105 Sages Through the Ages");
        final ArrayAdapter<String> lAdapter1 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,modList);
        lv1.setAdapter(lAdapter1);

        SearchView searchMods = (SearchView) findViewById(R.id.searchModules);
        searchMods.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lAdapter1.getFilter().filter(newText);
                return false;
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
                        Intent h= new Intent(StudnewBookingActivity.this,StudFeedActivity.class);
                        startActivity(h);
                        break;
                    case (R.id.side_booking):
                        Intent l= new Intent(StudnewBookingActivity.this,StudBookingActivity.class);
                        startActivity(l);
                        break;
                    case (R.id.side_profile):
                        Intent i= new Intent(StudnewBookingActivity.this,StudProfileActivity.class);
                        startActivity(i);
                        break;
                    case(R.id.ic_search):
                        Intent j= new Intent(StudnewBookingActivity.this,StudSearchActivity.class);
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
                        Intent h= new Intent(StudnewBookingActivity.this,StudFeedActivity.class);
                        startActivity(h);
                        break;
                    case (R.id.ic_booking):
                        Intent l= new Intent(StudnewBookingActivity.this,StudBookingActivity.class);
                        startActivity(l);
                        break;
                    case (R.id.ic_profile):
                        Intent i= new Intent(StudnewBookingActivity.this,StudProfileActivity.class);
                        startActivity(i);
                        break;
                    case(R.id.ic_search):
                        Intent j= new Intent(StudnewBookingActivity.this,StudSearchActivity.class);
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
