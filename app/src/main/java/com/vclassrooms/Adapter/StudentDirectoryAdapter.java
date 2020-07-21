package com.vclassrooms.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
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
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Entity.StudentDirectoryResponse;
import com.vclassrooms.Fragment.StudentDirecoryFragment;
import com.vclassrooms.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rahul on 30,June,2020
 */
public class StudentDirectoryAdapter extends RecyclerView.Adapter<StudentDirectoryAdapter.ViewHolder> {
    Context context;
    public  List<StudentDirectoryResponse.DirectoryeDetail> mStudentDataList;
    LayoutInflater inflter;
    ViewHolder viewHolder;
    private int row_index = -1;
    AppUtils appUtils;
    public StudentDirectoryAdapter(Context context, List<StudentDirectoryResponse.DirectoryeDetail> StudentDataList) {
        this.context = context;
        this.mStudentDataList = StudentDataList;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_student_directory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolde, final int position) {
        appUtils=new AppUtils();
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                viewHolder=viewHolde;
                String name="-";
                name= mStudentDataList.get(position).getFirstName();
                appUtils.setText(viewHolder.tvName,name);
                appUtils.setText(viewHolder.tvEmailId,mStudentDataList.get(position).getEmailId());


                if(!TextUtils.isEmpty(mStudentDataList.get(position).getMobileNo()))
                {
                    String PhoneNo="-";
                    try{
                        if(!TextUtils.isEmpty(mStudentDataList.get(position).getMobileNo())){
                            String  mobilenumber=mStudentDataList.get(position).getMobileNo();
                            mobilenumber = mobilenumber.replaceAll("[^0-9]","");
                            PhoneNo=mobilenumber;
                        }

                    }catch (Exception e)
                    {

                    }
                    appUtils.setText(viewHolder.tvPhNo,PhoneNo);
                }else
                {
                    appUtils.setText(viewHolder.tvPhNo,mStudentDataList.get(position).getMobileNo());
                }

                    appUtils.setText(viewHolder.tvGrade,mStudentDataList.get(position).getStandardName()+" ("+mStudentDataList.get(position).getStandardName()+")");


                    appUtils.setText(viewHolder.tv_Father,mStudentDataList.get(position).getFatherName());


                    appUtils.setText(viewHolder.tv_Mother,mStudentDataList.get(position).getMotherName());


                    appUtils.setText(viewHolder.tvcurrent_class,(mStudentDataList.get(position).getStandardName()+" ("+mStudentDataList.get(position).getStandardName()+")"));



                if(mStudentDataList.get(position).getImageURL()!=null) {
                    appUtils.setImage(viewHolder.imgProfile,context,mStudentDataList.get(position).getImageURL());
                }

                viewHolder.student_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        row_index = viewHolder.getAdapterPosition();
                        if (mStudentDataList.get(position).isExpand()==false) {
                            mStudentDataList.get(position).setExpand(true);
                            notifyItemChanged(position);
                        } else {
                            mStudentDataList.get(position).setExpand(false);
                            notifyItemChanged(position);

                        }

                    }
                });

                if (mStudentDataList.get(position).isExpand()) {
                    viewHolder.linearChild.setVisibility(View.VISIBLE);

                } else {
                    viewHolder.linearChild.setVisibility(View.GONE);
                }


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
        TextView tvName,tvGrade,tvEmailId,tvPhNo,tvcurrent_class,tv_Father,tv_Mother;
        LinearLayout linearChild,student_linear;
        CardView parentCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=(TextView)itemView.findViewById(R.id.tvName);
            tvGrade=(TextView)itemView.findViewById(R.id.tvGrade);
            tvEmailId=(TextView)itemView.findViewById(R.id.tvEmailId);
            tvPhNo=(TextView)itemView.findViewById(R.id.tvPhNo);
            imgProfile = (CircleImageView)itemView.findViewById(R.id.imgProfile);
            linearChild =(LinearLayout)itemView.findViewById(R.id.linearChild);
            student_linear =(LinearLayout)itemView.findViewById(R.id.student_linear);
            parentCardView  = (CardView)itemView.findViewById(R.id.parentCardView);
            tvcurrent_class=(TextView)itemView.findViewById(R.id.tvcurrent_class);
            tv_Father=(TextView)itemView.findViewById(R.id.tvFatherName);
            tv_Mother=(TextView)itemView.findViewById(R.id.tvmothername);


        }
    }


}

