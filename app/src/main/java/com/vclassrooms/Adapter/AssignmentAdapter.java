package com.vclassrooms.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Entity.AssignmentResponse;
import com.vclassrooms.Entity.StudentListResponse;
import com.vclassrooms.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rahul on 12,July,2020
 */
public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    Context context;
    public List<AssignmentResponse.Assignment> assignmentList;
    LayoutInflater inflter;
    private int row_index = -1;
   AssignmentDataClick assignmentDataClick;
   AppUtils appUtils;
    public AssignmentAdapter(Context context, List<AssignmentResponse.Assignment> assignments) {
        this.context = context;
        this.assignmentList = assignments;
    }

    public void setOnClickListener(AssignmentDataClick assignmentDataClick) {
        this.assignmentDataClick = assignmentDataClick;
    }
    public interface AssignmentDataClick {
        void onAssignmentClick(String details, int position);
    }


    @NonNull
    @Override
    public AssignmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_adapter, parent, false);
        return new AssignmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AssignmentAdapter.ViewHolder viewHolde, final int position) {
        appUtils=new AppUtils();
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                appUtils.setText(viewHolde.txtTitle,assignmentList.get(position).getAssignment_Name());
                appUtils.setText(viewHolde.txtDate,assignmentList.get(position).getDtDate());
                appUtils.setText(viewHolde.subject_tv,assignmentList.get(position).getSubjectName());


                viewHolde.cardPreview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (assignmentDataClick != null) {
                            assignmentDataClick.onAssignmentClick(assignmentList.get(position).getAssignment_Details(), position);
                            notifyDataSetChanged();
                        }
                    }
                });



            }
        };
        mainHandler.post(myRunnable);
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
        //return 200;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,txtDate,subject_tv;
        CardView cardPreview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=(TextView)itemView.findViewById(R.id.txtTitle);
            txtDate=(TextView)itemView.findViewById(R.id.txtDate);
            subject_tv = (TextView)itemView.findViewById(R.id.subject_tv);
            cardPreview = (CardView) itemView.findViewById(R.id.cardPreview);


        }
    }


}


