package com.android.decisionmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.decisionmaker.database.models.Decision;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;


import java.util.ArrayList;
import java.util.List;


public class DecisionView extends AppCompatActivity {

    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_view);

        Bundle extras = getIntent().getExtras();
        String nameAndDate;
        name = findViewById(R.id.ViewTitle);

        if (extras != null) {
            nameAndDate = extras.getString("textView");
            name.setText(nameAndDate);
        }

        Decision decision = new Decision();  // -----------------------------------------------------------------


        AnyChartView anyChartView = findViewById(R.id.any_chart_view);

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Apples", 6371664));
        data.add(new ValueDataEntry("Pears", 789622));
        data.add(new ValueDataEntry("Bananas", 7216301));
        data.add(new ValueDataEntry("Grapes", 1486621));
        data.add(new ValueDataEntry("Oranges", 1200000));

        pie.data(data);

        pie.title("Fruits imported in 2015 (in kg)");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Retail channels")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
    }
}