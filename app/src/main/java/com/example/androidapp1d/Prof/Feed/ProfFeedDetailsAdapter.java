package com.example.androidapp1d.Prof.Feed;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidapp1d.R;
import com.example.androidapp1d.Stud.Booking.StudBookingDetails;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/5/2017.
 */

public class ProfFeedDetailsAdapter extends RecyclerView.Adapter<ProfFeedDetailsHolder> {

    Context context;
    ArrayList<ProfFeedItem> bookings;
    private String title;
    private long timestamp;
    private String time, timing, creator;

    public ProfFeedDetailsAdapter(Context context, ArrayList<ProfFeedItem> bookings){
        this.context = context;
        this.bookings = bookings;
    }

    @Override
    public ProfFeedDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Convert to view object
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stud_feed_card, null);
        ProfFeedDetailsHolder holder = new ProfFeedDetailsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProfFeedDetailsHolder holder, final int position) {
        //bind data to view
        try {
            title = bookings.get(position).getTitle();
            timing = bookings.get(position).getTiming();
            time = timing.substring(0, timing.indexOf("-"));

            holder.announcement.setText(title + " starts at " + time);
            holder.mod.setText(bookings.get(position).getMod());
            holder.prof.setText(bookings.get(position).getProf());
            holder.creator.setText(bookings.get(position).getCreator());
            holder.description.setText(bookings.get(position).getDescription());
            creator = bookings.get(position).getStudent();

            //implement click listener
            holder.details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSequence bookingTitle = bookings.get(position).getTitle();
                Intent i = new Intent(context, StudBookingDetails.class);
                i.putExtra("creator", creator);
                i.putExtra("bookingID", bookings.get(position).getId());
                context.startActivity(i);
                Snackbar.make(v, bookingTitle + " details", Snackbar.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }
}