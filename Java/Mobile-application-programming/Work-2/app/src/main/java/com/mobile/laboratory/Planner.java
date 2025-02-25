package com.mobile.laboratory;

public class Planner {
    public String title;
    public String date;
    public String time;
    public String priority;

    public Planner() {
    }

    public Planner(String title, String date, String time, String priority) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }
}