package com.example.androidapp1d;

import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/26/2017.
 */

public class StudBookingCustomFilter extends Filter {

    StudBookingDetailsAdapter adapter;
    ArrayList<StudBookingItem> filterList;

    public StudBookingCustomFilter(StudBookingDetailsAdapter adapter, ArrayList<StudBookingItem> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //check constraint validity
        if(constraint != null && constraint.length() > 0){
            //change to upper
            constraint = constraint.toString().toUpperCase();
            //Store filtered bookings
            ArrayList<StudBookingItem> filteredBookings = new ArrayList<>();

            for(int i = 0; i < filterList.size(); i++){
                //check
                if(filterList.get(i).getTitle().toUpperCase().contains(constraint)){
                    filteredBookings.add(filterList.get(i));
                }
            }
            results.count = filteredBookings.size();
            results.values = filteredBookings;
        } else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.bookings = (ArrayList<StudBookingItem>) results.values;
        //refresh
        adapter.notifyDataSetChanged();
    }
}