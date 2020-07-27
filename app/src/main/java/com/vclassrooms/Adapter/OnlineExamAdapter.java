package com.vclassrooms.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.EbookDetailsResponse;
import com.vclassrooms.Entity.OnlineExaminationResponse;
import com.vclassrooms.R;

import java.util.List;

/**
 * Created by Rahul on 25,July,2020
 */
public class OnlineExamAdapter extends RecyclerView.Adapter<OnlineExamAdapter.ViewHolder> {
    Context context;
    public List<OnlineExaminationResponse.OnlineExamination> onlineExaminationList;
    LayoutInflater inflter;
    private int row_index = -1;
    OnlineExamDataClick onEbooktClick;
    AppUtils appUtils;
    Constatnts constatnts;
    String strRoleid;
    public OnlineExamAdapter(Context context, List<OnlineExaminationResponse.OnlineExamination> onlineExaminationList) {
        this.context = context;
        this.onlineExaminationList = onlineExaminationList;
    }

    public void setOnClickListener(OnlineExamDataClick onEbooktClick) {
        this.onEbooktClick = onEbooktClick;
    }
    public interface OnlineExamDataClick {
        void onExamClick(String details, int position);
    }


    @NonNull
    @Override
    public OnlineExamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_examfragment, parent, false);
        return new OnlineExamAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OnlineExamAdapter.ViewHolder viewHolde, final int position) {
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                appUtils.setText(viewHolde.txtTitle,onlineExaminationList.get(position).getTitle());
                appUtils.setText(viewHolde.tmtx, String.valueOf(onlineExaminationList.get(position).getTotalMark()));
                appUtils.setText(viewHolde.subjtxt,onlineExaminationList.get(position).getSubjectName());

                if(strRoleid.contentEquals("1")|| strRoleid.contentEquals("2")){
                  viewHolde.relativeTodownload.setVisibility(View.VISIBLE);
                }else {
                    viewHolde.relativeTodownload.setVisibility(View.GONE);
                }

                viewHolde.linear_download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onEbooktClick != null) {
                            onEbooktClick.onExamClick(String.valueOf(onlineExaminationList.get(position).getExaminationId()), position);
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
        return onlineExaminationList.size();
        //return 200;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,subjtxt,tmtx;
        LinearLayout linear_download;
        RelativeLayout relativeTodownload;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=(TextView)itemView.findViewById(R.id.txtTitle);
            subjtxt=(TextView)itemView.findViewById(R.id.subjtxt);
            tmtx = (TextView)itemView.findViewById(R.id.tmtx);
            linear_download = (LinearLayout) itemView.findViewById(R.id.linear_download);
            relativeTodownload = (RelativeLayout) itemView.findViewById(R.id.relativeTodownload);


        }
    }


}
