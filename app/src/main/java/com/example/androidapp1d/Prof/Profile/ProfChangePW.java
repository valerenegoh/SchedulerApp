package com.example.androidapp1d.Prof.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidapp1d.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfChangePW extends AppCompatActivity {

    private Button submitButton;
    private EditText enterpw;
    private EditText reEnterpw;
    private CheckBox mCbShowPwd;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference studpwRef;
    private String creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change My Password");
        submitButton = (Button) findViewById(R.id.submitbutton);
        enterpw = (EditText) findViewById(R.id.enternewpw);
        reEnterpw = (EditText) findViewById(R.id.reenterpw);
        mCbShowPwd = (CheckBox) findViewById(R.id.cbShowPwd);

        Intent i = this.getIntent();
        creator = i.getStringExtra("creator");

        firebaseDatabase = FirebaseDatabase.getInstance();
        studpwRef = firebaseDatabase.getReference().child("Professors").child(creator).child("password");

        mCbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    enterpw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    reEnterpw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    enterpw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    reEnterpw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Context context = view.getContext();
            if(!enterpw.getText().toString().equals(reEnterpw.getText().toString())){
                Toast.makeText(context, "Passwords don't match!", Toast.LENGTH_SHORT).show();
            } else {
                studpwRef.setValue(enterpw.getText().toString());
                Toast.makeText(context, "Password has been updated successfully!", Toast.LENGTH_LONG).show();
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



    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Cheryl","onStart() is invoked");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Cheryl", "onStop() is invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Cheryl","onDestroy() is invoked");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Cheryl", "onResume() is invoked");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Cheryl","onPause() is invoked");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Cheryl","onRestart() is invoked");
    }



}
