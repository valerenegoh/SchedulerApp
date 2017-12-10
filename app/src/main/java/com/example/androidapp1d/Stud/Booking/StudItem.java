package com.example.androidapp1d.Stud.Booking;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/10/2017.
 */

public class StudItem {

    private String year, description, email, studentID;
    private Integer capacityPreference;
    private ArrayList<String> favProfs = new ArrayList<>();
    private ArrayList<String> mods = new ArrayList<>();

    public StudItem(String year, String description, String email,
                    String studentID, Integer capacityPreference,
                    ArrayList<String> favProfs, ArrayList<String> mods) {
        this.year = year;
        this.description = description;
        this.email = email;
        this.studentID = studentID;
        this.capacityPreference = capacityPreference;
        this.favProfs = favProfs;
        this.mods = mods;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public Integer getCapacityPreference() {
        return capacityPreference;
    }

    public void setCapacityPreference(Integer capacityPreference) {
        this.capacityPreference = capacityPreference;
    }

    public ArrayList<String> getFavProfs() {
        return favProfs;
    }

    public void setFavProfs(ArrayList<String> favProfs) {
        this.favProfs = favProfs;
    }

    public ArrayList<String> getMods() {
        return mods;
    }

    public void setMods(ArrayList<String> mods) {
        this.mods = mods;
    }
}
