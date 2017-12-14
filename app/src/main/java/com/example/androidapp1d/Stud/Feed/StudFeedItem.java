package com.example.androidapp1d.Stud.Feed;

/**
 * Created by ASUS on 12/5/2017.
 */

public class StudFeedItem {
    private String id, title, prof, description, mod, timing, creator, student;

    public StudFeedItem() {
    }

    public StudFeedItem(String id, String student, String title, String prof, String description, String mod, String timing, String creator) {
        this.id = id;
        this.title = title;
        this.prof = prof;
        this.description = description;
        this.mod = mod;
        this.timing = timing;
        this.creator = creator;
        this.student = student;
    }

    public String getTitle() {
        return title;
    }

    public String getProf() {
        return prof;
    }

    public String getDescription() {
        return description;
    }

    public String getMod() {
        return mod;
    }

    public String getTiming() {
        return timing;
    }

    public String getCreator() {
        return creator;
    }

    public String getId() {
        return id;
    }

    public String getStudent() {
        return student;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
