package com.android.decisionmaker.database.models;

public class Choice {
    private int id;
    private String name;
    private int value;

    public Choice() {}

    public Choice(String name) {
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

    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }
}
