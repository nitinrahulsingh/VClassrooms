package com.vclassrooms.Activity;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.SchoolResponse;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {
    AppUtils appUtils;
    Constatnts constatnts;
    String mobileNumber="";
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try{
            context= SplashScreen.this;
            appUtils=new AppUtils();
            constatnts=new Constatnts();
            transitionActivity1();

        }catch (Exception e)
        {
            e.getMessage();
        }
    }
    private void transitionActivity1() {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                try{
                        TedPermission();


                }catch (Exception e)
                {
                    e.getMessage();
                }


            }
        }, 2600);

    }
    private void getSchoolDetailApi() {
        try {
            appUtils.showProgressbar(context);
            Call<SchoolResponse> call = ApiService.buildService(context).getSchoolDetails(constatnts.LICENSEKEY,constatnts.COMMAND_SELECT);
            call.enqueue(new Callback<SchoolResponse>() {
                @Override
                public void onResponse(Call<SchoolResponse> call, Response<SchoolResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if(response.body().getData().getSchoolDetails()!=null){
                                ArrayList<String> arrayList = new ArrayList<>();
                                for (int i = 0; i < response.body().getData().getSchoolDetails().size(); i++) {
                                    int schoolId = response.body().getData().getSchoolDetails().get(i).getSchoolId();
                                    String schoolName = response.body().getData().getSchoolDetails().get(i).getSchoolName();
                                    String strNameAndID = String.valueOf(schoolId) + " " + schoolName;
                                    arrayList.add(strNameAndID);
                                }
                                appUtils.setStringPrefrences(SplashScreen.this, constatnts.SH_APPPREFSCHOOLLIST, arrayList.toString(),constatnts.SH_SCHOOLList);
                                appUtils.simpleIntentFinish(context, LoginActivity.class,Bundle.EMPTY);
                            }else {
                                appUtils.showToast(context, getString(R.string.no_data));
                            }
                        } else  if (response.body().getStatusCode().equals(1)){
                            appUtils.showToast(context, getString(R.string.error_message));
                        }else  if (response.body().getStatusCode().equals(2)){
                            appUtils.showToast(context, getString(R.string.unauthorize_message));
                        }
                    } else {
                        appUtils.showToast(context, getString(R.string.error_message));
                    }

                }

                @Override
                public void onFailure(Call<SchoolResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
    public void TedPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                if (TextUtils.isEmpty(appUtils.getStringPrefrences(SplashScreen.this, constatnts.SH_APPPREFSCHOOLLIST, constatnts.SH_SCHOOLList))) {
                    getSchoolDetailApi();
                } else  if (!TextUtils.isEmpty(appUtils.getStringPrefrences(SplashScreen.this, constatnts.SH_APPPREF, constatnts.SH_USERID))){
                    appUtils.simpleIntentFinish(context, MainActivity.class,Bundle.EMPTY);
                }else if (!TextUtils.isEmpty(appUtils.getStringPrefrences(SplashScreen.this, constatnts.SH_APPPREFSCHOOLLIST, constatnts.SH_SCHOOLList))&&TextUtils.isEmpty(appUtils.getStringPrefrences(SplashScreen.this, constatnts.SH_APPPREF, constatnts.SH_USERID))){
                    appUtils.simpleIntentFinish(context, LoginActivity.class,Bundle.EMPTY);
                }
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                appUtils.showToast(context, getString(R.string.Permission_Denied)+"\n" + deniedPermissions.toString());
                finish();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getString(R.string.Please_give_permission_for_app_functionality))
                .setDeniedMessage(getString(R.string.If_you_reject_permission_you_can_not_use_this_service)+"\n\n"+getString(R.string.Please_turn_on_permissions_at))
                .setGotoSettingButtonText(getString(R.string.settings))
                .setPermissions(Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_CONTACTS,Manifest.permission.READ_CALL_LOG,Manifest.permission.CALL_PHONE
                        ,Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WAKE_LOCK)
                .check();

    }

}