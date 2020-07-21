package com.vclassrooms.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Entity.StudentDetails;
import com.vclassrooms.Entity.StudentDirectoryResponse;
import com.vclassrooms.R;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChildDirectoryAdapter extends RecyclerView.Adapter<ChildDirectoryAdapter.ViewHolder> {
    Context context;
    List<StudentDetails> mStudentDataList;

    AppUtils appUtils;
    public ChildDirectoryAdapter(Context context, List<StudentDetails> StudentDataList) {
        this.context = context;
        this.mStudentDataList = StudentDataList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.childadapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        appUtils=new AppUtils();
        appUtils.setText(viewHolder.tvName,mStudentDataList.get(position).getFirstName());
        if(mStudentDataList.get(position).getImageURL()!=null) {
         appUtils.setImage(viewHolder.imgProfile,context,mStudentDataList.get(position).getImageURL());
        }

    }

    @Override
    public int getItemCount() {


        return   mStudentDataList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgProfile;
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=(TextView)itemView.findViewById(R.id.tvName);
            imgProfile = (CircleImageView)itemView.findViewById(R.id.imgProfile);
        }
    }
}
