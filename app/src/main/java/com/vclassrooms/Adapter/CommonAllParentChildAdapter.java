package com.vclassrooms.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.vclassrooms.Entity.StudentListItem;
import com.vclassrooms.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rahul on 02,July,2020
 */
public class CommonAllParentChildAdapter extends RecyclerView.Adapter<CommonAllParentChildAdapter.ViewHolder> {
    private Context context;
    private List<StudentListItem> studentListItems;

    private int row_index = -1, posFirst = 0, postSecnd = 0;
    String firstNameText = "";
    String startAcdemcYears = "", endAcademicYears = "";
    AppUtils appUtils;
    ChildDataClick childDataClick;
    public CommonAllParentChildAdapter(Context contexts, List<StudentListItem> studentListItemArrayList,
                                      int iSelected) {
        context = contexts;
        studentListItems = studentListItemArrayList;

        postSecnd = iSelected;
    }
    public void setOnClickListener(ChildDataClick childDataClick) {
        this.childDataClick = childDataClick;
    }
    public interface ChildDataClick {
        void onChildDataClick(int id, String name);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_all_parent_child_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        appUtils=new AppUtils();
        final StudentListItem studentListItem = studentListItems.get(position);

        if (studentListItems.size() == 1) {
            RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                    500, ViewGroup.LayoutParams.MATCH_PARENT);
            holder.mBackgroundAssign.setLayoutParams(rel_btn);
        } else if (studentListItems.size() == 2) {
            RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                    300, ViewGroup.LayoutParams.MATCH_PARENT);
            holder.mBackgroundAssign.setLayoutParams(rel_btn);
        }
        firstNameText = Html.fromHtml(studentListItem.getFirstName()).toString();
        int spacePos = firstNameText.indexOf(" ");
        if (spacePos > 0) {
            firstNameText= firstNameText.substring(0, spacePos);
        }

        appUtils.setText(holder.txtChildrenName,firstNameText);

        holder.no_image_relative.setVisibility(View.VISIBLE);
        holder.no_image_relative.setBackground(context.getResources().getDrawable(R.drawable.ring));

        appUtils.setText(holder.txtStudentNames,firstNameText.substring(0, 1));

        appUtils.setImage(holder.imgStudent,context,studentListItem.getPhotoPath());
        holder.cardBackdround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                row_index = position;
                if (childDataClick!=null){
                    childDataClick.onChildDataClick(studentListItem.getId(), Html.fromHtml(studentListItem.getFirstName()).toString());
                    notifyDataSetChanged();
                    posFirst = 100;
                }
            }
        });

        if (row_index == position) {


            holder.cardBackdround.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.hack.setVisibility(View.GONE);
            holder.hack1.setVisibility(View.VISIBLE);
            holder.cardBackdround.setEnabled(false);

        } else {
            holder.cardBackdround.setEnabled(true);
            holder.cardBackdround.setBackgroundColor(context.getResources().getColor(R.color.gray_100));
            holder.hack.setVisibility(View.VISIBLE);
            holder.hack1.setVisibility(View.GONE);
        }

        if (posFirst == 0) {
            if (position == postSecnd) {
                if (childDataClick!=null){
                    childDataClick.onChildDataClick(studentListItem.getId(), studentListItem.getFirstName());
                    holder.cardBackdround.setBackgroundColor(context.getResources().getColor(R.color.white));
                    holder.hack.setVisibility(View.GONE);
                    holder.hack1.setVisibility(View.VISIBLE);
                    holder.cardBackdround.setEnabled(false);
                }

            }

        }
    }


    @Override
    public int getItemCount() {
        return studentListItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView txtChildrenName;
        LinearLayout linerClick;
        LinearLayout mBackgroundAssign;
        RelativeLayout cardBackdround;
        CircleImageView imgStudent;
        RelativeLayout no_image_relative, relaParent;
        TextView txtStudentNames;
        TextView hack, hack1;
        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            txtChildrenName = (TextView) view.findViewById(R.id.txtChildrenName);
            hack = (TextView) view.findViewById(R.id.hack);
            hack1 = (TextView) view.findViewById(R.id.hack1);
            mBackgroundAssign = (LinearLayout) view.findViewById(R.id.mBackgroundAssign);
            relaParent = (RelativeLayout) view.findViewById(R.id.relaParent);


            cardBackdround = (RelativeLayout) view.findViewById(R.id.cardBackdround);

            imgStudent = (CircleImageView) view.findViewById(R.id.imgStudent);
            no_image_relative = (RelativeLayout) view.findViewById(R.id.no_image_relative);
            txtStudentNames = (TextView) view.findViewById(R.id.txtStudentNames);

        }


    }

}

