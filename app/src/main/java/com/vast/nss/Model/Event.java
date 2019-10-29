package com.vast.nss.Model;

public class Event {

    private String title;
    private String location;
    private String date;
    private String category;
    private String hours;

    //Constructors
    public Event() {}


    //Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() { return date; }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
}
