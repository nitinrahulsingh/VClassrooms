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
import com.vclassrooms.Entity.AssignmentResponse;
import com.vclassrooms.Entity.ExamTimeTableResponse;
import com.vclassrooms.R;

import java.util.List;

/**
 * Created by Rahul on 12,July,2020
 */
public class ExamTimetableAdapter extends RecyclerView.Adapter<ExamTimetableAdapter.ViewHolder> {
    Context context;
    public List<ExamTimeTableResponse.Timetable> timetableList;
    LayoutInflater inflter;
    ExamTimetableAdapter.ViewHolder viewHolder;
    private int row_index = -1;
    ExamTimetableAdapter.AssignmentDataClick assignmentDataClick;
    AppUtils appUtils;
    public ExamTimetableAdapter(Context context, List<ExamTimeTableResponse.Timetable> timetableList) {
        this.context = context;
        this.timetableList = timetableList;
    }

    public void setOnClickListener(ExamTimetableAdapter.AssignmentDataClick assignmentDataClick) {
        this.assignmentDataClick = assignmentDataClick;
    }
    public interface AssignmentDataClick {
        void onAssignmentClick(String details, int position);
    }


    @NonNull
    @Override
    public ExamTimetableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_timetable_adapter, parent, false);
        return new ExamTimetableAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExamTimetableAdapter.ViewHolder viewHolde, final int position) {
        appUtils=new AppUtils();
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                appUtils.setText(viewHolde.subject_tv,timetableList.get(position).getSubjectName());
                String day=timetableList.get(position).getDay_Name().substring(0,3);
                appUtils.setText(viewHolde.day_txt,timetableList.get(position).getExamDate()+" ("+day+")");
                appUtils.setText(viewHolde.hr_starttv,timetableList.get(position).getStartTime());
                appUtils.setText(viewHolde.hr_endtv,timetableList.get(position).getEndTime());
                appUtils.setText(viewHolde.examType_txt,timetableList.get(position).getSem_Name()+"\n"+timetableList.get(position).getExamType());


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
        TextView subject_tv,day_txt,hr_starttv,hr_endtv,examType_txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day_txt=(TextView)itemView.findViewById(R.id.day_txt);
            hr_starttv=(TextView)itemView.findViewById(R.id.hr_starttv);
            subject_tv = (TextView)itemView.findViewById(R.id.subject_tv);
            hr_endtv = (TextView) itemView.findViewById(R.id.hr_endtv);
            examType_txt = (TextView) itemView.findViewById(R.id.examType_txt);


        }
    }


}