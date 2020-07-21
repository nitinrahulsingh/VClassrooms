package com.vclassrooms.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Activity.GalleryViewActivity;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Entity.GalleryDetailResponse;
import com.vclassrooms.Entity.StudentDirectoryResponse;
import com.vclassrooms.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rahul on 20,July,2020
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    Context context;
    public List<GalleryDetailResponse.GalleryDetails1> galleryDetails1s;
    LayoutInflater inflter;
    private int row_index = -1;
    AppUtils appUtils;
    public GalleryAdapter(Context context, List<GalleryDetailResponse.GalleryDetails1> galleryDetails1s) {
        this.context = context;
        this.galleryDetails1s = galleryDetails1s;
    }




    @NonNull
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_adapter, parent, false);
        return new GalleryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryAdapter.ViewHolder viewHolder, final int position) {
        appUtils=new AppUtils();
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {


                appUtils.setText(viewHolder.txtName,galleryDetails1s.get(position).getTitle());


                appUtils.setText(viewHolder.txtdate,appUtils.convertDateFormat("yyyy-MM-dd'T'HH:mm:ss","dd/MM/yyyy",galleryDetails1s.get(position).getInsertedDate()));





                if(galleryDetails1s.get(position).getFolderPath()!=null) {
                    appUtils.setImage(viewHolder.folderimg,context,galleryDetails1s.get(position).getFolderPath());
                }

                viewHolder.main_cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle=new Bundle();
                        bundle.putString("Gallery_Id",galleryDetails1s.get(position).getGalleryId().toString());
                        appUtils.simpleIntent(context, GalleryViewActivity.class,bundle);
                    }
                });



            }
        };
        mainHandler.post(myRunnable);
    }

    @Override
    public int getItemCount() {
        return galleryDetails1s.size();
        //return 200;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView folderimg;
        TextView txtName,txtdate;
        CardView main_cardview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            main_cardview=(CardView) itemView.findViewById(R.id.main_cardview);
            txtName=(TextView)itemView.findViewById(R.id.txtName);
            txtdate=(TextView)itemView.findViewById(R.id.txtdate);
            folderimg=(ImageView) itemView.findViewById(R.id.folderimg);
        }
    }


}
