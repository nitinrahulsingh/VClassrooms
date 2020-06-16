package com.vclassrooms.Activity;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.R;

import java.util.ArrayList;

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
                    appUtils.simpleIntentFinish(context, LoginActivity.class,Bundle.EMPTY);


                }catch (Exception e)
                {
                    e.getMessage();
                }


            }
        }, 2600);

    }
    public void TedPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                if(appUtils.isNetworkAvailableWithToast(context)) {
                    appUtils.simpleIntentFinish(context, LoginActivity.class, Bundle.EMPTY);
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
                        ,Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

    }

}