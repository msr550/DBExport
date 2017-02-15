package com.rbt.sandeep.gdrivedemo.pojo;

/**
 * Created by Sandeep on 2/15/2017.
 */

public class User {
    private int id;
    private String name;
    private String company;

    public User(String company, String name) {
        this.company = company;
        this.name = name;
    }

    public User() {

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
