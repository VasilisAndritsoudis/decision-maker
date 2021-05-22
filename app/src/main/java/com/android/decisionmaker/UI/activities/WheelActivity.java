package com.android.decisionmaker.UI.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.adefruandta.spinningwheel.SpinningWheelView;
import com.android.decisionmaker.R;
import com.android.decisionmaker.database.models.Choice;
import com.android.decisionmaker.database.models.Decision;

import java.util.ArrayList;

public class WheelActivity extends AppCompatActivity {

    Decision decision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);

        // TODO: take decision from Bundle
        decision = null;

        SpinningWheelView wheelView = (SpinningWheelView) findViewById(R.id.wheel);

        // Can be array string or list of object
        ArrayList<String> choices = new ArrayList<>();
        for (Choice choice : decision.getCriteria().get(0).getChoices())
            choices.add(choice.getName());
        wheelView.setItems(choices);

        // Set listener for rotation event
        wheelView.setOnRotationListener(new SpinningWheelView.OnRotationListener<String>() {
            // Call once when start rotation
            @Override
            public void onRotation() {
                Log.d("XXXX", "On Rotation");
            }

            // Call once when stop rotation
            @Override
            public void onStopRotation(String item) {
                Log.d("XXXX", "On Rotation");
            }
        });

        // If true: user can rotate by touch
        // If false: user can not rotate by touch
        wheelView.setEnabled(true);

        System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.println(wheelView.getWheelTextSize());
        wheelView.setWheelTextSize(100f);
        System.out.println(wheelView.getWheelTextSize());

//        this.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // max angle 50
//                // duration 10 second
//                // every 50 ms rander rotation
//                wheelView.rotate(200, 5000, 50);
//            }
//        });
    }
}