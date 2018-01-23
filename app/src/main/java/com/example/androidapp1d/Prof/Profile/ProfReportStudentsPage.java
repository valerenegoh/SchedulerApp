package com.example.androidapp1d.Prof.Profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidapp1d.LoginActivity;
import com.example.androidapp1d.R;

public class ProfReportStudentsPage extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_report_students_page);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reported List");

        /*
        //generate list
        LoginActivity.list= new ArrayList<String>();
        LoginActivity.list.add("Tracy Yee");
        LoginActivity.list.add("Cheryl Goh");
        LoginActivity.list.add("Lim Yu Fei");
        */


        //instantiate custom adapter
        ProfReportStudentsAdapter adapter = new ProfReportStudentsAdapter(LoginActivity.list, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.reportstudentlist);
        lView.setAdapter(adapter);

        ((ImageButton) findViewById(R.id.add_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((EditText)findViewById(R.id.inputblockedstudent)).getText().toString().matches("")) {
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter a name";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    LoginActivity.list.add(((EditText) findViewById(R.id.inputblockedstudent)).getText().toString());
                    //instantiate custom adapter
                    ProfReportStudentsAdapter adapter = new ProfReportStudentsAdapter(LoginActivity.list, ProfReportStudentsPage.this);

                    //handle listview and assign adapter
                    ListView lView = (ListView) findViewById(R.id.reportstudentlist);
                    lView.setAdapter(adapter);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();

        if(id==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
