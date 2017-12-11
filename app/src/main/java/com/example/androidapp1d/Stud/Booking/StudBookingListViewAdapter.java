package com.example.androidapp1d.Stud.Booking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp1d.R;

/**
 * Created by ASUS on 12/8/2017.
 */

public class StudBookingListViewAdapter extends BaseAdapter {

    Context context;
    String[] timeSlots;
    Integer[] colors;
    String[] availability;
    String[] bookingIDs;
    String profName, Date;
    private static LayoutInflater inflater = null;

    public StudBookingListViewAdapter(Context context, String profName, String Date, String[] timeSlots, Integer[] colors, String[] availability, String[] bookingIDs) {
        this.context = context;
        this.profName = profName;
        this.Date = Date;
        this.timeSlots = timeSlots;
        this.colors = colors;
        this.availability = availability;
        this.bookingIDs = bookingIDs;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        View tile;
        TextView title, timeSlot, availability;
        Button book;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.stud_bookingcustomslot, null);
        holder.tile = (View) rowView.findViewById(R.id.availabilityIndicator);
        holder.title = (TextView) rowView.findViewById(R.id.booking_title);
        holder.timeSlot = (TextView) rowView.findViewById(R.id.time_slot);
        holder.availability = (TextView) rowView.findViewById(R.id.availability);
        holder.book = (Button) rowView.findViewById(R.id.bookButton);

        holder.tile.setBackgroundResource(colors[position]);
        holder.timeSlot.setText(timeSlots[position]);
        holder.availability.setText(availability[position]);
        if(bookingIDs[position] != null){
            holder.title.setText(bookingIDs[position]);
            if(availability[position].equals("Available")){
                holder.book.setText("Book");
                holder.book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, StudBookingApplication.class);
                        i.putExtra("time", timeSlots[position]);
                        i.putExtra("date", Date);
                        i.putExtra("prof", profName);
                        context.startActivity(i);
                    }
                });
            } else{
                holder.book.setText("Join");
                holder.book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Joining", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else{
            holder.book.setText("");
        }
        return rowView;
    }
}