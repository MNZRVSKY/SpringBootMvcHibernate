package com.epam.learn.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EventModel {

    private int id;
    private String title;
    private Date date;
    private float price;

    public EventModel() {
        id= -1;
        title = "";
        date = new Date();
        price = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        this.date = dateFormat.parse(date);
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
