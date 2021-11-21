package com.epam.learn.model;


public class UserModel {

    private int id;
    private String name;
    private String email;

    public UserModel() {
        id = -1;
        name = "";
        email = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
