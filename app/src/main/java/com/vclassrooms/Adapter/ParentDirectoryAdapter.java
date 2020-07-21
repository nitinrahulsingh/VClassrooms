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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.ParentDirectoryResponse.DirectoryeDetail;
import com.vclassrooms.Entity.ParentDirectoryResponse;
import com.vclassrooms.Fragment.ParentDirectoryFragment;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 01,July,2020
 */
public class ParentDirectoryAdapter extends RecyclerView.Adapter<ParentDirectoryAdapter.ViewHolder> {
    Context context;
    List<ParentDirectoryResponse.DirectoryeDetail> mStudentDataList;
    List<ParentDirectoryResponse.DirectoryeDetail> mChildDataList;
    LayoutInflater inflter;
    private int row_index = -1;
    AppUtils appUtils;
    Constatnts constatnts;
    String strAuth, strRoleid, strUserId, strSchoolId, strAcademicId;
    ViewHolder holder;
    LinearLayoutManager mLayoutManager;
    ChildDirectoryAdapter childDirectoryAdapter;
    private StudentDataClick mStudentDataClick;
    ParentDirectoryAdapter studentDirectoryAdapter;
    int load = 0;

    public interface StudentDataClick {
        void onStudentDataClick(int id, String value);
    }

    public ParentDirectoryAdapter(Context context, List<ParentDirectoryResponse.DirectoryeDetail> StudentDataList, ParentDirectoryFragment Fragment) {
        this.context = context;
        this.mStudentDataList = StudentDataList;
        this.mStudentDataClick = (StudentDataClick) Fragment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_parent_directory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        appUtils=new AppUtils();
        Handler mainHandler = new Handler(context.getMainLooper());
        holder = viewHolder;
        mLayoutManager = new LinearLayoutManager(context);
        holder.familyMemberRecyclerView.setLayoutManager(mLayoutManager);
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                appUtils.setText(viewHolder.tvName,mStudentDataList.get(position).getFatherName());
                viewHolder.parentCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        row_index = viewHolder.getAdapterPosition();
                        if (mStudentDataList.get(position).isExpand() == false) {
                            mStudentDataClick.onStudentDataClick(position, mStudentDataList.get(position).getMobileNo());
                            mStudentDataList.get(position).setExpand(true);
                        } else {
                            mStudentDataList.get(position).setExpand(false);

                        }
                        notifyItemChanged(position);
                    }
                });
                if (mStudentDataList.get(position).getDirectoryeDetail() != null) {
                    if(mStudentDataList.get(position).isExpand()){
                        ChildDirectoryAdapter ChildDirectoryAdapter = new ChildDirectoryAdapter(context, mStudentDataList.get(position).getDirectoryeDetail());
                        viewHolder.familyMemberRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                        viewHolder.familyMemberRecyclerView.setAdapter(ChildDirectoryAdapter);
                        viewHolder.familyMemberRecyclerView.setHasFixedSize(true);
                        viewHolder.familyMemberRecyclerView.setNestedScrollingEnabled(false);
                    }
                }
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
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgProfile;
        TextView tvName;
        LinearLayout linearChild;
        CardView parentCardView;
        RecyclerView familyMemberRecyclerView;
        ChildDirectoryAdapter ChildDirectoryAdapter;
        LinearLayoutManager mLayoutManager;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            linearChild = (LinearLayout) itemView.findViewById(R.id.linearChild);
            parentCardView = (CardView) itemView.findViewById(R.id.parentCardView);
            familyMemberRecyclerView = (RecyclerView) itemView.findViewById(R.id.familyMemberRecyclerView);


        }
    }

}