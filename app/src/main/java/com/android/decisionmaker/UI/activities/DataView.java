package com.android.decisionmaker.UI.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adefruandta.spinningwheel.SpinningWheelView;
import com.android.decisionmaker.R;
import com.android.decisionmaker.database.models.Choice;
import com.android.decisionmaker.database.models.Criteria;
import com.android.decisionmaker.database.models.Decision;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataView extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Decision decision;

    public DataView() {
        // Required empty public constructor
    }

    public DataView(Decision decision) {
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
     * @return A new instance of fragment DataView.
     */
    // TODO: Rename and change types and number of parameters
    public static DataView newInstance(String param1, String param2) {
        DataView fragment = new DataView();
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
        View view = inflater.inflate(R.layout.fragment_data_view, container, false);

        return view;
    }
}