package com.vclassrooms.BottomSheetDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.AddOnlineLecResponse;
import com.vclassrooms.Entity.CommonSuccessResponse;
import com.vclassrooms.Entity.StandardDivisionResponse;
import com.vclassrooms.Entity.SubjectDetailsResponse;
import com.vclassrooms.Interface.CommonInterface;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;
import com.vclassrooms.SearchableSpinner.SearchableSpinner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import static com.vclassrooms.photopicker.utils.FileUtils.getPath;

/**
 * Created by Rahul on 25,July,2020
 */
public class AddOnlineLecBottomSheet extends BottomSheetDialogFragment {
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
    @BindView(R.id.file_name)
    TextView file_name;
    @BindView(R.id.spinner2)
    SearchableSpinner subjtSpinner;
    @BindView(R.id.fileformat_txt)
    TextView fileformat_txt;
    String strMediaId;
    CommonInterface commonInterface;
    String strStandardId="",strFileSize="",strSubjectId,strDivisionId="";
    List<StandardDivisionResponse.Division> mStandardDataList = new ArrayList<>();
    String FilePath="";
    List<SubjectDetailsResponse.Subject> subjectList = new ArrayList<>();
    public AddOnlineLecBottomSheet newInstance(Fragment fragment) {
        commonInterface = (CommonInterface) fragment;
        return new AddOnlineLecBottomSheet();
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.add_e_book_bottomsheet, container, false);
        ButterKnife.bind(this, mView);
        context = getActivity();
        appUtils = new AppUtils();
        constatnts = new Constatnts();
        init();
        return mView;
    }

    private void init() {
        fileformat_txt.setText("Only video can be uploaded");
        file_name.setVisibility(View.GONE);
        subjtSpinner.setVisibility(View.VISIBLE);
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
        heading_tv.setText("Online Lec");
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

    @OnClick({R.id.btnSubmit, R.id.linear_attach, R.id.minimize_relative})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnSubmit:
                if(isValid()){
                    onUploadImageApi(FilePath);
                }
                break;
            case R.id.linear_attach:
                browseDocuments();
                break;
            case R.id.minimize_relative:
                dismiss();
                break;
        }
    }
    private void browseDocuments() {


        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        intent.setType("video/*");
        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), 1);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }
                    FilePath = getPath(getContext(), data.getData());
                    file_name.setVisibility(View.VISIBLE);
                    appUtils.setText(file_name,displayName);
                    File f = new File(FilePath);
                    long size = f.length();
                    strFileSize=appUtils.formatSize(size);
                }
            }
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
    public boolean isValid(){
        if(TextUtils.isEmpty(et_comment.getText().toString())){
            appUtils.showToast(context,"Please enter detail");
            return false;
        }
        if(TextUtils.isEmpty(et_tittle.getText().toString())){
            appUtils.showToast(context,"Please enter detail");
            return false;
        }
        if(FilePath==null && FilePath.isEmpty()){
            appUtils.showToast(context,"Please add File");
            return false;
        }
        if(strStandardId==null && strStandardId.isEmpty()){
            appUtils.showToast(context,"Please select standard");
            return false;
        }if(strSubjectId==null && strSubjectId.isEmpty()){
            appUtils.showToast(context,"Please select Subject");
            return false;
        }
        return true;
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
    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MultipartBody.FORM, descriptionString);
    }
    private void onUploadImageApi(String filedetail) {
        String command="";
        command="Insert";
        appUtils.showProgressbar(context);

        String extension = filedetail.substring(filedetail.lastIndexOf("."));
        File file = new File(filedetail);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/*"),
                        file
                );
        String imagename = file.getName();
        String filename = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss_SS", Locale.getDefault()).format(new Date());
        extension = filename + extension;

        //RequestBody data = createPartFromString("" + new Gson().toJson(onlineLectureEnum));
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", imagename, requestFile);
        try {

            Call<AddOnlineLecResponse> call = ApiService.buildService(context).onUploadLec(body,strAuth,command,strAcademicId,strDivisionId,strAuth
                    ,extension,strUserId,et_comment.getText().toString(),et_tittle.getText().toString(),strRoleid,strSchoolId,strStandardId,strSubjectId,
              strUserId,extension);
            call.enqueue(new Callback<AddOnlineLecResponse>() {
                @Override
                public void onResponse(Call<AddOnlineLecResponse> call, Response<AddOnlineLecResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if(response.body().getData().getOnlineLecture()!=null && response.body().getData().getOnlineLecture().size()>0){
                                if(response.body().getData().getOnlineLecture().get(0).getColumn1().contentEquals("Success")){
                                    appUtils.showToast(context, getString(R.string.success));
                                    commonInterface.OnCommonInterfaceClick(0,true);
                                    dismiss();
                                }else {
                                    appUtils.showToast(context, getString(R.string.fail_message));
                                }

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
                public void onFailure(Call<AddOnlineLecResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
}