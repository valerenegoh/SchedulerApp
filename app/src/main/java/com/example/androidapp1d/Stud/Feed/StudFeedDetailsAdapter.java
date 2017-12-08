package com.example.androidapp1d.Stud.Feed;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidapp1d.R;
import com.example.androidapp1d.Stud.Booking.StudBookingCustomFilter;
import com.example.androidapp1d.Stud.Booking.StudBookingItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by ASUS on 12/5/2017.
 */

public class StudFeedDetailsAdapter extends RecyclerView.Adapter<StudFeedDetailsHolder> {

    Context context;
    ArrayList<StudBookingItem> bookings, filterList;
    StudBookingCustomFilter filter;
    public static final String KEY = "GetBookingDetails";
    private String mod;
    private long timestamp;
    private String time;

    public StudFeedDetailsAdapter(Context context, ArrayList<StudBookingItem> bookings){
        this.context = context;
        this.bookings = bookings;
        this.filterList = bookings;
    }

    @Override
    public StudFeedDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Convert to view object
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_card, null);
        StudFeedDetailsHolder holder = new StudFeedDetailsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StudFeedDetailsHolder holder, final int position) {
        //bind data to view

        mod = bookings.get(position).getMod();
        timestamp = bookings.get(position).getTimestamp();
        if(DateUtils.isToday(timestamp)){
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            time = formatter.format(timestamp);
        }
        holder.announcement.setText(mod + " starting at " + time);
        holder.title.setText(bookings.get(position).getTitle());
        holder.prof.setText(bookings.get(position).getProf());
        holder.time.setText(bookings.get(position).getTiming());
        holder.description.setText(bookings.get(position).getDescription());

        //implement click listener
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence bookingTitle = bookings.get(position).getTitle();
                /*Intent i = new Intent(context, StudBookingItem.class);
                i.putExtra(KEY, bookingTitle);
                context.startActivity(i);*/
                Snackbar.make(v, bookingTitle + " details", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }
}