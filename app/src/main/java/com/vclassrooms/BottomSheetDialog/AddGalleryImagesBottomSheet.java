package com.vclassrooms.BottomSheetDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.vclassrooms.Adapter.AttachmentAdapter;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.AddGalleryResponse;
import com.vclassrooms.Entity.CommonSuccessResponse;
import com.vclassrooms.Entity.MarkAttendanceEnum;
import com.vclassrooms.Entity.StandardDivisionResponse;
import com.vclassrooms.Entity.UploadImageDetails;
import com.vclassrooms.Interface.CommonInterface;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;
import com.vclassrooms.SearchableSpinner.SearchableSpinner;
import com.vclassrooms.photopicker.activity.PickImageActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Rahul on 19,July,2020
 */
public class AddGalleryImagesBottomSheet extends BottomSheetDialogFragment {
    View mView;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth, strRoleid, strUserId, strSchoolId, strAcademicId;
    @BindView(R.id.heading_tv)
    TextView heading_tv;
    @BindView(R.id.et_tittle)
    EditText et_tittle;
    @BindView(R.id.spinner)
    SearchableSpinner stdDiv_spinner;
    @BindView(R.id.et_comment)
    EditText et_comment;
    @BindView(R.id.spinner_cardview)
    CardView spinner_cardview;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview_attachment;
    private ArrayList<String> pathList = new ArrayList<>();
    private ArrayList<UploadImageDetails> uploadImageDetailslist = new ArrayList<>();
    String strMediaId;
    CommonInterface commonInterface;
    public AddGalleryImagesBottomSheet newInstance(Fragment fragment) {
        commonInterface = (CommonInterface) fragment;
        return new AddGalleryImagesBottomSheet();
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.add_assignment_bottomsheet, container, false);
        ButterKnife.bind(this, mView);
        context = getActivity();
        appUtils = new AppUtils();
        constatnts = new Constatnts();
        init();
        return mView;
    }

    private void init() {
        spinner_cardview.setVisibility(View.GONE);
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
        heading_tv.setText("Gallery");

    }

    @OnClick({R.id.btnSubmit, R.id.linear_attach, R.id.minimize_relative})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnSubmit:
                if(isValid()){
                    AddGalleryDetailApi();
                }
                break;
            case R.id.linear_attach:
                Intent mIntent = new Intent(context, PickImageActivity.class);
                mIntent.putExtra(PickImageActivity.KEY_LIMIT_MAX_IMAGE, constatnts.MAX_LIMIT_IMAGE);
                mIntent.putExtra(PickImageActivity.KEY_LIMIT_MIN_IMAGE, 1);
                startActivityForResult(mIntent, PickImageActivity.PICKER_REQUEST_CODE);
                break;
            case R.id.minimize_relative:
                dismiss();
                break;
        }
    }
    public boolean isValid(){
        if(TextUtils.isEmpty(et_comment.getText().toString())){
            appUtils.showToast(context,"Please enter detail");
            return false;
        }
        if(TextUtils.isEmpty(et_tittle.getText().toString())){
            appUtils.showToast(context,"Please enter detail");
            return false;
        }
        if(uploadImageDetailslist.size()<0){
            appUtils.showToast(context,"Please add image");
            return false;
        }
        return true;
    }
    private void AddGalleryDetailApi() {
        try {
            appUtils.showProgressbar(context);
            Call<AddGalleryResponse> call = ApiService.buildService(context).onAddGalleryDetail(strAuth,"Insert",et_tittle.getText().toString(),et_comment.getText().toString()
            ,strSchoolId,strAcademicId,strUserId);
            call.enqueue(new Callback<AddGalleryResponse>() {
                @Override
                public void onResponse(Call<AddGalleryResponse> call, Response<AddGalleryResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if(response.body().getData().getGalleryDetails()!=null && response.body().getData().getGalleryDetails().size()>0){
                                strMediaId=response.body().getData().getGalleryDetails().get(0).getColumn1();
                                onUploadImageApi(response.body().getData().getGalleryDetails().get(0).getColumn1());
                            }else {
                                appUtils.showToast(context, getString(R.string.fail_message));
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
                public void onFailure(Call<AddGalleryResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (resultCode == -1 && requestCode == PickImageActivity.PICKER_REQUEST_CODE) {
            this.pathList = intent.getExtras().getStringArrayList(PickImageActivity.KEY_DATA_RESULT);
            if (this.pathList != null && !this.pathList.isEmpty()) {
                StringBuilder sb = new StringBuilder("");
                for (int i = 0; i < pathList.size(); i++) {
                    UploadImageDetails uploadImageDetail = new UploadImageDetails( pathList.get(i), "image" + (i + 1));
                    uploadImageDetailslist.add(uploadImageDetail);
                }
                recyclerview_attachment.setVisibility(View.VISIBLE);
                AttachmentAdapter stdBottomAdapter = new AttachmentAdapter(getContext(), uploadImageDetailslist);
                recyclerview_attachment.setLayoutManager(new GridLayoutManager(getContext(), 3));
                recyclerview_attachment.setAdapter(stdBottomAdapter);
            }
        }
    }
    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MultipartBody.FORM, descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri,int position) {

        MultipartBody.Part part;

        try {
            File file = new File(String.valueOf(fileUri));
            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse("image/*"),
                            file
                    );
            // MultipartBody.Part is used to send also the actual file name
            part = MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
        } catch (Exception e) {
            e.printStackTrace();
            part = null;
        }

        return part;


    }
    private void onUploadImageApi(String mediaId) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        if (uploadImageDetailslist != null) {
            // create part for file (photo, video, ...)
            for (int i = 0; i < uploadImageDetailslist.size(); i++) {
                parts.add(prepareFilePart(uploadImageDetailslist.get(i).getImageName(), Uri.parse(uploadImageDetailslist.get(i).getFilePath()),i));
            }
        }
        try {
            appUtils.showProgressbar(context);
            Call<CommonSuccessResponse> call = ApiService.buildService(context).onUploadImages(strAuth,parts,"insert",strSchoolId,strAcademicId,strUserId,"3",mediaId);
            call.enqueue(new Callback<CommonSuccessResponse>() {
                @Override
                public void onResponse(Call<CommonSuccessResponse> call, Response<CommonSuccessResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.code() == 401) {
                    } else {
                        if (response.body() != null) {
                            if (response.body().getStatusCode().equals(0)) {
                                appUtils.showToast(context, getString(R.string.success));
                                commonInterface.OnCommonInterfaceClick(0,true);
                                dismiss();
                            } else {
                                appUtils.showToast(context, getString(R.string.error_message));
                            }
                        } else {
                            appUtils.showToast(context, getString(R.string.error_message));
                        }
                    }

                }

                @Override
                public void onFailure(Call<CommonSuccessResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
