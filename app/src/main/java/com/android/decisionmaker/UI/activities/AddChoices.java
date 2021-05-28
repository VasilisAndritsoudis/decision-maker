package com.android.decisionmaker.UI.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.AddChoicesAdapter;
import com.android.decisionmaker.database.models.Choice;
import com.android.decisionmaker.database.models.Decision;

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
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_choices);

        choice = findViewById(R.id.addChoicesChoiceEdit);
        name = findViewById(R.id.addChoicesNameEdit);
        warning = findViewById(R.id.addChoicesWarning);

        //gets Subcategory if it exists
        extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("SubCategory") && extras.getString("SubCategory") != null) {
                subCategoryName = extras.getString("SubCategory");
            } else {
                subCategoryName = null;
            }
        }


        //checks for rotation in order not to lose any data given from the user
        if(savedInstanceState != null) {
            choices = (ArrayList<Choice>) savedInstanceState.get("AddedChoices");
            name.setText(savedInstanceState.getString("Decision Name"));
            choice.setText(savedInstanceState.getString("Choice"));
            if (savedInstanceState.containsKey("Warning")) {
                warning.setText(savedInstanceState.getString("Warning"));
                warning.setVisibility(savedInstanceState.getInt("Visibility"));
            }
        } else {
            choices = new ArrayList<>();
        }

        //Listener for Keystroke in the enter button
        choice.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addChoice(v);
                return true;
            }
            return false;
        });



        recyclerAddedChoices = findViewById(R.id.prepareRecyclerChoices);
        choicesAdapter = new AddChoicesAdapter(choices);
        recyclerAddedChoices.setAdapter(choicesAdapter);
        recyclerAddedChoices.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * OnClick method to go to the next activity
     * @param view is the View Object we are currently on
     */
    @SuppressLint("SetTextI18n")
    public void goToPrepare(View view) {
        //Checks if there is any problem with user's input before sending him in the next activity
        if(name.getText().toString().isEmpty()){
            warning.setVisibility(View.VISIBLE);
            warning.setText("You have to set a name before you continue!");
            return;
        } else if (choices.size()<=1) {
            warning.setVisibility(View.VISIBLE);
            warning.setText("You have to add at least two choices!");
            return;
        }
        //Creates next activity and saving everything that will be needed there. Then starts the activity
        Intent intent = new Intent(this, Prepare.class);
        Decision decision = new Decision();
        decision.setName(name.getText().toString());
        decision.setDate(new Date());
        decision.setSubCategory(subCategoryName);
        intent.putExtra("Decision", decision);
        intent.putExtra("Choices",choices);
        if(extras != null) {
            if(extras.containsKey("Category") && extras.getString("Category") != null){
                String string = extras.getString("Category");
                intent.putExtra("Category",string);
            }
        }
        startActivity(intent);
    }

    /**
     * OnClick method to add a Choice Object for your Decision
     * @param view is the View Object we are currently on
     */
    public void addChoice (View view) {
        //Checks if there is any problem with user's input
        if(choice.getText().toString().isEmpty())
            return;
        for(Choice item : choices)
            if(item.getName().equalsIgnoreCase(choice.getText().toString())) {
                choice.setText("");
                return;
            }
        //Notifies the adapter of the recycler view after user adds a choice
        Choice temp = new Choice();
        temp.setName(choice.getText().toString().trim());
        choices.add(temp);
        choice.setText("");
        choicesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //Saving everything that will be needed in case user rotates the phone in order to
        //keep his input
        if(extras != null) {
            outState.putAll(extras);
        }
        outState.putSerializable("AddedChoices", choices);
        outState.putString("Decision Name", name.getText().toString());
        outState.putString("Choice", choice.getText().toString());
        if(!warning.getText().toString().isEmpty()){
            outState.putString("Warning", warning.getText().toString());
            outState.putInt("Visibility", warning.getVisibility());
        }
        super.onSaveInstanceState(outState);
    }

}