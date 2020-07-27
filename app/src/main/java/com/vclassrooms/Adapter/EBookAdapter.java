package com.vclassrooms.Adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Entity.AssignmentResponse;
import com.vclassrooms.Entity.EbookDetailsResponse;
import com.vclassrooms.R;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Rahul on 22,July,2020
 */
public class EBookAdapter extends RecyclerView.Adapter<EBookAdapter.ViewHolder> {
    Context context;
    public List<EbookDetailsResponse.EbookDetail> ebookDetailList;
    LayoutInflater inflter;
    private int row_index = -1;
    EbookDataClick ebookDataClick;
    AppUtils appUtils;
    public EBookAdapter(Context context, List<EbookDetailsResponse.EbookDetail> ebookDetailList) {
        this.context = context;
        this.ebookDetailList = ebookDetailList;
    }

    public void setOnClickListener(EbookDataClick ebookDataClick) {
        this.ebookDataClick = ebookDataClick;
    }
    public interface EbookDataClick {
        void onEbooktClick(String details, int position);
    }


    @NonNull
    @Override
    public EBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.e_book_adapter, parent, false);
        return new EBookAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EBookAdapter.ViewHolder viewHolde, final int position) {
        appUtils=new AppUtils();
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                appUtils.setText(viewHolde.txtfileSize,ebookDetailList.get(position).getFileSize().toUpperCase());
                appUtils.setText(viewHolde.txtFileTitle,ebookDetailList.get(position).getTitle());
                appUtils.setText(viewHolde.txtCalenDate,appUtils.convertDateFormat("yyyy-MM-dd'T'HH:mm:ss","dd/MM/yyyy",ebookDetailList.get(position).getInsertedDate()));
                appUtils.setText(viewHolde.teacher_txt,ebookDetailList.get(position).getTeacherName());


                viewHolde.linear_download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ebookDataClick != null) {
                            ebookDataClick.onEbooktClick(ebookDetailList.get(position).getVchFilePath(), position);
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
        return ebookDetailList.size();
        //return 200;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFileTitle,txtCalenDate,teacher_txt,txtfileSize;
        LinearLayout linear_download;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFileTitle=(TextView)itemView.findViewById(R.id.txtFileTitle);
            txtfileSize=(TextView)itemView.findViewById(R.id.txtfileSize);
            txtCalenDate=(TextView)itemView.findViewById(R.id.txtCalenDate);
            teacher_txt = (TextView)itemView.findViewById(R.id.teacher_txt);
            linear_download = (LinearLayout) itemView.findViewById(R.id.linear_download);


        }
    }


}
