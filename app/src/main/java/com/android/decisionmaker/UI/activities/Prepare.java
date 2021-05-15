package com.android.decisionmaker.UI.activities;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.PrepareAdapterAdded;
import com.android.decisionmaker.UI.adapters.PrepareAdapterCheckBoxes;

import java.util.ArrayList;

public class Prepare extends AppCompatActivity {

    ArrayList<String> addedChoices;
    ArrayList<String> criteria;
    ArrayList<String> addedCriteria;

    EditText choice;
    EditText criterion;

    RecyclerView recyclerAddedChoices;
    RecyclerView recyclerAddedCriteria;
    RecyclerView checkBoxes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);


        addedChoices = new ArrayList<>();
        addedChoices.add("Samsung Galaxy S311");
        addedChoices.add("Nokia 3310");
        addedChoices.add("Turing Machine");
        addedChoices.add("Samsung Galaxy S311");
        addedChoices.add("Nokia 3310");
        addedChoices.add("Turing Machine");
        addedChoices.add("Samsung Galaxy S311");
        addedChoices.add("Nokia 3310");
        addedChoices.add("Turing Machine");

        criteria = new ArrayList<>();
        criteria.add("Price");
        criteria.add("Something else");
        criteria.add("I need DB");

        addedCriteria = new ArrayList<>();
        addedCriteria.add("First Criterion");
        addedCriteria.add("Second Criterion");
        addedCriteria.add("Third Criterion");


        recyclerAddedChoices = findViewById(R.id.prepareRecyclerChoices);
        PrepareAdapterAdded choicesAdapter = new PrepareAdapterAdded(addedChoices);
        recyclerAddedChoices.setAdapter(choicesAdapter);
        recyclerAddedChoices.setLayoutManager(new LinearLayoutManager(this));

        recyclerAddedCriteria = findViewById(R.id.prepareRecyclerCriteria);
        PrepareAdapterAdded criteriaAdapter = new PrepareAdapterAdded(addedCriteria);
        recyclerAddedCriteria.setAdapter(criteriaAdapter);
        recyclerAddedCriteria.setLayoutManager(new LinearLayoutManager(this));

        checkBoxes = findViewById(R.id.prepareRecyclerCheckBoxes);
        PrepareAdapterCheckBoxes checkBoxAdapter = new PrepareAdapterCheckBoxes(criteria);
        checkBoxes.setAdapter(checkBoxAdapter);
        checkBoxes.setLayoutManager(new LinearLayoutManager(this));


    }

}