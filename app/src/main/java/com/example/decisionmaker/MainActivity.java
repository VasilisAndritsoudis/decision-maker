package com.example.decisionmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToPeopleList(View view){
        Intent intent = new Intent(this, PeopleList.class);
        startActivity(intent);
    }

    public void goToShoppingList(View view){
        Intent intent = new Intent(this, ShoppingList.class);
        startActivity(intent);
    }

    public void goToActivitiesList(View view){
        Intent intent = new Intent(this, ActivitiesList.class);
        startActivity(intent);
    }

    public void goToTravellingList(View view){
        Intent intent = new Intent(this, TravellingList.class);
        startActivity(intent);
    }
}