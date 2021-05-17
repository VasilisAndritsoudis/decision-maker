package com.android.decisionmaker.UI.adapters;

import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.database.models.Category;

import java.util.ArrayList;

public class PermaAdapter extends RecyclerView.Adapter<PermaAdapter.ViewHolder>{

    private static ArrayList<Pair<Category,Boolean>> arrayList;
    static boolean enabled;
    EditText category;
    TextView title;

    public PermaAdapter (ArrayList<Pair<Category,Boolean>> list, boolean enabled, EditText editText,
                         TextView textView) {
        arrayList = list;
        PermaAdapter.enabled = enabled;
        category = editText;
        title = textView;
    }

    @NonNull
    @Override
    public PermaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.perma_recycler_layout, parent, false);

        return new PermaAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull PermaAdapter.ViewHolder holder, int position) {
        holder.name.setText(arrayList.get(position).first.getName());
        if (arrayList.get(position).second) {
            holder.itemView.setBackgroundColor(Color.BLUE);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.permaCategoryName);

            if (enabled) {
                itemView.setOnClickListener(v -> {
                    if (name.getText().toString().equals("New Category")) {
                        category.setVisibility(View.VISIBLE);
                        category.setText("");
                        title.setVisibility(View.VISIBLE);
                    } else {
                        category.setText(name.getText().toString());
                        category.setVisibility(View.INVISIBLE);
                        title.setVisibility(View.INVISIBLE);
                    }
                    ArrayList<Pair<Category,Boolean>> temp = new ArrayList<>();
                    for(Pair<Category,Boolean> pair : arrayList) {
                        if(pair.first.getName().equals(name.getText().toString())) {
                            temp.add(new Pair<>(pair.first, true));
                        } else {
                            temp.add(new Pair<>(pair.first, false));
                        }
                    }
                    arrayList = temp;
                    notifyDataSetChanged();
                });
            }
        }
    }
}
