package com.android.decisionmaker.database.models;

public class Criteria {
    private int id;
    private String name;
    private int weight;

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

    public int getWeight() { return weight; }

    public void setWeight(int weight) { this.weight = weight; }
}
