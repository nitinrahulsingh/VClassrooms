package com.vclassrooms.Adapter;

import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Entity.ClasTimeTableResponse;
import com.vclassrooms.Entity.ExamTimeTableResponse;
import com.vclassrooms.Entity.Timetable;
import com.vclassrooms.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Rahul on 12,July,2020
 */
public class ClassTimeTableAdapter extends RecyclerView.Adapter<ClassTimeTableAdapter.ViewHolder> {
    Context context;
    public List<Timetable> timetableList;
    LayoutInflater inflter;
    ClassTimeTableAdapter.ViewHolder viewHolder;
    private int row_index = -1;
    ClassTimeTableAdapter.AssignmentDataClick assignmentDataClick;
    AppUtils appUtils;
    public ClassTimeTableAdapter(Context context, List<Timetable> timetableList) {
        this.context = context;
        this.timetableList = timetableList;

    }

    public void setOnClickListener(ClassTimeTableAdapter.AssignmentDataClick assignmentDataClick) {
        this.assignmentDataClick = assignmentDataClick;
    }
    public interface AssignmentDataClick {
        void onAssignmentClick(String details, int position);
    }


    @NonNull
    @Override
    public ClassTimeTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_timetable_adapter, parent, false);
        return new ClassTimeTableAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClassTimeTableAdapter.ViewHolder viewHolde, final int position) {
        appUtils=new AppUtils();
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {

                    appUtils.setText(viewHolde.subject_tv,timetableList.get(position).getSubjectName());
                    appUtils.setText(viewHolde.hr_starttv,timetableList.get(position).getStartTime());
                    appUtils.setText(viewHolde.hr_endtv,timetableList.get(position).getEndTime());
                    appUtils.setText(viewHolde.name_txt,timetableList.get(position).getFirst_Name());




            }
        };
        mainHandler.post(myRunnable);
    }

    @Override
    public int getItemCount() {
        return timetableList.size();
        //return 200;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject_tv,hr_starttv,hr_endtv,name_txt;
        LinearLayout linear_main;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hr_starttv=(TextView)itemView.findViewById(R.id.hr_starttv);
            subject_tv = (TextView)itemView.findViewById(R.id.subject_tv);
            hr_endtv = (TextView) itemView.findViewById(R.id.hr_endtv);
            name_txt = (TextView) itemView.findViewById(R.id.name_txt);
            linear_main = (LinearLayout) itemView.findViewById(R.id.linear_main);


        }
    }


}
