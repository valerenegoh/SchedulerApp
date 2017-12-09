package com.example.androidapp1d;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;

    private TextView message;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;
    private ArrayList<String> retrievelist = new ArrayList<>();
    private Button getKey;
    String key;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference messagesdatabaseReference;
    DatabaseReference venueRef;
    private static final String PROF = "uniqueID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            firebaseDatabase = FirebaseDatabase.getInstance();
            messagesdatabaseReference = firebaseDatabase.getReference().child("Students/User1/Preferences/capacity");
            venueRef = firebaseDatabase.getReference().child("Professors").child(PROF).child("Preferences/venue");

            // Initialize references to views
            mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
            message = (TextView) findViewById(R.id.messageText);
            mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
            mMessageEditText = (EditText) findViewById(R.id.messageEditText);
            mSendButton = (Button) findViewById(R.id.sendButton);
            getKey = (Button) findViewById(R.id.getkey);

            // Initialize progress bar
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);

            // ImagePickerButton shows an image picker to upload a image for a message
            mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: Fire an intent to show an image picker
                }
            });

            // Enable Send button when there's text to send
            mMessageEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().trim().length() > 0) {
                        mSendButton.setEnabled(true);
                    } else {
                        mSendButton.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

            // Send button sends a message and clears the EditText
            mSendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: Send messages on click
                    String message = mMessageEditText.getText().toString();
                    messagesdatabaseReference.push().setValue(message);
                    // Clear input box
                    mMessageEditText.setText("");
                }
            });

            getKey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*messagesdatabaseReference.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            //get arraylist from database
                            String mymessage = dataSnapshot.getValue(String.class);
                            retrievelist.add(mymessage);
                            message.setText(retrievelist.toString());
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/

                    /*messagesdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                        //retrieving data via unique key
                        Integer messageTxt = dataSnapshot.getValue(Integer.class);
                        message.setText(messageTxt.toString());

                        //switching branches
//                        keysdatabaseReference.child(key).setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
//                            @Override
//                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                if(databaseError == null) {
//                                    messagesdatabaseReference.child(key).setValue(null);
//                                } else{
//                                    Toast.makeText(MainActivity.this, "failed to switch branch", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });*/

                    venueRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String venue = dataSnapshot.getValue(String.class);
                            message.setText(venue);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            });
        }catch(Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
