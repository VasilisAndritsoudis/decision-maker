package com.android.decisionmaker.UI.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.AddChoicesAdapter;
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
    AddChoicesAdapter choicesAdapter;
    TextView warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_choices);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            subCategoryName = extras.getString("SubCategory");
        }

        choices = new ArrayList<>();

        choice = findViewById(R.id.addChoicesChoiceEdit);
        name = findViewById(R.id.addChoicesNameEdit);

        choice.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus)
                choice.setText("");
        });

        choice.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addChoice(v);
                return true;
            }
            return false;
        });

        warning = findViewById(R.id.addChoicesWarning);

        recyclerAddedChoices = findViewById(R.id.prepareRecyclerChoices);
        choicesAdapter = new AddChoicesAdapter(choices);
        recyclerAddedChoices.setAdapter(choicesAdapter);
        recyclerAddedChoices.setLayoutManager(new LinearLayoutManager(this));
    }

    public void goToPrepare(View view) {
        if(name.getText().toString().isEmpty()){
            warning.setVisibility(View.VISIBLE);
            warning.setText("You have to set a name before you continue!");
            return;
        } else if (choices.size()<=1) {
            warning.setVisibility(View.VISIBLE);
            warning.setText("You have to add at least two choices!");
            return;
        }
        Intent intent = new Intent(this, Prepare.class);
        Decision decision = new Decision();
        decision.setName(name.getText().toString());
        decision.setDate(new Date());
        decision.setSubCategory(subCategoryName);
        intent.putExtra("Decision", decision);
        intent.putExtra("Choices",choices);
        startActivity(intent);
    }
    
    public void addChoice (View view) {
        if(choice.getText().toString().isEmpty())
            return;
        for(Choice item : choices)
            if(item.getName().equals(choice.getText().toString())) {
                choice.setText("");
                return;
            }
        Choice temp = new Choice();
        temp.setName(choice.getText().toString().trim());
        choices.add(temp);
        choice.setText("");
        choicesAdapter.notifyDataSetChanged();
    }
}