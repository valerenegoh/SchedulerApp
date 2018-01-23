package com.example.androidapp1d.Stud.Profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.androidapp1d.R;
import com.example.androidapp1d.Stud.Booking.StudBookingActivity;
import com.example.androidapp1d.Stud.Feed.StudFeedActivity;
import com.example.androidapp1d.Stud.Feed.StudNotificationActivity;
import com.example.androidapp1d.Stud.StudSearchActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar actionBar;
    private NavigationView navigationView;
    private Button personal, modsprofs, friends, capButton, pwButton, biblioButton;
    private AlertDialog.Builder builder, builder2;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference studRef;
    private String CREATOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studactivity_profile);

        getSupportActionBar().setTitle("Your Profile");

        Intent i = this.getIntent();
        CREATOR = i.getStringExtra("creator");

        firebaseDatabase = FirebaseDatabase.getInstance();
        studRef = firebaseDatabase.getReference().child("Students").child(CREATOR);

        personal = (Button) findViewById(R.id.personal_info_button);
        modsprofs = (Button) findViewById(R.id.booking_settings_button);
        capButton = (Button) findViewById(R.id.change_cap_button);
        friends = (Button) findViewById(R.id.friends_button);
        pwButton = (Button) findViewById(R.id.change_pw_button);
        biblioButton = (Button) findViewById(R.id.change_biblio_button);

        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudProfileActivity.this, StudPersonalDetails.class);
                i.putExtra("studentName", CREATOR);
                startActivity(i);
            }
        });

        modsprofs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudProfileActivity.this, StudeditProfModActivity.class);
                i.putExtra("studentName", CREATOR);
                startActivity(i);
            }
        });

        capButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setup layout
                LinearLayout layout = new LinearLayout(StudProfileActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setGravity(Gravity.CENTER_HORIZONTAL);
                layout.setPadding(50, 50, 50, 50);
                final EditText inputCap = new EditText(StudProfileActivity.this);
                inputCap.setHint("Enter new default booking capacity");
                inputCap.setPadding(20, 30, 20, 30);
                inputCap.setInputType(InputType.TYPE_CLASS_NUMBER);
                layout.addView(inputCap);

                builder = new AlertDialog.Builder(StudProfileActivity.this);
                builder.setCancelable(false)
                    .setView(layout)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            studRef.child("capacity").setValue(inputCap.getText());
                            Toast.makeText(StudProfileActivity.this, "default capacity changed to " +
                                    inputCap.getText().toString(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                builder.create().show();
            }
        });

        biblioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setup layout
                LinearLayout layout = new LinearLayout(StudProfileActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setGravity(Gravity.CENTER_HORIZONTAL);
                layout.setPadding(50, 50, 50, 50);
                final EditText inputBiblio = new EditText(StudProfileActivity.this);
                inputBiblio.setHint("Enter new bibliography write-up");
                inputBiblio.setPadding(20, 30, 20, 30);
                layout.addView(inputBiblio);

                builder2 = new AlertDialog.Builder(StudProfileActivity.this);
                builder2.setCancelable(false)
                        .setView(layout)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                studRef.child("description").setValue(inputBiblio.getText());
                                Toast.makeText(StudProfileActivity.this, "bibliography updated", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder2.create().show();
            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudProfileActivity.this, StudFriends.class);
                i.putExtra("studentName", CREATOR);
                startActivity(i);
            }
        });

        pwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudProfileActivity.this, StudChangePW.class);
                i.putExtra("creator", CREATOR);
                startActivity(i);
            }
        });

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
                        h.putExtra("creator", CREATOR);
                        startActivity(h);
                        break;
                    case (R.id.side_booking):
                        Intent i= new Intent(StudProfileActivity.this, StudBookingActivity.class);
                        i.putExtra("creator", CREATOR);
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
                        h.putExtra("creator", CREATOR);
                        startActivity(h);
                        break;
                    case (R.id.ic_booking):
                        Intent i= new Intent(StudProfileActivity.this,StudBookingActivity.class);
                        i.putExtra("creator", CREATOR);
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
