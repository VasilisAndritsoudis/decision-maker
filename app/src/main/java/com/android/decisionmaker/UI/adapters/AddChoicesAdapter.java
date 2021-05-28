    package com.android.decisionmaker.UI.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.database.models.Choice;

import java.util.ArrayList;

    public class AddChoicesAdapter extends RecyclerView.Adapter<AddChoicesAdapter.ViewHolder> {

    private final ArrayList<Choice> arrayList;

    /**
     * Adapter's constructor
     * @param list is the Arraylist of Choices that have to been printed
     */
    public AddChoicesAdapter(ArrayList<Choice> list) {
        arrayList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prepare_added_layout, parent, false);

        return new AddChoicesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //sets the text for the text view that recycler view is using from its layout
        holder.textView.setText(arrayList.get(arrayList.size() - 1 - position).getName());
    }

    @Override
    public int getItemCount() {
        if(arrayList == null)
            return 0;
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        //selects the appropriate text view that wants to set the text
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.prepareAdded);
        }
    }
}
