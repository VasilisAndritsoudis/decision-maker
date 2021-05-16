package com.android.decisionmaker.UI.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.PrepareAdapterAdded;
import com.android.decisionmaker.database.models.Choice;
import com.android.decisionmaker.database.models.Decision;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class AddChoices extends AppCompatActivity {

    ArrayList<Choice> choices;
    EditText choice;
    EditText name;
    RecyclerView recyclerAddedChoices;
    String subCategoryName;
    PrepareAdapterAdded choicesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_choices);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            subCategoryName = extras.getString("buttonPressed");
        }

        choices = new ArrayList<>();

        choice = findViewById(R.id.addChoicesChoiceEdit);
        name = findViewById(R.id.addChoicesNameEdit);

        choice.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus)
                choice.setText("");
        });

        recyclerAddedChoices = findViewById(R.id.prepareRecyclerChoices);
        choicesAdapter = new PrepareAdapterAdded(choices);
        recyclerAddedChoices.setAdapter(choicesAdapter);
        recyclerAddedChoices.setLayoutManager(new LinearLayoutManager(this));
    }

    public void goToPrepare(View view) {
        Intent intent = new Intent(this, Prepare.class);
        Decision decision = new Decision();
        decision.setName(name.getText().toString());
        decision.setDate(new Date());
        decision.setSubCategory(subCategoryName);
        intent.putExtra("Decision", (Serializable) decision);
        intent.putExtra("Choices",choices);
        startActivity(intent);
    }

    public void addChoice (View view) {
        if(choice.getText().toString().isEmpty())
            return;
        Choice temp = new Choice();
        temp.setName(choice.getText().toString().trim());
        choices.add(temp);
        choice.setText("");
        choicesAdapter.notifyDataSetChanged();
    }
}