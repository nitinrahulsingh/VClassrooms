package com.vclassrooms.BottomSheetDialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vclassrooms.Adapter.AttachmentAdapter;
import com.vclassrooms.Adapter.AttendanceStandardListAdapter;
import com.vclassrooms.Adapter.StdListBottomAdapter;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.AssignmentInsertResponse;
import com.vclassrooms.Entity.CommonSuccessResponse;
import com.vclassrooms.Entity.StandardDivisionResponse;
import com.vclassrooms.Entity.SubjectDetailsResponse;
import com.vclassrooms.Entity.UploadImageDetails;
import com.vclassrooms.Interface.CommonInterface;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;
import com.vclassrooms.SearchableSpinner.SearchableSpinner;
import com.vclassrooms.photopicker.activity.PickImageActivity;

import java.io.File;
import java.io.IOException;
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
 * Created by Rahul on 11,July,2020
 */
public class AddAssignmentBottomSheet extends BottomSheetDialogFragment {
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
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview_attachment;
    @BindView(R.id.spinner2_cardview)
    CardView spinner2_cardview;
    @BindView(R.id.spinner2)
    SearchableSpinner subjtSpinner;
    private ArrayList<String> pathList = new ArrayList<>();
    private ArrayList<UploadImageDetails> uploadImageDetailslist = new ArrayList<>();
    List<StandardDivisionResponse.Division> mStandardDataList = new ArrayList<>();
    List<SubjectDetailsResponse.Subject> subjectList = new ArrayList<>();
    String strStandardId,strSubjectId,strDivisionId;
    CommonInterface commonInterface;
    String strMediaId;
    public AddAssignmentBottomSheet newInstance(Fragment fragment) {
        commonInterface = (CommonInterface) fragment;
        return new AddAssignmentBottomSheet();
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
        spinner2_cardview.setVisibility(View.VISIBLE);
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
        heading_tv.setText("Assignment");
        stdDiv_spinner.setHint("Select Standard");
        subjtSpinner.setHint("Select Subject");

        stdDiv_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strStandardId=mStandardDataList.get(position).getStandardId().toString();
                strDivisionId=mStandardDataList.get(position).getDivisionId().toString();
                getSubjectApi();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        subjtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strSubjectId=subjectList.get(position).getSubjectId().toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        getStandardDetailApi();
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
//        if(uploadImageDetailslist==null && uploadImageDetailslist.size()==0){
//            appUtils.showToast(context,"Please add File");
//            return false;
//        }
        if(strStandardId==null && strStandardId.isEmpty()){
            appUtils.showToast(context,"Please select standard");
            return false;
        }if(strSubjectId==null && strSubjectId.isEmpty()){
            appUtils.showToast(context,"Please select Subject");
            return false;
        }
        return true;
    }
    @OnClick({R.id.btnSubmit, R.id.linear_attach, R.id.minimize_relative})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnSubmit:
                if(isValid()){
                    onUploadImageApi();
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
    private void onUploadAllImageApi(String mediaId) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        if (uploadImageDetailslist != null) {
            // create part for file (photo, video, ...)
            for (int i = 0; i < uploadImageDetailslist.size(); i++) {
                parts.add(prepareFilePart(uploadImageDetailslist.get(i).getImageName(), Uri.parse(uploadImageDetailslist.get(i).getFilePath()),i));
            }
        }
        try {
            appUtils.showProgressbar(context);
            Call<CommonSuccessResponse> call = ApiService.buildService(context).onUploadImages(strAuth,parts,"insert",strSchoolId,strAcademicId,strUserId,"2",mediaId);
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
    private void onUploadImageApi() {
        List<MultipartBody.Part> parts = new ArrayList<>();
        if (uploadImageDetailslist != null && uploadImageDetailslist.size()>0) {
            for (int i = 0; i < uploadImageDetailslist.size(); i++) {
                parts.add(prepareFilePart(uploadImageDetailslist.get(i).getImageName(), Uri.parse(uploadImageDetailslist.get(i).getFilePath()),i));
            }
        }
        try {
            appUtils.showProgressbar(context);
            Call<AssignmentInsertResponse> call = ApiService.buildService(context).onUploadAssignmentDetails(strAuth,"Insert",strStandardId,strDivisionId,
                    strSubjectId,strUserId,et_tittle.getText().toString(),et_comment.getText().toString(),strAcademicId,strSchoolId,strUserId);
            call.enqueue(new Callback<AssignmentInsertResponse>() {
                @Override
                public void onResponse(Call<AssignmentInsertResponse> call, Response<AssignmentInsertResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.code() == 401) {
                    } else {
                        if (response.body() != null) {
                            if (response.body().getStatusCode().equals(0)) {
                                strMediaId=response.body().getData().getAssignmentDetails().get(0).getColumn1();
                                onUploadAllImageApi(response.body().getData().getAssignmentDetails().get(0).getColumn1());
                            } else {
                                appUtils.showToast(context, getString(R.string.error_message));
                            }
                        } else {
                            appUtils.showToast(context, getString(R.string.error_message));
                        }
                    }

                }

                @Override
                public void onFailure(Call<AssignmentInsertResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
    private void getStandardDetailApi() {
        try {
            appUtils.showProgressbar(context);
            Call<StandardDivisionResponse> call = ApiService.buildService(context).getStandardDivisionList(strAuth, "GetStandardDivision", strRoleid, strUserId, strSchoolId, strAcademicId, "0");
            call.enqueue(new Callback<StandardDivisionResponse>() {
                @Override
                public void onResponse(Call<StandardDivisionResponse> call, Response<StandardDivisionResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if (response.body().getData().getDivision() != null) {
                                mStandardDataList = response.body().getData().getDivision();
                                ArrayAdapter<StandardDivisionResponse.Division> adapter =
                                        new ArrayAdapter<StandardDivisionResponse.Division>(context, android.R.layout.simple_spinner_item, mStandardDataList);
                                stdDiv_spinner.setAdapter(adapter);
                                strStandardId=mStandardDataList.get(0).getStandardId().toString();
                                strDivisionId=mStandardDataList.get(0).getDivisionId().toString();
                            } else {
                                appUtils.showToast(context, getString(R.string.no_data));
                            }
                        } else if (response.body().getStatusCode().equals(1)) {
                            appUtils.showToast(context, getString(R.string.error_message));
                        } else if (response.body().getStatusCode().equals(2)) {
                            appUtils.showToast(context, getString(R.string.unauthorize_message));
                        }
                    } else {
                        appUtils.showToast(context, getString(R.string.error_message));
                    }

                }

                @Override
                public void onFailure(Call<StandardDivisionResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
    private void getSubjectApi() {
        try {
            appUtils.showProgressbar(context);
            Call<SubjectDetailsResponse> call = ApiService.buildService(context).getSubjectList(strAuth, "getSubject", strRoleid, strUserId, strSchoolId, strAcademicId, strStandardId);
            call.enqueue(new Callback<SubjectDetailsResponse>() {
                @Override
                public void onResponse(Call<SubjectDetailsResponse> call, Response<SubjectDetailsResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if (response.body().getData().getSubject() != null && response.body().getData().getSubject().size()>0) {
                                subjectList = response.body().getData().getSubject();
                                ArrayAdapter<SubjectDetailsResponse.Subject> adapter =
                                        new ArrayAdapter<SubjectDetailsResponse.Subject>(context, android.R.layout.simple_spinner_item, subjectList);
                                subjtSpinner.setAdapter(adapter);
                                strSubjectId=subjectList.get(0).getSubjectId().toString();
                            } else {
                                appUtils.showToast(context, getString(R.string.no_data));
                            }
                        } else if (response.body().getStatusCode().equals(1)) {
                            appUtils.showToast(context, getString(R.string.error_message));
                        } else if (response.body().getStatusCode().equals(2)) {
                            appUtils.showToast(context, getString(R.string.unauthorize_message));
                        }
                    } else {
                        appUtils.showToast(context, getString(R.string.error_message));
                    }

                }

                @Override
                public void onFailure(Call<SubjectDetailsResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
