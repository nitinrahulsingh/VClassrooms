package com.vclassrooms.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Adapter.AnnouncementAdapter;
import com.vclassrooms.Adapter.NotificationAdapter;
import com.vclassrooms.BottomSheetDialog.AddAnouncementBottomSheet;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Database.DatabaseHelper;
import com.vclassrooms.Database.NotificationDetails;
import com.vclassrooms.Entity.AnnouncementDetailResponse;
import com.vclassrooms.Interface.CommonInterface;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 17,August,2020
 */
public class NotificationFragment extends Fragment {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.txt_no_data)
    TextView txt_no_data;

    DatabaseHelper databaseHelper;
    List<NotificationDetails> listofNotification;
    LinearLayoutManager mLayoutManager;
    NotificationAdapter notificationAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.notification_fragment,null);
        ButterKnife.bind(NotificationFragment.this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
        return mview;
    }

    private void init() {
        databaseHelper = new DatabaseHelper(getContext());
        mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
        listofNotification = databaseHelper.getNotification(constatnts.NotificationFlag);
        if(listofNotification.size()>0){
            recyclerview.setVisibility(View.VISIBLE);
            txt_no_data.setVisibility(View.GONE);
            notificationAdapter = new NotificationAdapter(getActivity(), listofNotification);
            recyclerview.setAdapter(notificationAdapter);
            recyclerview.setHasFixedSize(true);
            recyclerview.setNestedScrollingEnabled(false);
        }else {
            recyclerview.setVisibility(View.GONE);
            txt_no_data.setVisibility(View.VISIBLE);
        }

//        if(listofNotification.size()>0){
//            databaseHelper.updateNotification(constatnts.Read,constatnts.NotificationFlag);
//        }

    }


}