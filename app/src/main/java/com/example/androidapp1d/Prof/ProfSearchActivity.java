package com.example.androidapp1d.Prof;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidapp1d.R;

import java.util.ArrayList;

public class ProfSearchActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private ArrayAdapter<String> lAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profactivity_search);

        ListView lv =(ListView)findViewById(R.id.profList);
        ArrayList<String> profList = new ArrayList<String>();
        profList.add("Oka Kurniawan");
        profList.add("Valerene Goh");
        profList.add("Cheryl Goh");
        profList.add("Nigel Chan");
        lAdapter = new ArrayAdapter<String>(ProfSearchActivity.this,android.R.layout.simple_list_item_1,profList);
        lv.setAdapter(lAdapter);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        final Menu menu = bottomNavigationView.getMenu();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case (R.id.ic_feed):
                        Intent h= new Intent(ProfSearchActivity.this,ProfFeedActivity.class);
                        startActivity(h);
                        break;
                    case (R.id.ic_booking):
                        Intent j= new Intent(ProfSearchActivity.this,ProfBookingActivity.class);
                        startActivity(j);
                        break;
                    case (R.id.ic_profile):
                        Intent i= new Intent(ProfSearchActivity.this,ProfProfileActivity.class);
                        startActivity(i);
                        break;
                    case(R.id.ic_search):
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profmenu_search,menu);
        MenuItem item = menu.findItem(R.id.menuSearchProf);
        android.widget.SearchView searchView = (android.widget.SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id==android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
