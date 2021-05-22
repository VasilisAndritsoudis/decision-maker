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
    Bundle extras;
    DataTextsAdapter textsAdapter;

    public DataView() {
        // Required empty public constructor
    }

    public DataView(Decision decision) {
        this.decision = decision;
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