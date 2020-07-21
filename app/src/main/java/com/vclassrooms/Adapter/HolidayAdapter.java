package com.vclassrooms.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Entity.HolidayDetailResponse;
import com.vclassrooms.R;

import java.util.List;

/**
 * Created by Rahul on 17,July,2020
 */
public class HolidayAdapter extends  RecyclerView.Adapter<HolidayAdapter.ViewHolder> {
    Context context;
    public List<HolidayDetailResponse.HolidayEvent> mHolidayList;
    LayoutInflater inflter;
    ViewHolder viewHolder;
    private int row_index = -1;
    AppUtils appUtils;

    public HolidayAdapter(Context context, List<HolidayDetailResponse.HolidayEvent> mHolidayList) {
        this.context = context;
        this.mHolidayList = mHolidayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventcalender_list_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolde, final int position) {
        appUtils = new AppUtils();
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                viewHolde.from_todate.setText(mHolidayList.get(position).getFromDate()+" - "+mHolidayList.get(position).getToDate());
                viewHolde.festivalname.setText(mHolidayList.get(position).getHolidayName());
                viewHolde.datetv.setText(appUtils.convertDateFormat("dd/MM/yyyy","dd MMMM yyyy",mHolidayList.get(position).getFromDate()));
                if(position % 7 == 0){
                    viewHolde.datetv.setTextColor(context.getResources().getColorStateList(R.color.colorPrimaryDark));
                }else if(position % 7 == 1){
                    viewHolde.datetv.setTextColor(context.getResources().getColorStateList(R.color.orange));
                }else if(position % 7 == 2){
                    viewHolde.datetv.setTextColor(context.getResources().getColorStateList(R.color.yellow));
                }else if(position % 7 == 3){
                    viewHolde.datetv.setTextColor(context.getResources().getColorStateList(R.color.green));
                }else if(position % 7 == 4){
                    viewHolde.datetv.setTextColor(context.getResources().getColorStateList(R.color.red));
                }else if(position % 7 == 5){
                    viewHolde.datetv.setTextColor(context.getResources().getColorStateList(R.color.colorPrimary));
                }else if(position % 7 == 6){
                    viewHolde.datetv.setTextColor(context.getResources().getColorStateList(R.color.purple));
                }
            }
        };
        mainHandler.post(myRunnable);
    }

    @Override
    public int getItemCount() {
        return mHolidayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView datetv,festivalname,from_todate;
        CardView cv_add;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            datetv = (TextView) itemView.findViewById(R.id.datetv);
            festivalname = (TextView) itemView.findViewById(R.id.festivalname);
            from_todate = (TextView) itemView.findViewById(R.id.from_todate);
            cv_add = (CardView) itemView.findViewById(R.id.cv_add);



        }
    }


}
