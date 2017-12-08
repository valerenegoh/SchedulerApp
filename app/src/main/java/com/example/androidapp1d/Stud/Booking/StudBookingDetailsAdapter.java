package com.example.androidapp1d.Stud.Booking;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.androidapp1d.R;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/5/2017.
 */

public class StudBookingDetailsAdapter extends RecyclerView.Adapter<StudBookingDetailsHolder> implements Filterable {

    Context context;
    ArrayList<StudBookingItem> bookings, filterList;
    StudBookingCustomFilter filter;
    public static final String KEY = "GetBookingDetails";

    public StudBookingDetailsAdapter(Context context, ArrayList<StudBookingItem> bookings){
        this.context = context;
        this.bookings = bookings;
        this.filterList = bookings;
    }

    @Override
    public StudBookingDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Convert to view object
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookings_card, null);
        StudBookingDetailsHolder holder = new StudBookingDetailsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StudBookingDetailsHolder holder, final int position) {
        //bind data to view
        holder.title.setText(bookings.get(position).getTitle());
        holder.prof.setText(bookings.get(position).getProf());
        holder.venue.setText(bookings.get(position).getVenue());
        holder.date.setText(bookings.get(position).getDate());
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

        //implement click listener
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence bookingTitle = bookings.get(position).getTitle();
                Snackbar.make(v, bookingTitle + " delete", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    //return filter object
    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new StudBookingCustomFilter(this, filterList);
        }
        return filter;
    }
}
