package com.vclassrooms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.vclassrooms.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableListAdapter(Context contexts, List<String> listDataHeaders,
                                 HashMap<String, List<String>> listChildData) {
        context = contexts;
        listDataHeader = listDataHeaders;
        _listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String stringTitle = "";
        final String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
        View viewLine = (View) convertView.findViewById(R.id.viewLine);

        if (groupPosition == 0) {
            viewLine.setVisibility(View.GONE);

        } else {
            viewLine.setVisibility(View.VISIBLE);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        LinearLayout linearHeader = (LinearLayout) convertView.findViewById(R.id.linearHeader);
        lblListHeader.setText(headerTitle);



        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        ImageView imageViewArrow = (ImageView) convertView.findViewById(R.id.imageViewArrow);

        if (headerTitle.contentEquals(context.getString(R.string.Home))) {
            imageView.setImageResource(R.drawable.logo);
        } else if (headerTitle.contentEquals(context.getString(R.string.Profile))) {
            imageView.setImageResource(R.drawable.profile);

        } else if (headerTitle.contentEquals(context.getString(R.string.Setting))) {
            imageView.setImageResource(R.drawable.setting);
            imageViewArrow.setImageResource(R.drawable.setting);
            //Direcotory check
        } else if (headerTitle.contentEquals(context.getString(R.string.directory))){
                imageView.setImageResource(R.drawable.directories1);
        } else if (headerTitle.contentEquals(context.getString(R.string.Logout))) {
            imageView.setImageResource(R.drawable.logout);
        }
        else if (headerTitle.contentEquals(context.getString(R.string.dashboard))) {
            imageView.setImageResource(R.drawable.logout);
        }else if (headerTitle.contentEquals(context.getString(R.string.gallery))) {
            imageView.setImageResource(R.drawable.setting);
        }




        if (_listDataChild.get(listDataHeader.get(groupPosition)).size() == 0) {
            imageViewArrow.setImageResource(R.drawable.ic_action_close);

        } else {
            if (isExpanded) {
                imageViewArrow.setImageResource(R.drawable.ic_action_up_arrow);
            } else {
                imageViewArrow.setImageResource(R.drawable.down_arrow);

            }
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
         String headerTitle = (String) getGroup(groupPosition);
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
        String headerTitle = (String) getGroup(groupPosition);
    }
}
