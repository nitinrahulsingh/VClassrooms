package com.vclassrooms.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.vclassrooms.Entity.UploadImageDetails;
import com.vclassrooms.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rahul on 11,July,2020
 */
public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.ViewHolder> {
    Context context;
    public List<UploadImageDetails> uploadImageDetails;
    LayoutInflater inflter;
    AttachmentAdapter.ViewHolder viewHolder;
    private int row_index = -1;
    AttachmenttDataClick attachmenttDataClick;
    AppUtils appUtils;
    public AttachmentAdapter(Context context, List<UploadImageDetails> uploadImageDetails) {
        this.context = context;
        this.uploadImageDetails = uploadImageDetails;
    }

    public void setOnClickListener(AttachmenttDataClick attachmenttDataClick) {
        this.attachmenttDataClick = attachmenttDataClick;
    }
    public interface AttachmenttDataClick {
        void onAttachmentClick(int id, int position);
    }


    @NonNull
    @Override
    public AttachmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attachment, parent, false);
        return new AttachmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttachmentAdapter.ViewHolder viewHolde, final int position) {
        appUtils=new AppUtils();
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                viewHolder=viewHolde;
                appUtils.setImage(viewHolde.ivPhoto,context,uploadImageDetails.get(position).getFilePath());
            }
        };
        mainHandler.post(myRunnable);
    }

    @Override
    public int getItemCount() {
        return uploadImageDetails.size();
        //return 200;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        RelativeLayout relvParent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
            relvParent = (RelativeLayout) itemView.findViewById(R.id.relvParent);



        }
    }


}

