package com.vclassrooms.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.vclassrooms.Entity.AttendanceDetail;
import com.vclassrooms.Entity.StudentAttendanceDetailResponse;
import com.vclassrooms.Entity.StudentListResponse;
import com.vclassrooms.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rahul on 09,July,2020
 */
public class MarkAttendanceAdapter extends RecyclerView.Adapter<MarkAttendanceAdapter.ViewHolder> {
    Context context;
    public List<AttendanceDetail> mStudentDataList;
    LayoutInflater inflter;
    MarkAttendanceAdapter.ViewHolder viewHolder;
    private int row_index = -1;
    AppUtils appUtils;
    public MarkAttendanceAdapter(Context context, List<AttendanceDetail> StudentDataList) {
        this.context = context;
        this.mStudentDataList = StudentDataList;
    }


    @NonNull
    @Override
    public MarkAttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mark_attendance_adapter, parent, false);
        return new MarkAttendanceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MarkAttendanceAdapter.ViewHolder viewHolde, final int position) {
        appUtils=new AppUtils();
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                viewHolder=viewHolde;
                final AttendanceDetail attendanceDetail = mStudentDataList.get(position);
                String name="-";
                name= attendanceDetail.getName();
                appUtils.setText(viewHolder.tvName,name);

                if(attendanceDetail.getStatus().contentEquals("P")){
                    attendanceDetail.setPresent(true);
                    appUtils.setText(viewHolder.attendance_status,"P");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        viewHolder.attendance_status.setButtonTintList(context.getResources().getColorStateList(R.color.green));
                    }
                    viewHolder.attendance_status.setTextColor(context.getResources().getColorStateList(R.color.green));
                }else if( attendanceDetail.getStatus().contentEquals("A")){
                    attendanceDetail.setPresent(false);
                    appUtils.setText(viewHolder.attendance_status,"A");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        viewHolder.attendance_status.setButtonTintList(context.getResources().getColorStateList(R.color.red));
                    }
                    viewHolder.attendance_status.setTextColor(context.getResources().getColorStateList(R.color.red));
                }else if( attendanceDetail.getStatus()==null){
                    attendanceDetail.setPresent(true);
                    attendanceDetail.setStatus("P");
                    appUtils.setText(viewHolder.attendance_status,"P");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        viewHolder.attendance_status.setButtonTintList(context.getResources().getColorStateList(R.color.red));
                    }
                    viewHolder.attendance_status.setTextColor(context.getResources().getColorStateList(R.color.red));
                }

                if(attendanceDetail.getImageURL()!=null) {
                    appUtils.setImage(viewHolder.imgProfile,context,attendanceDetail.getImageURL());
                }

                viewHolde.attendance_status.setOnCheckedChangeListener(null);

                //if true, your checkbox will be selected, else unselected
                viewHolde.attendance_status.setChecked(attendanceDetail.isPresent());

                viewHolde.attendance_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        attendanceDetail.setPresent(isChecked);
                        if(isChecked){
                            attendanceDetail.setStatus("P");
                        }else {
                            attendanceDetail.setStatus("A");
                        }
                        notifyItemChanged(position);
                    }
                });




            }
        };
        mainHandler.post(myRunnable);
    }

    @Override
    public int getItemCount() {
        return mStudentDataList.size();
        //return 200;
    }
    public List<AttendanceDetail> getStudentList() {
        return mStudentDataList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgProfile;
        TextView tvName,tvRollNo;
        CheckBox attendance_status;
        LinearLayout student_linear;
        CardView parentCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=(TextView)itemView.findViewById(R.id.tvName);
            tvRollNo=(TextView)itemView.findViewById(R.id.tvRollNo);
            imgProfile = (CircleImageView)itemView.findViewById(R.id.imgProfile);
            student_linear =(LinearLayout)itemView.findViewById(R.id.student_linear);
            parentCardView  = (CardView)itemView.findViewById(R.id.parentCardView);
            attendance_status  = (CheckBox) itemView.findViewById(R.id.attendance_status);


        }
    }


}
