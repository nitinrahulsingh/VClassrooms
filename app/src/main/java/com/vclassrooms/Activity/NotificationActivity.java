package com.vclassrooms.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Adapter.NotificationAdapter;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Database.DatabaseHelper;
import com.vclassrooms.Database.NotificationDetails;
import com.vclassrooms.R;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Rahul on 19,August,2020
 */
public class NotificationActivity extends AppCompatActivity {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.txt_no_data)
    TextView txt_no_data;
    @BindView(R.id.back_relative)
    TextView back_relative;
    @BindView(R.id.home_btn)
    ImageView home_btn;
    DatabaseHelper databaseHelper;
    List<NotificationDetails> listofNotification;
    LinearLayoutManager mLayoutManager;
    NotificationAdapter notificationAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        context= NotificationActivity.this;
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
    }
    private void init() {
        databaseHelper = new DatabaseHelper(context);
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
            notificationAdapter = new NotificationAdapter(context, listofNotification);
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

        back_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUtils.simpleIntentFinish(context, MainActivity.class,Bundle.EMPTY);
            }
        });
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUtils.simpleIntentFinish(context, MainActivity.class,Bundle.EMPTY);
            }
        });
    }
}
