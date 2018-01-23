package com.example.androidapp1d.Prof.Profile;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ASUS on 12/13/2017.
 */

public class PrefItem {

    private int timeslotsize;
    private String venue;
    private HashMap<String, ArrayList<String>> availability;

    public PrefItem() {
    }

    public PrefItem(int timeslot, String venue, HashMap<String, ArrayList<String>> selectedavaillist) {
        this.timeslotsize = timeslot;
        this.venue = venue;
        this.availability = selectedavaillist;
    }

    public int getTimeslot() {
        return timeslotsize;
    }

    public void setTimeslot(int timeslot) {
        this.timeslotsize = timeslot;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public HashMap<String, ArrayList<String>> getSelectedavaillist() {
        return availability;
    }

    public void setSelectedavaillist(HashMap<String, ArrayList<String>> selectedavaillist) {
        this.availability = selectedavaillist;
    }
}
