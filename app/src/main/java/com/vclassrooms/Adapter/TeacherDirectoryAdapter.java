package com.vclassrooms.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vclassrooms.BottomSheetDialog.AddGalleryImagesBottomSheet;
import com.vclassrooms.BottomSheetDialog.PhoneCallOptionBottomSheet;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Entity.TeacherDirectoryResponse;
import com.vclassrooms.Fragment.GalleryFragment;
import com.vclassrooms.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rahul on 30,June,2020
 */
public class TeacherDirectoryAdapter extends RecyclerView.Adapter<TeacherDirectoryAdapter.ViewHolder> {

    Context context;

    List<TeacherDirectoryResponse.DirectoryeDetail> mStaffDataList;
    LayoutInflater inflter;
    private int row_index = -1;

    AppUtils appUtils;
    public TeacherDirectoryAdapter(Context context, List<TeacherDirectoryResponse.DirectoryeDetail> StaffDataList) {
        this.context = context;
        this.mStaffDataList = StaffDataList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_teacher_directory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        appUtils=new AppUtils();

        if(mStaffDataList.get(position).getEmailId()==null || mStaffDataList.get(position).getEmailId().equalsIgnoreCase("")){
            holder.txtMail.setVisibility(View.INVISIBLE);
        }else{
            appUtils.setText(holder.txtMail,mStaffDataList.get(position).getEmailId());
        }
        if(TextUtils.isEmpty(mStaffDataList.get(position).getMobileNo())){
            holder.txtMob.setVisibility(View.INVISIBLE);
        }else{
            String PhoneNo="";
            try{
                if(!TextUtils.isEmpty(mStaffDataList.get(position).getMobileNo())){
                    String  mobilenumber=mStaffDataList.get(position).getMobileNo();
                    mobilenumber = mobilenumber.replaceAll("[^0-9]","");
                    PhoneNo=mobilenumber;
                }

            }catch (Exception e)
            {

            }
            appUtils.setText(holder.txtMob,PhoneNo);
        }

        holder.tvDaily_Schedule.setPaintFlags(holder.tvDaily_Schedule.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.tvDaily_Schedule.setText("Schedule");
        appUtils.setText(holder.txtName,mStaffDataList.get(position).getFirstName());
        holder.txtMob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneCallOptionBottomSheet phoneCallOptionBottomSheet = new
                        PhoneCallOptionBottomSheet();
                phoneCallOptionBottomSheet.newInstance(mStaffDataList.get(position).getMobileNo(),mStaffDataList.get(position).getEmailId(),mStaffDataList.get(position).getFirstName());
                phoneCallOptionBottomSheet.show(((AppCompatActivity) context).getSupportFragmentManager(),
                        "add__dialog_fragment");
            }
        });
        holder.txtMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneCallOptionBottomSheet phoneCallOptionBottomSheet = new
                        PhoneCallOptionBottomSheet();
                phoneCallOptionBottomSheet.newInstance(mStaffDataList.get(position).getMobileNo(),mStaffDataList.get(position).getEmailId(),mStaffDataList.get(position).getFirstName());
                phoneCallOptionBottomSheet.show(((AppCompatActivity) context).getSupportFragmentManager(),
                        "add__dialog_fragment");
            }
        });
        if(mStaffDataList.get(position).getImageURL()!=null) {
            appUtils.setImage(holder.imgStudent,context,mStaffDataList.get(position).getImageURL());
        }

        holder.tvDaily_Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try{
//                    Intent intent=new Intent(context, Staff_Schedule_activity.class);
//                    intent.putExtra("Staff_id",mStaffDataList.get(position).getId());
//                    intent.putExtra("StaffName",mStaffDataList.get(position).getLastName() + " " + mStaffDataList.get(position).getFirstName());
//                    context.startActivity(intent);
//                }catch (Exception e){
//                    Log.e("StaffRespose",e.getMessage());
//                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mStaffDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtMob,txtMail,tvDaily_Schedule;
        CircleImageView imgStudent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=(TextView)itemView.findViewById(R.id.txtName);
            txtMob=(TextView)itemView.findViewById(R.id.txtMob);
            txtMail=(TextView)itemView.findViewById(R.id.txtMail);
            imgStudent = (CircleImageView)itemView.findViewById(R.id.imgStudent);
            tvDaily_Schedule=(TextView)itemView.findViewById(R.id.tvDaily_Schedule);

        }
    }
}

