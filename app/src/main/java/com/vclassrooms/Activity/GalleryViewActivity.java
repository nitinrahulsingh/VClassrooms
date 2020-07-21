package com.vclassrooms.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.vclassrooms.Adapter.AssignmentAdapter;
import com.vclassrooms.Adapter.SlidingImage_Adapter;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.AssignmentResponse;
import com.vclassrooms.Entity.GalleryDetailResponse;
import com.vclassrooms.Fragment.AssignmentFragment;
import com.vclassrooms.PageIndicator.CirclePageIndicator;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 20,July,2020
 */
public class GalleryViewActivity extends AppCompatActivity {
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId,strGalleryId;
    List<GalleryDetailResponse.GalleryDetails1> galleryDetailResponses = new ArrayList<GalleryDetailResponse.GalleryDetails1>();
    @BindView(R.id.txt_no_data)
    TextView txt_no_data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.galleryview_activity);
        ButterKnife.bind(GalleryViewActivity.this);
        context= GalleryViewActivity.this;
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
        mPager = (ViewPager) findViewById(R.id.member_imageViewPager);
        strGalleryId= getIntent().getExtras().getString("Gallery_Id");
        getImagesApi();

    }
    private void getImagesApi() {
        try {
            appUtils.showProgressbar(context);
            Call<GalleryDetailResponse> call = ApiService.buildService(context).getGalleryDetails(strAuth,"SelectFolderImages",strSchoolId,strAcademicId,strGalleryId);
            call.enqueue(new Callback<GalleryDetailResponse>() {
                @Override
                public void onResponse(Call<GalleryDetailResponse> call, Response<GalleryDetailResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode()==0) {
                            if(response.body().getData().getGalleryDetails1()!=null && response.body().getData().getGalleryDetails1().size()>0 ){
                                txt_no_data.setVisibility(View.GONE);
                                galleryDetailResponses=response.body().getData().getGalleryDetails1();
                                onImageSet();
                            }else {

                                txt_no_data.setVisibility(View.VISIBLE);
                            }
                        } else  if (response.body().getStatusCode()==1){
                            appUtils.showToast(context, getString(R.string.error_message));
                        }else  if (response.body().getStatusCode()==2){
                            appUtils.showToast(context, getString(R.string.unauthorize_message));
                        }
                    } else {
                        appUtils.showToast(context, getString(R.string.error_message));
                    }

                }

                @Override
                public void onFailure(Call<GalleryDetailResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void onImageSet() {
        mPager.setAdapter(new SlidingImage_Adapter(GalleryViewActivity.this,galleryDetailResponses));
        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
//Set circle indicator radius
        indicator.setRadius(5 * density);
        NUM_PAGES =galleryDetailResponses.size();
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
        //Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
