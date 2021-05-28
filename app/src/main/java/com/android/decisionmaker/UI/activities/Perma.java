package com.android.decisionmaker.UI.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.PermaAdapter;
import com.android.decisionmaker.database.handlers.DBHandler;
import com.android.decisionmaker.database.models.Category;
import com.android.decisionmaker.database.models.SubCategory;

import java.util.ArrayList;
import java.util.Objects;

public class Perma extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Pair<Category,Boolean>> categories;

    TextView categoryNameTv;
    EditText categoryName;
    EditText subCategoryName;
    TextView warning;
    Bundle extras;
    boolean enable;
    PermaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perma);

        //adds home button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        DBHandler dbHandler = DBHandler.getDBHandler(this);
        ArrayList<Category> temp = dbHandler.getCategories();

        categoryName = findViewById(R.id.permaCategoryEditext);
        categoryNameTv = findViewById(R.id.permaCategoryLabel);
        subCategoryName = findViewById(R.id.permaSubCategoryEditText);

        warning = findViewById(R.id.permaWarning);

        extras = getIntent().getExtras();

        categories = new ArrayList<>();

        //checks for rotation and recreates all the given input from the user
        if (savedInstanceState != null) {
            enable = savedInstanceState.getBoolean("Enable");
            categoryName.setText(savedInstanceState.getString("CategoryName"));
            categoryName.setVisibility(savedInstanceState.getInt("CategoryVisibility"));
            categoryNameTv.setVisibility(savedInstanceState.getInt("CategoryVisibility"));
            subCategoryName.setText(savedInstanceState.getString("SubCategoryName"));
            if (savedInstanceState.containsKey("Warning")) {
                warning.setText(savedInstanceState.getString("Warning"));
                warning.setVisibility(savedInstanceState.getInt("Visibility"));
            }
            if (categoryName.getText().toString().isEmpty() || categoryName.getVisibility() == View.VISIBLE) {
                for (Category category : temp) {
                    categories.add(new Pair<>(category, false));
                }
                Category category = new Category();
                category.setName("New Category");
                if (categoryName.getVisibility() == View.VISIBLE) {
                    categories.add(new Pair<>(category, true));
                } else {
                    categories.add(new Pair<>(category, false));
                }
            } else {
                for (Category category : temp) {
                    if (category.getName().equals(categoryName.getText().toString()))
                        categories.add(new Pair<>(category, true));
                    else
                        categories.add(new Pair<>(category, false));
                }
                Category category = new Category();
                category.setName("New Category");
                categories.add(new Pair<>(category, false));
            }
        }

        //checks if the user wants to create a completely new category or expand an existing one
        if (extras != null && savedInstanceState == null) {
            String string = extras.getString("Perma");
            if (!string.equals("None")) {
                enable = false;
                categoryName.setText(string);
                for (Category category : temp) {
                    if (category.getName().equals(string)) {
                        categories.add(new Pair<>(category, true));
                    } else {
                        categories.add(new Pair<>(category, false));
                    }
                }
                Category category = new Category();
                category.setName("New Category");
                categories.add(new Pair<>(category, false));
            } else {
                enable = true;
                for (Category category : temp) {
                    categories.add(new Pair<>(category, false));
                }
                Category category = new Category();
                category.setName("New Category");
                categories.add(new Pair<>(category, false));
            }
        }

        recyclerView = findViewById(R.id.permaRecyclerView);
        adapter = new PermaAdapter(categories, enable, categoryName, categoryNameTv);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    /**
     * An OnClick method that passes the user to the next activity
     * @param view is the View Object we are currently on
     */
    @SuppressLint("SetTextI18n")
    public void goToAddChoices(View view) {
        Intent intent = new Intent(this, AddChoices.class);

        //checks for violation before continuing to the next activity
        if(categoryName.getText().toString().isEmpty()) {
            warning.setText("You have to select a category or create one!");
            warning.setVisibility(View.VISIBLE);
            return;
        }
        if (subCategoryName.getText().toString().isEmpty()) {
            warning.setText("You have to name your subcategory to continue!");
            warning.setVisibility(View.VISIBLE);
            return;
        }
        if(categoryName.getText().toString().equalsIgnoreCase("New Category")) {
            warning.setText("\"New Category\" as name of category is prohibited!");
            warning.setVisibility(View.VISIBLE);
            return;
        }
        DBHandler dbHandler = DBHandler.getDBHandler(this);
        for (Category category : dbHandler.getCategories())
            for (SubCategory subCategory : dbHandler.getSubCategoriesOfCategory(category.getName()))
                if(subCategoryName.getText().toString().equalsIgnoreCase(subCategory.getName())){
                    warning.setText("This subcategory already exists in " + category.getName() + " category!");
                    warning.setVisibility(View.VISIBLE);
                    return;
                }
        //everything is fine, starts the next activity
        intent.putExtra("SubCategory",subCategoryName.getText().toString());
        intent.putExtra("Category", categoryName.getText().toString());
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //saves everything needed in case we have to recreate the activity without the user
        //losing any of his inputs
        outState.putAll(extras);
        outState.putInt("CategoryVisibility", categoryName.getVisibility());
        outState.putString("CategoryName", categoryName.getText().toString());
        outState.putString("SubCategoryName", subCategoryName.toString());
        if(!warning.getText().toString().isEmpty()){
            outState.putString("Warning", warning.getText().toString());
            outState.putInt("Visibility", warning.getVisibility());
        }
        outState.putBoolean("Enable", enable);
        super.onSaveInstanceState(outState);
    }
}