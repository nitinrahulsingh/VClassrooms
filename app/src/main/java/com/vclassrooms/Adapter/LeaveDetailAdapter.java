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
import com.vclassrooms.Entity.AssignmentResponse;
import com.vclassrooms.Entity.LeaveDeatailsResponse;
import com.vclassrooms.R;

import java.util.List;

/**
 * Created by Rahul on 18,July,2020
 */
public class LeaveDetailAdapter extends RecyclerView.Adapter<LeaveDetailAdapter.ViewHolder> {
    Context context;
    public List<LeaveDeatailsResponse.LeaveDetail> leaveDetailList;
    LayoutInflater inflter;
    private int row_index = -1;
    AppUtils appUtils;
    public LeaveDetailAdapter(Context context, List<LeaveDeatailsResponse.LeaveDetail> leaveDetailList) {
        this.context = context;
        this.leaveDetailList = leaveDetailList;
    }

    @NonNull
    @Override
    public LeaveDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leavedetail_adapter, parent, false);
        return new LeaveDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LeaveDetailAdapter.ViewHolder viewHolde, final int position) {
        appUtils=new AppUtils();
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                appUtils.setText(viewHolde.leave_type_tv,leaveDetailList.get(position).getLeaveTypeName());
                appUtils.setText(viewHolde.leave_tiitle_tv,leaveDetailList.get(position).getLeaveName());
                appUtils.setText(viewHolde.description_tv,leaveDetailList.get(position).getLeaveDetails());


                viewHolde.main_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        row_index = viewHolde.getAdapterPosition();
                        if (leaveDetailList.get(position).isExpand()==false) {
                            leaveDetailList.get(position).setExpand(true);
                            notifyItemChanged(position);
                        } else {
                            leaveDetailList.get(position).setExpand(false);
                            notifyItemChanged(position);

                        }
                    }
                });

                if (leaveDetailList.get(position).isExpand()) {
                    viewHolde.linearChild.setVisibility(View.VISIBLE);

                } else {
                    viewHolde.linearChild.setVisibility(View.GONE);
                }

            }
        };
        mainHandler.post(myRunnable);
    }

    @Override
    public int getItemCount() {
        return leaveDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView leave_type_tv,leave_tiitle_tv,description_tv;
        CardView main_cardView;
        LinearLayout linearChild;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leave_type_tv=(TextView)itemView.findViewById(R.id.leave_type_tv);
            leave_tiitle_tv=(TextView)itemView.findViewById(R.id.leave_tiitle_tv);
            description_tv = (TextView)itemView.findViewById(R.id.description_tv);
            main_cardView = (CardView) itemView.findViewById(R.id.main_cardView);
            linearChild = (LinearLayout) itemView.findViewById(R.id.linearChild);


        }
    }


}
