package com.vclassrooms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Entity.StandardDivisionResponse;
import com.vclassrooms.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 18,May,2020
 */
public class StdListBottomAdapter extends RecyclerView.Adapter<StdListBottomAdapter.ItemListHolder> implements Filterable {
    Context context;
    List<StandardDivisionResponse.Division> StandardDetailListDetails = new ArrayList<>();
    List<StandardDivisionResponse.Division> StandardDetailList = new ArrayList<>();
    StandardDataClick standardDataClick;
    AppUtils appUtils;
    public StdListBottomAdapter(Context context, List<StandardDivisionResponse.Division> StandardDetailListDetails) {
        this.context = context;
        this.StandardDetailListDetails = StandardDetailListDetails;
        this.StandardDetailList = StandardDetailListDetails;
    }

    public void setOnClickListener(StandardDataClick studentDataClick) {
        this.standardDataClick = studentDataClick;
    }
    public interface StandardDataClick {
        void onStandardDataClick(int id, int position);
    }

    @Override
    public ItemListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.std_bottom_adapter, parent, false);
        return new ItemListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemListHolder holder, final int position) {
        try {
            appUtils=new AppUtils();
            appUtils.setText(holder.detail_tv,StandardDetailListDetails.get(position).getStandardName()+" ("+StandardDetailListDetails.get(position).getDivisionName()+")");
            holder.main_cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (standardDataClick != null) {
                        standardDataClick.onStandardDataClick(StandardDetailListDetails.get(position).getStandardId(), position);
                        notifyDataSetChanged();
                    }
                }
            });

        } catch (Exception e) {
            e.getMessage();
        }

    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<StandardDivisionResponse.Division> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = StandardDetailList;
                } else {
                    for (StandardDivisionResponse.Division list : StandardDetailList) {
                        if (list.getStandardName().toLowerCase().contains(query.toLowerCase())) {
                            filtered.add(list);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                StandardDetailListDetails = (List<StandardDivisionResponse.Division>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public int getItemCount() {
        return StandardDetailListDetails.size();
    }


    class ItemListHolder extends RecyclerView.ViewHolder {
        CardView main_cardview;
        TextView detail_tv;
        public ItemListHolder(@NonNull View itemView) {
            super(itemView);
            main_cardview = itemView.findViewById(R.id.main_cardview);
            detail_tv = itemView.findViewById(R.id.detail_tv);

        }


    }
}

