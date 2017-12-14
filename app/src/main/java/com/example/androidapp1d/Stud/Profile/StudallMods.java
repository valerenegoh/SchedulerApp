package com.example.androidapp1d.Stud.Profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidapp1d.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ASUS on 12/12/2017.
 */

public class StudallMods extends AppCompatActivity {

    private ListView lv;
    private String[] modList;
    private String CREATOR, clickedMod;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference modsRef;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.stud_profileallmod);

            Intent i = this.getIntent();
//        CREATOR = i.getStringExtra("studentName");
            CREATOR = "Valerene Goh";

            firebaseDatabase = FirebaseDatabase.getInstance();
            modsRef = firebaseDatabase.getReference().child("Students").child(CREATOR).child("mods");

//            modList = (String[]) ArrayUtils.addAll(getResources().getStringArray(R.array.ISTD_modules), getResources().getStringArray(R.array.ESD_modules),
//                    getResources().getStringArray(R.array.EPD_modules), getResources().getStringArray(R.array.ASD_modules), getResources().getStringArray(R.array.common_modules));
            modList = getResources().getStringArray(R.array.ISTD_modules);

            lv = (ListView) findViewById(R.id.modulesList);

            final ArrayAdapter<String> lAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, modList);
            lv.setAdapter(lAdapter2);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    clickedMod = modList[position];
                    builder = new AlertDialog.Builder(StudallMods.this);
                    builder.setTitle("Add module?");
                    builder.setMessage("Any consultations about this module will appear on your feed.");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            modsRef.push().setValue(clickedMod);
                            Toast.makeText(StudallMods.this, clickedMod + " added to your list of modules", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });

            SearchView searchMods = (SearchView) findViewById(R.id.searchModules);
            searchMods.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        } catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
