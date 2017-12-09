package com.example.androidapp1d.Stud.Booking;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/9/2017.
 */

public class StudBookingCreateItem {

    private String title;
    private String prof;
    private String venue;
    private String date;
    private ArrayList<String> students = new ArrayList<>();
    private String description;
    private String mod;
    private Integer capacity;
    private long timestamp;
    private String timing;

    public StudBookingCreateItem(String title, String description, String timing, String date,
                           String mod, String prof, Integer capacity, String creator, String venue) {
        this.title = title;
        this.description = description;
        this.timing = timing;
        this.venue = venue;

        this.date = date;
        this.mod = mod;
        this.prof = prof;
        this.capacity = capacity;
        this.students.add(creator);
        String startTime = timing.substring(0, timing.indexOf("-"));
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("h:ma, EEEE, d MMMM Y");
        LocalDateTime localDateTime = LocalDateTime.parse(startTime + ", " + date, dateFormat);
        DateTime utc = localDateTime.toDateTime(DateTimeZone.UTC);
        this.timestamp = utc.getMillis() / 1000;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }
}
