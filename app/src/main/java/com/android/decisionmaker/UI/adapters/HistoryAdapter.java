package com.android.decisionmaker.UI.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.activities.DecisionView;
import com.android.decisionmaker.database.models.Decision;

import java.util.ArrayList;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    public static class Pair {
        Decision decision;
        String date;

        public Pair(Decision decision, String date) {
            this.decision = decision;
            this.date = date;
        }

        public Decision getDecision() {
            return decision;
        }
    }

    private static ArrayList<Pair> pairArrayList;
    private final HistoryAdapterInterface listenerInterface;

    public HistoryAdapter (ArrayList<Pair> list, HistoryAdapterInterface listenerInterface) {
        pairArrayList = list;
        this.listenerInterface = listenerInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_recycler_layout, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = pairArrayList.get(position).decision.getName() + "\n" + pairArrayList.get(position).date;
        holder.nameAndDate.setText(text);
    }

    @Override
    public int getItemCount() {
        return pairArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameAndDate;
        ImageButton delete;
        ImageButton edit;

        public ViewHolder(View itemView) {
            super(itemView);

            nameAndDate = itemView.findViewById(R.id.historyDecisionText);
            delete = itemView.findViewById(R.id.historyDeleteButton);
            edit = itemView.findViewById(R.id.historyEditButton);

            itemView.setOnClickListener(v -> {
                Intent i = new Intent(itemView.getContext(), DecisionView.class);

                String name = nameAndDate.getText().toString().split("\n")[0];
                String date = nameAndDate.getText().toString().split("\n")[1];

                Decision decision = null;

                for (Pair pair : HistoryAdapter.pairArrayList) {
                    if (pair.date.equals(date) && pair.decision.getName().equals(name)) {
                        decision = pair.decision;
                        break;
                    }
                }

                if (decision != null) {
                    i.putExtra("Decision" , decision);
                    itemView.getContext().startActivity(i);
                }
            });

            edit.setOnClickListener(v -> listenerInterface.onClickEdit(getAbsoluteAdapterPosition()));

            delete.setOnClickListener(v -> listenerInterface.onClickDelete(getAbsoluteAdapterPosition()));
        }
    }
}
