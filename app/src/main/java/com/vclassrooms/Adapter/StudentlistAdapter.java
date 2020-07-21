package com.vclassrooms.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
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
import com.vclassrooms.Entity.StudentDirectoryResponse;
import com.vclassrooms.Entity.StudentListResponse;
import com.vclassrooms.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rahul on 09,July,2020
 */
public class StudentlistAdapter extends RecyclerView.Adapter<StudentlistAdapter.ViewHolder> {
    Context context;
    public List<StudentListResponse.StudentList> mStudentDataList;
    LayoutInflater inflter;
    StudentlistAdapter.ViewHolder viewHolder;
    private int row_index = -1;
        StudentDataClick studentDataClick;
    public StudentlistAdapter(Context context, List<StudentListResponse.StudentList> StudentDataList) {
        this.context = context;
        this.mStudentDataList = StudentDataList;
    }

    public void setOnClickListener(StudentDataClick studentDataClick) {
        this.studentDataClick = studentDataClick;
    }
    public interface StudentDataClick {
        void onStudentDataClick(int id, int position);
    }


    @NonNull
    @Override
    public StudentlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentlist_adapter, parent, false);
        return new StudentlistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentlistAdapter.ViewHolder viewHolde, final int position) {
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                viewHolder=viewHolde;
                String name="-";
                name= mStudentDataList.get(position).getFirstName();
                viewHolder.tvName.setText(name);
                
                if(mStudentDataList.get(position).getRollNo()!=null)
                {
                    viewHolder.tvRollNo.setText(mStudentDataList.get(position).getRollNo().toString());
                }else
                {
                    viewHolder.tvRollNo.setText("-");
                }


                if(mStudentDataList.get(position).getImageURL()!=null) {
                    Glide.with(context)
                            .load(mStudentDataList.get(position).getImageURL())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    //on load failed
                                    viewHolder.imgProfile.setVisibility(View.VISIBLE);
                                    viewHolder.imgProfile.setBackgroundResource(R.drawable.profile);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    //on load success

                                    viewHolder.imgProfile.setVisibility(View.VISIBLE);
                                    return false;
                                }
                            })
                            .into(viewHolder.imgProfile);
                }

                viewHolder.student_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    if (studentDataClick != null) {
                        studentDataClick.onStudentDataClick(mStudentDataList.get(position).getStudentId(), position);
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
        return mStudentDataList.size();
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

