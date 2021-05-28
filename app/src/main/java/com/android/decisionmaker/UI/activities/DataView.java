package com.android.decisionmaker.UI.activities;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.DataButtonsAdapter;
import com.android.decisionmaker.UI.adapters.DataTextsAdapter;
import com.android.decisionmaker.database.models.Criteria;
import com.android.decisionmaker.database.models.Decision;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DataView extends Fragment{


    private Decision decision;
    Bundle extras;
    DataTextsAdapter textsAdapter;

    public DataView() {
        // Required empty public constructor
    }

    public DataView(Decision decision) {
        this.decision = decision;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_view, container, false);

        ArrayList<String> namesAndValues;

        if (savedInstanceState != null) {
            decision = (Decision) savedInstanceState.get("Decision");
            namesAndValues = savedInstanceState.getStringArrayList("Names and Values");
        } else {
            extras = this.getArguments();
            if (extras != null) {
                decision = (Decision) extras.get("Decision");
            }
            namesAndValues = new ArrayList<>();
        }


        RecyclerView recycler = view.findViewById(R.id.dataTextRecycler);
        textsAdapter = new DataTextsAdapter(namesAndValues);
        recycler.setAdapter(textsAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ArrayList<Criteria> criteria = decision.getCriteria();

        RecyclerView recyclerView = view.findViewById(R.id.dataButtonRecycler);
        DataButtonsAdapter adapter = new DataButtonsAdapter(criteria, namesAndValues, textsAdapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("Decision", decision);
        outState.putStringArrayList("Names and Values", textsAdapter.getArrayList());
        super.onSaveInstanceState(outState);
    }
}