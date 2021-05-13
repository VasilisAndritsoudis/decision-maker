package com.example.decisionmaker.database.models;

import java.sql.Date;

public class Decision {
    private int id;
    private String subCategory;
    private String name;
    private Date date;

    public Decision() {}

    public Decision(String name, String subCategory) {
        this.name = name;
        this.subCategory = subCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
