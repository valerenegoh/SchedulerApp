package com.example.androidapp1d.Prof.Profile;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/10/2017.
 */

public class ProfItem {

    private String year, description, email, office, password;
    private Integer timeslot;
    private ArrayList<String> mods = new ArrayList<>();

    public ProfItem() {
    }

    public ProfItem(String password, String year, String description, String email,
                    String office, ArrayList<String> mods) {
        this.password = password;
        this.year = year;
        this.description = description;
        this.email = email;
        this.office = office;
        this.timeslot = timeslot;
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

    public ArrayList<String> getMods() {
        return mods;
    }

    public void setMods(ArrayList<String> mods) {
        this.mods = mods;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public Integer getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Integer timeslot) {
        this.timeslot = timeslot;
    }
}
