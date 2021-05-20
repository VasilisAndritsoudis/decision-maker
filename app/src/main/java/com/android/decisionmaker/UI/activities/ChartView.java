package com.android.decisionmaker.UI.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.decisionmaker.R;
import com.android.decisionmaker.algorithms.WAS;
import com.android.decisionmaker.database.handlers.DBHandler;
import com.android.decisionmaker.database.models.Choice;
import com.android.decisionmaker.database.models.Criteria;
import com.android.decisionmaker.database.models.Decision;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Cartesian3d;
import com.anychart.charts.Pie;
import com.anychart.core.SeparateChart;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.cartesian.series.Column3d;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChartView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Decision decision;

    public ChartView() {
        // Required empty public constructor
    }

    public ChartView(Decision decision) {
        this.decision = decision;

//        Log.d("Dec Name", decision.getName());
//        Log.d("Dec Date", decision.getDate().toString());
//        Log.d("Dec SubCat", decision.getSubCategory());
//
//        for (Criteria criteria : decision.getCriteria()) {
//            Log.d("Dec Crit Name", criteria.getName());
//            Log.d("Dec Crit Weight", String.valueOf(criteria.getWeight()));
//
//            for (Choice choice : criteria.getChoices()) {
//                Log.d("Dec Crit Choice Name", choice.getName());
//                Log.d("Dec Crit Choice Val", String.valueOf(choice.getValue()));
//            }
//        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChartView.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartView newInstance(String param1, String param2) {
        ChartView fragment = new ChartView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart_view, container, false);

        Bundle extras = this.getArguments();
        if (extras != null) {
            decision = (Decision) extras.get("Decision");
        }

        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);

        String viewType = DBHandler.getDBHandler(this.getContext()).getViewType();
        switch (viewType) {
            case "Pie":
                anyChartView.setChart(prepareChart(decision));
                break;
            case "Histogram":
                anyChartView.setChart(prepareHistogram(decision));
                break;
            case "Histogram3D":
                anyChartView.setChart(prepareHistogram3D(decision));
        }

        return view;
    }

    private SeparateChart prepareChart(Decision decision) {
        ArrayList<Integer> percentages = WAS.logic(createScoresMatrix(decision), createWeightsMatrix(decision));

        Criteria temp = decision.getCriteria().get(0);
        List<DataEntry> data = new ArrayList<>();

        int count = 0;
        for (Choice choice : temp.getChoices()) {
            data.add(new ValueDataEntry(choice.getName(), percentages.get(count)));
            count++;
        }

        Pie pie = AnyChart.pie();

        pie.data(data);

        pie.title(decision.getName());

        pie.labels().position("inside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Choices")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        return pie;
    }

    private SeparateChart prepareHistogram(Decision decision) {
        ArrayList<Integer> percentages = WAS.logic(createScoresMatrix(decision), createWeightsMatrix(decision));

        Criteria temp = decision.getCriteria().get(0);
        List<DataEntry> data = new ArrayList<>();

        int count = 0;
        for (Choice choice : temp.getChoices()) {
            data.add(new ValueDataEntry(choice.getName(), percentages.get(count)));
            count++;
        }

        //Cartesian3d pie = AnyChart.column3d();
        Cartesian cartesian = AnyChart.column();

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }%");

        cartesian.animation(true);
        cartesian.title(decision.getName());

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }%");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Choice");

        return cartesian;
    }

    private SeparateChart prepareHistogram3D(Decision decision) {
        ArrayList<Integer> percentages = WAS.logic(createScoresMatrix(decision), createWeightsMatrix(decision));

        Criteria temp = decision.getCriteria().get(0);
        List<DataEntry> data = new ArrayList<>();

        int count = 0;
        for (Choice choice : temp.getChoices()) {
            data.add(new ValueDataEntry(choice.getName(), percentages.get(count)));
            count++;
        }

        //Cartesian3d pie = AnyChart.column3d();
        Cartesian3d cartesian = AnyChart.column3d();

        Column3d column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }%");

        cartesian.animation(true);
        cartesian.title(decision.getName());

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }%");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Choice");

        return cartesian;
    }

    /**
     * Creates a 2D matrix with the scores of each choice in every criterion
     * Each row is a choice and each column is a criterion
     * @param decision need to decomposed
     * @return the matrix with the scores
     */
    private ArrayList<ArrayList<Integer>> createScoresMatrix(Decision decision) {
        ArrayList<ArrayList<Integer>> scores = new ArrayList<>();

        for (int i = 0; i < decision.getCriteria().get(0).getChoices().size(); i++)
            scores.add(new ArrayList<>());


        for (Criteria criteria : decision.getCriteria()) {
            int pos = 0;
            for (Choice choice : criteria.getChoices()) {
                scores.get(pos).add(choice.getValue());
                pos++;
            }
        }

        return  scores;
    }

    /**
     * Creates a matrix with the weights of the criteria
     * @param decision with the need weights
     * @return the matrix the the weights
     */
    private ArrayList<Integer> createWeightsMatrix(Decision decision) {
        ArrayList<Integer> weights = new ArrayList<>();

        for (Criteria criterion : decision.getCriteria())
            weights.add(criterion.getWeight());

        return weights;
    }
}