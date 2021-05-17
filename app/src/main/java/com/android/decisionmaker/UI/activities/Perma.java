package com.android.decisionmaker.UI.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.PermaAdapter;

import java.util.ArrayList;

public class Perma extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perma);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String string = extras.getString("Perma");
            if (!string.equals("None")) {

            }
        }

        recyclerView = findViewById(R.id.permaRecyclerView);
        PermaAdapter adapter = new PermaAdapter(categories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void goToPrepare(View view) {
        Intent intent = new Intent(this, Prepare.class);
        startActivity(intent);
    }
}