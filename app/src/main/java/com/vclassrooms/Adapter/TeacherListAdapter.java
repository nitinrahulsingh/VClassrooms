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
import com.vclassrooms.Entity.StudentListResponse;
import com.vclassrooms.Entity.TeacherDirectoryResponse;
import com.vclassrooms.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rahul on 24,July,2020
 */
public class TeacherListAdapter extends RecyclerView.Adapter<TeacherListAdapter.ViewHolder> {
    Context context;
    public List<TeacherDirectoryResponse.DirectoryeDetail> mTearcherDetail;
    LayoutInflater inflter;
    TeacherListAdapter.ViewHolder viewHolder;
    private int row_index = -1;
    TeacherDataClick teacherDataClick;
    AppUtils appUtils;
    public TeacherListAdapter(Context context, List<TeacherDirectoryResponse.DirectoryeDetail> mTearcherDetail) {
        this.context = context;
        this.mTearcherDetail = mTearcherDetail;
    }

    public void setOnClickListener(TeacherDataClick teacherDataClick) {
        this.teacherDataClick = teacherDataClick;
    }
    public interface TeacherDataClick {
        void onTeachertDataClick(int id, int position);
    }


    @NonNull
    @Override
    public TeacherListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentlist_adapter, parent, false);
        return new TeacherListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TeacherListAdapter.ViewHolder viewHolde, final int position) {
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                appUtils=new AppUtils();
                viewHolder=viewHolde;
                String name="-";
                name= mTearcherDetail.get(position).getFirstName();
                appUtils.setText(viewHolder.tvName,name);
                viewHolder.tvRollNo.setVisibility(View.INVISIBLE);
                if(mTearcherDetail.get(position).getImageURL()!=null) {
                    appUtils.setImage(viewHolder.imgProfile,context,mTearcherDetail.get(position).getImageURL());
                }

                viewHolder.student_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTearcherDetail != null) {
                            teacherDataClick.onTeachertDataClick(mTearcherDetail.get(position).getEmpId(), position);
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
        return mTearcherDetail.size();
        //return 200;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgProfile;
        TextView tvName,tvRollNo;
        LinearLayout student_linear;
        CardView parentCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=(TextView)itemView.findViewById(R.id.tvName);
            tvRollNo=(TextView)itemView.findViewById(R.id.tvRollNo);
            imgProfile = (CircleImageView)itemView.findViewById(R.id.imgProfile);
            student_linear =(LinearLayout)itemView.findViewById(R.id.student_linear);
            parentCardView  = (CardView)itemView.findViewById(R.id.parentCardView);


        }
    }


}
