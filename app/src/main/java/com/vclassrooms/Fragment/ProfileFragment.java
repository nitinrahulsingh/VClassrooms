package com.vclassrooms.Fragment;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.vclassrooms.Activity.LoginActivity;
import com.vclassrooms.Activity.SplashScreen;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Common.ImagePickerActivity;
import com.vclassrooms.Entity.ProfileDetailInsertResponse;
import com.vclassrooms.Entity.ProfileResponse;
import com.vclassrooms.Entity.SchoolResponse;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

/**
 * Created by Rahul on 29,June,2020
 */
public class ProfileFragment extends Fragment {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.StdName)
    TextView StdName;
    @BindView(R.id.gendertv)
    TextView gendertv;
    @BindView(R.id.MobileNotv)
    TextView MobileNotv;
    @BindView(R.id.EmailIdtv)
    TextView EmailIdtv;
    @BindView(R.id.DOBtv)
    TextView DOBtv;
    @BindView(R.id.addresstv)
    TextView addresstv;
    @BindView(R.id.profile_img)
    CircleImageView profile_img;
    String picturePath;
    public static final int REQUEST_IMAGE = 200;
    String strName="",strEmailId="",strMobileno="",strAddress="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.fragment_profile,null);
        ButterKnife.bind(this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
        return mview;
    }

    private void init() {
        strAuth=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_FCM);
        strRoleid=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_USERTYPEID);
        strUserId=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_USERID);
        strSchoolId=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_SCHOOLID);
        strAcademicId=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_ACADEMICYEAR);
        getProfileDetailApi();
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickerOptions();
            }
        });
    }
    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(context,"en", new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(context, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(context, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
       startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {

                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                    Uri contentUri  = Uri.parse(MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "Title", null));
                    Cursor cursor = null;
                    try {


                        String[] proj = { MediaStore.Images.Media.DATA };
                        cursor =getContext().getContentResolver().query(contentUri, proj, null, null, null);
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        picturePath= String.valueOf(cursor.getString(column_index));
                        Log.d("Picture Path", picturePath);
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                    }
                    Bundle bundle =new Bundle();
                    bundle.putString("ImagePath",picturePath);

                    // loading profile image from local cache
                    //loadProfile(uri.toString());
                    onUploadImageApi(picturePath);
                    setProfileImage(picturePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void getProfileDetailApi() {
        try {
            String command="";
            if(strRoleid.contentEquals("2")){
                command=constatnts.GetStudentProfile;
            }else  if(strRoleid.contentEquals("3")){
                command=constatnts.GetTeacherProfile;
            }  else  if(strRoleid.contentEquals("5")){
                command=constatnts.GetAdminProfile;
            }
            appUtils.showProgressbar(context);
            Call<ProfileResponse> call = ApiService.buildService(context).getProfileDetails(strAuth,command,strRoleid,strUserId,strSchoolId,strAcademicId);
            call.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if(response.body().getData().getProfileDetails()!=null){
                               setValue(response.body().getData().getProfileDetails());
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
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    //Add gender
    private void setValue(List<ProfileResponse.ProfileDetail> profileDetails) {
        name.setText(profileDetails.get(0).getFirstName());
        strName=profileDetails.get(0).getFirstName();
        if(strRoleid.contentEquals("5")){
            StdName.setText(profileDetails.get(0).getDesignation());
        }else {
            if(!TextUtils.isEmpty(profileDetails.get(0).getStandardName())){
                StdName.setText(profileDetails.get(0).getStandardName()+" ("+profileDetails.get(0).getDivisionName()+")");
            }else {
                StdName.setText("-");
            }

        }

        gendertv.setText("-");
        MobileNotv.setText(profileDetails.get(0).getMobileNo());
        strMobileno=profileDetails.get(0).getMobileNo();
        strEmailId=profileDetails.get(0).getEmailId();
        if(TextUtils.isEmpty(profileDetails.get(0).getEmailId())){
            EmailIdtv.setText("-");
        }else {
            EmailIdtv.setText(profileDetails.get(0).getEmailId());
        }
        if(TextUtils.isEmpty(profileDetails.get(0).getAddress())){
            addresstv.setText("-");
        }else {
            addresstv.setText(profileDetails.get(0).getAddress());
        }
        strAddress=profileDetails.get(0).getAddress();
        setProfileImage(profileDetails.get(0).getImageURL());
    }



    private void onUploadImageApi(String ImagePath) {
        String command="";
        if(strRoleid.contentEquals("2")){
            command="InsertStudentProfile";
        }else  if(strRoleid.contentEquals("3")){
            command="InsertTeacherProfile";
        }  else  if(strRoleid.contentEquals("5")){
            command="InsertAdminProfile";
        }
        appUtils.showProgressbar(context);

        String extension = ImagePath.substring(ImagePath.lastIndexOf("."));
        File file = new File(ImagePath);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/*"),
                        file
                );
        String imagename = file.getName();
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", imagename, requestFile);
        try {
            String filename = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss_SS", Locale.getDefault()).format(new Date());
            extension = filename + extension;
            Call<ProfileDetailInsertResponse> call = ApiService.buildService(context).onProfileChanges(body,strAuth,command,strRoleid,strUserId,strSchoolId,strAcademicId,extension
            ,strName,strMobileno,strEmailId,strAddress);
            call.enqueue(new Callback<ProfileDetailInsertResponse>() {
                @Override
                public void onResponse(Call<ProfileDetailInsertResponse> call, Response<ProfileDetailInsertResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if(response.body().getData().getProfileDetails()!=null){
                                appUtils.showToast(context, getString(R.string.profile_updated));
                            }else {
                                appUtils.showToast(context, getString(R.string.error_message));
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
                public void onFailure(Call<ProfileDetailInsertResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
    public void setProfileImage(String path){
        if(path!=null) {
            Glide.with(context)
                    .load(path)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            //on load failed
                            profile_img.setVisibility(View.VISIBLE);
                            profile_img.setBackgroundResource(R.drawable.profile);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            //on load success

                            profile_img.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(profile_img);
        }
    }
}
