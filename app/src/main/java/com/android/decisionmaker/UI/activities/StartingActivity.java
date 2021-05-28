package com.android.decisionmaker.UI.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.decisionmaker.R;
import com.android.decisionmaker.database.handlers.DBHandler;

public class StartingActivity extends AppCompatActivity {

    ImageButton imageButton;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageButton = findViewById(R.id.startInfoButton);


        setContentView(R.layout.activity_starting_activity);

        dbHandler = DBHandler.getDBHandler(this);
    }

    //Inflate the menu to the layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        String viewType = dbHandler.getViewType();

        switch (viewType) {
            //Chart Type (group): check the chart from db

            case "Histogram":
                menu.findItem(R.id.histogram_choice).setChecked(true);
                break;
            case "Histogram3D":
                menu.findItem(R.id.histogram3d_choice).setChecked(true);
                break;
            default:
                menu.findItem(R.id.pie_chart_choice).setChecked(true);
                break;

        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            //Chart Type (group): we need to update the type of the chart

            case R.id.pie_chart_choice:
                item.setChecked(!item.isChecked());
                dbHandler.updateViewType("Pie");
                return true;
            case R.id.histogram_choice:
                item.setChecked(!item.isChecked());
                dbHandler.updateViewType("Histogram");
                return true;
            case R.id.histogram3d_choice:
                item.setChecked(!item.isChecked());
                dbHandler.updateViewType("Histogram3D");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * An OnClick method to go to the correspondent activity
     * @param view is the View Object we are currently on
     */
    public void goToHistory(View view) {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    /**
     * An OnClick method to go to the correspondent activity
     * @param view is the View Object we are currently on
     */
    public void goToDecisionMenu(View view) {
        Intent intent = new Intent(this, DecisionMenu.class);
        startActivity(intent);

    }

    /**
     * An OnClick method to go to the correspondent activity
     * @param view is the View Object we are currently on
     */
    public void goToAddChoices(View view) {
        Intent intent = new Intent(this, AddChoices.class);
        startActivity(intent);
    }

    /**
     * An OnClick method to go to the correspondent activity
     * @param view is the View Object we are currently on
     */
    public void goToInformation(View view) {
        Intent intent = new Intent(this, Information.class);
        startActivity(intent);
    }

}