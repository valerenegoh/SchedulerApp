package com.example.androidapp1d.Stud.Booking;

/**
 * Created by ASUS on 12/5/2017.
 */

public class StudBookingItem {
    private String id, title, prof, venue, date, description, student;

    public StudBookingItem() {
    }

    public StudBookingItem(String id, String student, String title, String prof, String venue, String date, String description) {
        this.id = id;
        this.title = title;
        this.prof = prof;
        this.venue = venue;
        this.date = date;
        this.description = description;
        this.student = student;
    }

    public String getTitle() {
        return title;
    }

    public String getProf() {
        return prof;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getStudent() {
        return student;
    }
}
