package com.vclassrooms.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.vclassrooms.Activity.LoginActivity;
import com.vclassrooms.Activity.MainActivity;
import com.vclassrooms.Adapter.TeacherDirectoryAdapter;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.ChangePasswordRequest;
import com.vclassrooms.Entity.CommonSuccessResponse;
import com.vclassrooms.Entity.ProfileResponse;
import com.vclassrooms.Entity.StudentListItem;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 03,July,2020
 */
public class ChangePasswordFragment extends Fragment {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId,strUsername;
    @BindView(R.id.linear_tab)
    LinearLayout linear_tab;
    @BindView(R.id.txtParentsChngPass)
    TextView txtParentsChngPass;
    @BindView(R.id.txtTittle)
    TextView txtTittle;
    @BindView(R.id.txtChildChngPass)
    TextView txtChildChngPass;
    @BindView(R.id.parentImg)
    ImageView parentImg;
    @BindView(R.id.childImg)
    ImageView childImg;
    @BindView(R.id.viewparent)
    View viewparent;
    @BindView(R.id.viewchild)
    View viewchild;
    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edt_oldPassword)
    EditText edt_oldPassword;
    @BindView(R.id.edtNewPassword)
    EditText edtNewPassword;
    @BindView(R.id.edtConfPassword)
    EditText edtConfPassword;
    @BindView(R.id.txtspinnerHeader)
    TextView txtspinnerHeader;
    @BindView(R.id.Linear_spinnerChild)
    LinearLayout Linear_spinnerChild;
    @BindView(R.id.spinnerChild)
    Spinner spinnerChild;
    List<StudentListItem> studentListItemArrayList = new ArrayList<>();
    String strChildId,strChildRoleId="2";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.change_password_fragment,null);
        ButterKnife.bind(this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
        return mview;
    }
    private void NoOfChildrenRecyclerviews() {
        studentListItemArrayList.clear();
        try {
            String strId = "", strFirstName = "",  strPhotoPath = "";
            JSONArray jsonArray = new JSONArray(appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ALL_CHILD_DETAILS));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                strId = jsonObject.getString("id");
                strFirstName = jsonObject.getString("firstName");
                strPhotoPath = jsonObject.getString("photoPath");
                StudentListItem studentListItem = new StudentListItem();
                studentListItem.setId(Integer.parseInt(strId));
                studentListItem.setFirstName(strFirstName);
                studentListItem.setPhotoPath(strPhotoPath);
                studentListItemArrayList.add(studentListItem);
                ArrayAdapter<StudentListItem> adapter = new ArrayAdapter<StudentListItem>(getContext().getApplicationContext(), android.R.layout.simple_spinner_item,studentListItemArrayList);
                spinnerChild.setAdapter(adapter);
                strChildId= String.valueOf(studentListItemArrayList.get(0).getId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        spinnerChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                strChildId= String.valueOf(studentListItemArrayList.get(i).getId());
            }
        });
    }

    private void init() {
        strAuth=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_FCM);
        strRoleid=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_USERTYPEID);
        strUserId=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_USERID);
        strSchoolId=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_SCHOOLID);
        strAcademicId=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_ACADEMICYEAR);
        strUsername=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_USERNAME);
        edtUsername.setText(strUsername);
        if(strRoleid.contentEquals("1")){
            linear_tab.setVisibility(View.VISIBLE);
            txtspinnerHeader.setVisibility(View.VISIBLE);
            Linear_spinnerChild.setVisibility(View.VISIBLE);
        }else {
            linear_tab.setVisibility(View.GONE);
            txtspinnerHeader.setVisibility(View.GONE);
            Linear_spinnerChild.setVisibility(View.GONE);
        }
        txtspinnerHeader.setVisibility(View.GONE);
        Linear_spinnerChild.setVisibility(View.GONE);
        if(strRoleid.contentEquals("1")){
            txtTittle.setText("Parent");
        }else  if(strRoleid.contentEquals("2")){
            txtTittle.setText("Student");
        }else  if(strRoleid.contentEquals("3")){
            txtTittle.setText("Teacher");
        }else  if(strRoleid.contentEquals("5")){
            txtTittle.setText("Admin");
        }
    }
    @OnClick({R.id.btnSubmit,R.id.relparent,R.id.relChildt})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnSubmit:
                if(isValid()){
                    UpdatePasswordApi();
                }
                break;
            case R.id.relparent:
                txtspinnerHeader.setVisibility(View.GONE);
                Linear_spinnerChild.setVisibility(View.GONE);
                txtTittle.setText("Parent");
                break;
            case R.id.relChildt:
                txtTittle.setText("Student");
                txtspinnerHeader.setVisibility(View.VISIBLE);
                Linear_spinnerChild.setVisibility(View.VISIBLE);
                if(studentListItemArrayList.size()<0){
                    NoOfChildrenRecyclerviews();
                }
                break;
        }
    }
    public boolean isValid(){
        if(TextUtils.isEmpty(edtUsername.getText().toString())){
            appUtils.showToast(mview.getContext(),"Please enter valid Username");
            return false;
        }
        if(TextUtils.isEmpty(edtNewPassword.getText().toString())){
            appUtils.showToast(mview.getContext(),"Please enter valid New Password");
            return false;
        }
        if(TextUtils.isEmpty(edtConfPassword.getText().toString())){
            appUtils.showToast(mview.getContext(),"Password does not match");
            return false;
        }
        if(!TextUtils.isEmpty(edtConfPassword.getText().toString())&&!TextUtils.isEmpty(edtNewPassword.getText().toString())){
            if(!edtConfPassword.getText().toString().contentEquals(edtNewPassword.getText().toString())){
                appUtils.showToast(mview.getContext(),"Password does not match");
                return false;
            }
        }
            if(TextUtils.isEmpty(edt_oldPassword.getText().toString())){
                appUtils.showToast(mview.getContext(),"Password is incorrect");
                return false;
            }

        return true;
    }

    private void UpdatePasswordApi() {
        try {
            String command="ChangePassword";
            ChangePasswordRequest changePasswordRequest=new ChangePasswordRequest();
            changePasswordRequest.setAcademic_Id(Integer.parseInt(strAcademicId));
            changePasswordRequest.setSchool_Id(Integer.parseInt(strSchoolId));
            changePasswordRequest.setNew_Password(edtNewPassword.getText().toString());
            changePasswordRequest.setOld_Password(edt_oldPassword.getText().toString());
            if(txtspinnerHeader.getVisibility()==View.VISIBLE){
                changePasswordRequest.setRole_Id(Integer.parseInt(strChildRoleId));
                changePasswordRequest.setUser_Id(Integer.parseInt(strChildId));
            }else {
                changePasswordRequest.setRole_Id(Integer.parseInt(strRoleid));
                changePasswordRequest.setUser_Id(Integer.parseInt(strUserId));
            }

            changePasswordRequest.setUser_Name(edtUsername.getText().toString());
            Log.e("changepassword",new Gson().toJson(changePasswordRequest));
            appUtils.showProgressbar(context);
            Call<CommonSuccessResponse> call = ApiService.buildService(context).updatePassword(strAuth,command,changePasswordRequest);
            call.enqueue(new Callback<CommonSuccessResponse>() {
                @Override
                public void onResponse(Call<CommonSuccessResponse> call, Response<CommonSuccessResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if(response.body().getData().getPasswordDetails()!=null){
                                appUtils.showToast(context,"Your password has been change successfully");
                                appUtils.removeAllPrefrences(mview.getContext(), constatnts.SH_APPPREF);
                                Intent intent = new Intent(mview.getContext(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                getActivity().finish();
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
