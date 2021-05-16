package com.android.decisionmaker.database.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Decision implements Serializable {
    private int id;
    private String subCategory;
    private String name;
    private Date date;
    private ArrayList<Criteria> criteria;

    public Decision() {
        this.criteria = new ArrayList<>();
    }

    public Decision(String name, String subCategory) {
        this();
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

    public ArrayList<Criteria> getCriteria() {
        return criteria;
    }

    public void setCriteria(ArrayList<Criteria> criteria) {
        this.criteria = new ArrayList<>();
        this.criteria.addAll(criteria);
    }
}
