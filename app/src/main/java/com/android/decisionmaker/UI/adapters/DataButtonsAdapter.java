package com.android.decisionmaker.UI.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.database.models.Choice;
import com.android.decisionmaker.database.models.Criteria;

import java.util.ArrayList;

public class DataButtonsAdapter extends RecyclerView.Adapter<DataButtonsAdapter.ViewHolder> {

    private final ArrayList<Criteria> arrayList;
    private final ArrayList<String> namesAndValues;
    private final DataTextsAdapter textsAdapter;

    /**
     * Adapter's Constructor
     * @param list is the Arraylist of Criteria that have to been printed
     * @param namesAndValues is the list of Strings that have the Choices and the values that belongs
     *                       to the selected criterion
     * @param textsAdapter is another adapter that has to been notified everytime @param namesAndValues
     *                     is being changed
     */
    public DataButtonsAdapter(ArrayList<Criteria> list, ArrayList<String> namesAndValues,
                              DataTextsAdapter textsAdapter) {
        arrayList = list;
        this.namesAndValues = namesAndValues;
        this.textsAdapter = textsAdapter;
    }

    @NonNull
    @Override
    public DataButtonsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_buttons_recycler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataButtonsAdapter.ViewHolder holder, int position) {
        holder.button.setText(arrayList.get(position).getName());

        //when a criterion is selected create the appropriate strings in pairs of names and their
        //correspondent values
        holder.button.setOnClickListener(v -> {
            Criteria criterion = arrayList.get(position);
            namesAndValues.clear();
            namesAndValues.add("Importance~" + criterion.getWeight());
            for (Choice choice : criterion.getChoices()) {
                namesAndValues.add(choice.getName() + "~" + choice.getValue());
            }

            textsAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        if(arrayList == null)
            return 0;
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            button = itemView.findViewById(R.id.dataViewbutton);

        }
    }
}
