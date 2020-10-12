package com.vclassrooms.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Entity.OnlineLectureDetailsResponse;
import com.vclassrooms.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Rahul on 23,July,2020
 */
public class OnlineLecAdapter  extends RecyclerView.Adapter<OnlineLecAdapter.ViewHolder> {
    Context context;
    public List<OnlineLectureDetailsResponse.OnlineLecture> onlineLectureList;
    LayoutInflater inflter;
    private int row_index = -1;
    AppUtils appUtils;

    public OnlineLecAdapter(Context context, List<OnlineLectureDetailsResponse.OnlineLecture> onlineLectureList) {
        this.context = context;
        this.onlineLectureList = onlineLectureList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_lec_adapter, parent, false);
        return new OnlineLecAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OnlineLecAdapter.ViewHolder viewHolder, final int position) {
        appUtils = new AppUtils();
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {


                appUtils.setText(viewHolder.txtName, onlineLectureList.get(position).getLessonName());


             appUtils.setText(viewHolder.txtdate, appUtils.convertDateFormat("yyyy-MM-dd'T'HH:mm:ss", "dd/MM/yyyy", onlineLectureList.get(position).getInsertedDate()));


//                if (onlineLectureList.get(position).getFilePath() != null) {
//                    appUtils.setImage(viewHolder.folderimg, context, onlineLectureList.get(position).getFolderPath());
//                }
                if(onlineLectureList.get(position).getFilePath()!=null && !TextUtils.isEmpty(onlineLectureList.get(position).getFilePath())){
                    new DownloadImage(viewHolder.folderimg).execute(onlineLectureList.get(position).getFilePath());
                }

                viewHolder.main_cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onlineLectureList.get(position).getFilePath()!=null && !TextUtils.isEmpty(onlineLectureList.get(position).getFilePath())){
                            String videoUrl = onlineLectureList.get(position).getFilePath();
                            Intent playVideo = new Intent(Intent.ACTION_VIEW);
                            playVideo.setDataAndType(Uri.parse(videoUrl), "video/*");
                            context.startActivity(playVideo);
                        }

                    }
                });


            }
        };
        mainHandler.post(myRunnable);
    }

    @Override
    public int getItemCount() {
        return onlineLectureList.size();
        //return 200;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView folderimg;
        TextView txtName, txtdate;
        CardView main_cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            main_cardview = (CardView) itemView.findViewById(R.id.main_cardview);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtdate = (TextView) itemView.findViewById(R.id.txtdate);
            folderimg = (ImageView) itemView.findViewById(R.id.folderimg);
        }
    }
    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImage(ImageView bmImage) {
            this.bmImage = (ImageView ) bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            Bitmap myBitmap = null;
            MediaMetadataRetriever mMRetriever = null;
            try {
                mMRetriever = new MediaMetadataRetriever();
                if (Build.VERSION.SDK_INT >= 14)
                    mMRetriever.setDataSource(urls[0], new HashMap<String, String>());
                else
                    mMRetriever.setDataSource(urls[0]);
                myBitmap = mMRetriever.getFrameAtTime();
            } catch (Exception e) {
                e.printStackTrace();


            } finally {
                if (mMRetriever != null) {
                    mMRetriever.release();
                }
            }
            return myBitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}