package com.vclassrooms.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Database.NotificationDetails;
import com.vclassrooms.Entity.AnnouncementDetailResponse;
import com.vclassrooms.R;

import java.util.List;

/**
 * Created by Rahul on 17,August,2020
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    public List<NotificationDetails> notificationDetails;
    LayoutInflater inflter;
    private int row_index = -1;

    AppUtils appUtils;
    public NotificationAdapter(Context context, List<NotificationDetails> notificationDetails) {
        this.context = context;
        this.notificationDetails = notificationDetails;
    }



    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_adapter, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationAdapter.ViewHolder viewHolde, final int position) {
        appUtils=new AppUtils();
        Constatnts constatnts=new Constatnts();
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                appUtils.setText(viewHolde.tvdetail,notificationDetails.get(position).getDetails());
                if(notificationDetails.get(position).getIsread().contentEquals(constatnts.Read)){
                    viewHolde.parentCardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                }else {
                    viewHolde.parentCardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                }

                if(notificationDetails.get(position).getTittle().contentEquals(constatnts.Title_Assignment)){
                    viewHolde.img.setBackground(context.getResources().getDrawable(R.drawable.assignment));
                }
                if(notificationDetails.get(position).getTittle().contentEquals(constatnts.Title_Attendance)){
                    viewHolde.img.setBackground(context.getResources().getDrawable(R.drawable.attendance));
                }
                if(notificationDetails.get(position).getTittle().contentEquals(constatnts.Title_EBook)){
                    viewHolde.img.setBackground(context.getResources().getDrawable(R.drawable.ebook));
                }
                if(notificationDetails.get(position).getTittle().contentEquals(constatnts.Title_Gallery)){
                    viewHolde.img.setBackground(context.getResources().getDrawable(R.drawable.gallery));
                }
                if(notificationDetails.get(position).getTittle().contentEquals(constatnts.Title_Gallery)){
                    viewHolde.img.setBackground(context.getResources().getDrawable(R.drawable.gallery));
                }
                if(notificationDetails.get(position).getTittle().contentEquals(constatnts.Title_OnlineLecture)){
                    viewHolde.img.setBackground(context.getResources().getDrawable(R.drawable.online_lec));
                }
                if(notificationDetails.get(position).getTittle().contentEquals(constatnts.Title_OnlineExamination)){
                    viewHolde.img.setBackground(context.getResources().getDrawable(R.drawable.online_exam));
                }
                if(notificationDetails.get(position).getTittle().contentEquals(constatnts.Title_Leave)){
                    viewHolde.img.setBackground(context.getResources().getDrawable(R.drawable.leave_icon));
                }

            }
        };
        mainHandler.post(myRunnable);
    }

    @Override
    public int getItemCount() {
        return notificationDetails.size();
        //return 200;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvdetail;
        CardView parentCardView;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvdetail=(TextView)itemView.findViewById(R.id.tvdetail);
            img=(ImageView) itemView.findViewById(R.id.img);
            parentCardView = (CardView) itemView.findViewById(R.id.parentCardView);


        }
    }


}
