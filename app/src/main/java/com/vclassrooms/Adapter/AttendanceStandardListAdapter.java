package com.vclassrooms.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.StandardDivisionResponse;
import com.vclassrooms.Fragment.AssignmentFragment;
import com.vclassrooms.Fragment.ClassExamTimeTableFragment;
import com.vclassrooms.Fragment.ClassTimeTableFragment;
import com.vclassrooms.Fragment.MarkAttendanceFragment;
import com.vclassrooms.Fragment.StudentListStandardWiseFragment;
import com.vclassrooms.R;

import java.util.List;

/**
 * Created by Rahul on 08,July,2020
 */
public class AttendanceStandardListAdapter extends RecyclerView.Adapter<AttendanceStandardListAdapter.ViewHolder> {
    Activity activity;
    public List<StandardDivisionResponse.Division> mStandardDataList;
    LayoutInflater inflter;
    AttendanceStandardListAdapter.ViewHolder viewHolder;
    private int row_index = -1;
    AppUtils appUtils;
    String strModuleName;
    Constatnts constatnts;
    public AttendanceStandardListAdapter(Activity activity, List<StandardDivisionResponse.Division> standardDetails,String ModuleName) {
        this.activity = activity;
        this.mStandardDataList = standardDetails;
        this.strModuleName = ModuleName;
    }
    @NonNull
    @Override
    public AttendanceStandardListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_attendance_standardlist, parent, false);
        return new AttendanceStandardListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttendanceStandardListAdapter.ViewHolder viewHolde, final int position) {
        try {
            appUtils=new AppUtils();
            constatnts=new Constatnts();
            Spannable wordToSpan = new SpannableString("*" + mStandardDataList.get(position).getStandardName()+" ("+
                    mStandardDataList.get(position).getDivisionName()+")");
            wordToSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolde.className.setText(wordToSpan);
            viewHolde.imgViewClassAttendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onViewClick(position);

                }
            });
            viewHolde.imgMarkClassAttendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MarkAttendanceFragment markAttendanceFragment = new MarkAttendanceFragment();
                   Bundle bundle=new Bundle();
                    bundle.putString("ClassName", mStandardDataList.get(position).getStandardName()+" ("+
                            mStandardDataList.get(position).getDivisionName()+")");
                    bundle.putString("DivisionId", String.valueOf(mStandardDataList.get(position).getDivisionId()));
                    bundle.putString("StandardID", String.valueOf(mStandardDataList.get(position).getStandardId()));
                    markAttendanceFragment.setArguments(bundle);
                    appUtils.onAddFragment(activity, markAttendanceFragment,R.id.fragment_mainLayout);
                }
            });
            setVisibiltyCondition(viewHolde);
        } catch (Exception e) {

        }

    }

    private void setVisibiltyCondition(ViewHolder viewHolde) {
        switch (strModuleName) {
            case "AttendanceModule":
                viewHolde.imgMarkClassAttendance.setVisibility(View.VISIBLE);
                break;
            case "ClassTimetable":
                viewHolde.imgMarkClassAttendance.setVisibility(View.GONE);
                break;
            case "ExamTimetable":
                viewHolde.imgMarkClassAttendance.setVisibility(View.GONE);
                break;
            case "Assignment":
                viewHolde.imgMarkClassAttendance.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mStandardDataList.size();
        //return 200;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseName, className, teacher;
        ImageView imgViewClassAttendance, imgMarkClassAttendance;
        RelativeLayout my_class_relative;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewClassAttendance = itemView.findViewById(R.id.imgViewClassAttendance);
            imgMarkClassAttendance = itemView.findViewById(R.id.imgMarkClassAttendance);
            className = itemView.findViewById(R.id.textViewClassName);
            teacher = itemView.findViewById(R.id.textViewTeacher);
            my_class_relative = itemView.findViewById(R.id.my_class_relative);


        }
    }
    public void onViewClick(int position){
        Bundle bundle;
        //Depending upon the Module FRagment Redirection
        switch (strModuleName) {
            case "AttendanceModule":
                StudentListStandardWiseFragment fragment = new StudentListStandardWiseFragment();
                 bundle=new Bundle();
                bundle.putString("ClassName", mStandardDataList.get(position).getStandardName()+" ("+
                        mStandardDataList.get(position).getDivisionName()+")");
                bundle.putString("DivisionId", String.valueOf(mStandardDataList.get(position).getDivisionId()));
                bundle.putString("StandardID", String.valueOf(mStandardDataList.get(position).getStandardId()));
                fragment.setArguments(bundle);
                appUtils.onAddFragment(activity, fragment,R.id.fragment_mainLayout);
                break;
            case "ClassTimetable":
                ClassTimeTableFragment classTimeTableFragment = new ClassTimeTableFragment();
                bundle=new Bundle();
                bundle.putString("ClassName", mStandardDataList.get(position).getStandardName()+" ("+
                        mStandardDataList.get(position).getDivisionName()+")");
                bundle.putString("DivisionId", String.valueOf(mStandardDataList.get(position).getDivisionId()));
                bundle.putString("StandardID", String.valueOf(mStandardDataList.get(position).getStandardId()));
                classTimeTableFragment.setArguments(bundle);
                appUtils.onAddFragment(activity, classTimeTableFragment,R.id.fragment_mainLayout);
                break;
            case "ExamTimetable":
                ClassExamTimeTableFragment classExamTimeTableFragment = new ClassExamTimeTableFragment();
                bundle=new Bundle();
                bundle.putString("ClassName", mStandardDataList.get(position).getStandardName()+" ("+
                        mStandardDataList.get(position).getDivisionName()+")");
                bundle.putString("DivisionId", String.valueOf(mStandardDataList.get(position).getDivisionId()));
                bundle.putString("StandardID", String.valueOf(mStandardDataList.get(position).getStandardId()));
                classExamTimeTableFragment.setArguments(bundle);
                appUtils.onAddFragment(activity, classExamTimeTableFragment,R.id.fragment_mainLayout);
                break;
            case "Assignment":
                AssignmentFragment assignmentFragment = new AssignmentFragment();
                bundle=new Bundle();
                bundle.putString("ClassName", mStandardDataList.get(position).getStandardName()+" ("+
                        mStandardDataList.get(position).getDivisionName()+")");
                bundle.putString("DivisionId", String.valueOf(mStandardDataList.get(position).getDivisionId()));
                bundle.putString("StandardID", String.valueOf(mStandardDataList.get(position).getStandardId()));
                assignmentFragment.setArguments(bundle);
                appUtils.onAddFragment(activity, assignmentFragment,R.id.fragment_mainLayout);
                break;
            default:
                break;
        }
    }
}
