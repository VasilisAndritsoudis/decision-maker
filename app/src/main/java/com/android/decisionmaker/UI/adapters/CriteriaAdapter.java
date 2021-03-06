package com.android.decisionmaker.UI.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.database.models.Decision;

import java.util.ArrayList;

public class CriteriaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final ArrayList<String> arrayList;
    private final CriteriaAdapterInterface listenerInterface;
    private final Decision decision;
    private final int count;

    /**
     * Adapter's Constructor
     * @param list is the Arraylist of Strings that have to been printed
     * @param decision is the decision we want to change values and weights of correspondent
     *                 choices and criteria through seekbars
     * @param count is an int to know in which we criterion we are
     * @param listenerInterface an interface to override a listener
     */
    public CriteriaAdapter(ArrayList<String> list, Decision decision, int count,
                           CriteriaAdapterInterface listenerInterface) {
        arrayList = list;
        this.listenerInterface = listenerInterface;
        this.decision = decision;
        this.count = count;
    }

    /**
     * A getter to get the decision at its current state
     * @return the Decision Object
     */
    public Decision getDecision() {
        return decision;
    }

    @NonNull
    @Override
    //uses three different view holders for three different layouts
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.criteria_recycler_title, parent, false);
            return new ViewHolder1(v);
        } else if (viewType == arrayList.size()-1) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.criteria_recycler_button, parent, false);
            return new ViewHolder2(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.criteria_recycler_layout, parent, false);
            return new ViewHolder0(v);
        }
    }

    @Override
    //Binds the items to the respective place
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position == 0) {
            ViewHolder1 viewHolder1 = (ViewHolder1) holder;
            viewHolder1.name.setText(arrayList.get(position));
            viewHolder1.seekBar.setProgress(decision.getCriteria().get(count).getWeight());
        } else if (position == arrayList.size()-1) {
            ViewHolder2 viewHolder2 = (ViewHolder2) holder;
            viewHolder2.name.setText(arrayList.get(position));
        } else {
            ViewHolder0 viewHolder0 = (ViewHolder0) holder;
            viewHolder0.name.setText(arrayList.get(position));
            viewHolder0.seekBar.setProgress(decision.getCriteria().get(count).getChoices().get(position-1).getValue());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder0 extends RecyclerView.ViewHolder {

        TextView name;
        SeekBar seekBar;

        public ViewHolder0(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.criteriaProductName);
            seekBar = itemView.findViewById(R.id.criteriaSeekBar);
            seekBar.setMax(99);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    listenerInterface.onSeekBarChange(getAbsoluteAdapterPosition(),
                            seekBar.getProgress(), arrayList.get(0));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView name;
        SeekBar seekBar;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.criteriaTitle);
            seekBar = itemView.findViewById(R.id.criteriaWeightSeekBar);
            seekBar.setMax(99);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    listenerInterface.onSeekBarChange(getAbsoluteAdapterPosition(),
                            seekBar.getProgress(), arrayList.get(0));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {

        Button name;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.criteriaNextButton);

        }
    }

}
