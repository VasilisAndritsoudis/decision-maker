package com.example.decisionmaker.database.models;

public class Criteria {
    private int id;
    private String name;

    public Criteria() {}

    public Criteria(String name) {
        this.name = name;
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
}
