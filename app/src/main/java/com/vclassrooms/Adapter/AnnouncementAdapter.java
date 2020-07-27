package com.vclassrooms.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Entity.AnnouncementDetailResponse;
import com.vclassrooms.Entity.AssignmentResponse;
import com.vclassrooms.R;

import java.util.List;

/**
 * Created by Rahul on 25,July,2020
 */
public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {
    Context context;
    public List<AnnouncementDetailResponse.AnnouncementDetail> announcementDetails;
    LayoutInflater inflter;
    private int row_index = -1;

    AppUtils appUtils;
    public AnnouncementAdapter(Context context, List<AnnouncementDetailResponse.AnnouncementDetail> announcementDetails) {
        this.context = context;
        this.announcementDetails = announcementDetails;
    }



    @NonNull
    @Override
    public AnnouncementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_adapter, parent, false);
        return new AnnouncementAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AnnouncementAdapter.ViewHolder viewHolde, final int position) {
        appUtils=new AppUtils();
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                appUtils.setText(viewHolde.txtTitle,announcementDetails.get(position).getAnnouncementName());
                appUtils.setText(viewHolde.txtDate,appUtils.convertDateFormat("yyyy-MM-dd'T'HH:mm:ss","dd/MM/yyyy",announcementDetails.get(position).getFromDate())+" - "+
                appUtils.convertDateFormat("yyyy-MM-dd'T'HH:mm:ss","dd/MM/yyyy",announcementDetails.get(position).getToDate()));
                appUtils.setText(viewHolde.txtDetail,announcementDetails.get(position).getAnnouncementDetails());


                viewHolde.cardPreview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                    }
                });



            }
        };
        mainHandler.post(myRunnable);
    }

    @Override
    public int getItemCount() {
        return announcementDetails.size();
        //return 200;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,txtDate,txtDetail;
        CardView cardPreview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=(TextView)itemView.findViewById(R.id.txtTitle);
            txtDate=(TextView)itemView.findViewById(R.id.txtDate);
            txtDetail = (TextView)itemView.findViewById(R.id.txtDetail);
            cardPreview = (CardView) itemView.findViewById(R.id.cardPreview);


        }
    }


}