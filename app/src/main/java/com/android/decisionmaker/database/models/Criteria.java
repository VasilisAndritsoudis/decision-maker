package com.android.decisionmaker.database.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Criteria implements Serializable {
    private int id;
    private String name;
    private int weight;
    private ArrayList<Choice> choices;

    public Criteria() {
        this.choices = new ArrayList<>();
    }

    public Criteria(String name) {
        this();
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public ArrayList<Choice> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<Choice> choices) {
        this.choices = new ArrayList<>();
        this.choices.addAll(choices);
    }
}
