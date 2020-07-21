package com.vclassrooms.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Adapter.CommonAllParentChildAdapter;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 02,July,2020
 */
public class ParentProfileFragment extends Fragment {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    CommonAllParentChildAdapter assignAdapterChildren;

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
    @BindView(R.id.studentRecHorizontal)
    RecyclerView studentRecHorizontal;
    List<StudentListItem> studentListItemArrayList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.parent_profile_fragment,null);
        ButterKnife.bind(this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        NoOfChildrenRecyclerviews(0);
        init();
        return mview;
    }
    private void NoOfChildrenRecyclerviews(int iSelected) {
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

            }
            strUserId= String.valueOf(studentListItemArrayList.get(0).getId());
            SetNoOfChildrenRecyclerview(iSelected);
            getProfileDetailApi();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void init() {
        strAuth=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_FCM);
        strRoleid="2";
       // strUserId=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_USERID);
        strSchoolId=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_SCHOOLID);
        strAcademicId=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_ACADEMICYEAR);
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
            Call<ProfileResponse> call = ApiService.buildService(context).getProfileDetails(strAuth,constatnts.GetStudentProfile,strRoleid,strUserId,strSchoolId,strAcademicId);
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

    }

    private void SetNoOfChildrenRecyclerview(int iSelected) {
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        studentRecHorizontal.setLayoutManager(horizontalLayoutManagaer);

        if (iSelected == 0) {
            if (studentListItemArrayList.size() == 1) {

                assignAdapterChildren = new CommonAllParentChildAdapter(context, studentListItemArrayList, 0);//iSelected
                studentRecHorizontal.setAdapter(assignAdapterChildren);
                studentRecHorizontal.setNestedScrollingEnabled(false);

            } else if (studentListItemArrayList.size() == 2) {

                studentRecHorizontal.setLayoutManager(new GridLayoutManager(context, 2));
                assignAdapterChildren = new CommonAllParentChildAdapter(context, studentListItemArrayList, 0 );//iSelected
                studentRecHorizontal.setAdapter(assignAdapterChildren);
                studentRecHorizontal.setNestedScrollingEnabled(false);


            } else if (studentListItemArrayList.size() >= 3) {

                assignAdapterChildren = new CommonAllParentChildAdapter(context, studentListItemArrayList,  0);//1
                studentRecHorizontal.setAdapter(assignAdapterChildren);
                studentRecHorizontal.setNestedScrollingEnabled(false);
            }
        } else if (iSelected == 1) {

            horizontalLayoutManagaer.scrollToPositionWithOffset(iSelected, studentListItemArrayList.size());
            assignAdapterChildren = new CommonAllParentChildAdapter(context, studentListItemArrayList, 0);//iSelected
            studentRecHorizontal.setAdapter(assignAdapterChildren);
            studentRecHorizontal.setNestedScrollingEnabled(false);

        } else if (iSelected > 2 && iSelected < 1000) {


            horizontalLayoutManagaer.scrollToPositionWithOffset(iSelected, studentListItemArrayList.size());

            assignAdapterChildren = new CommonAllParentChildAdapter(context, studentListItemArrayList, 0);//iSelected
            studentRecHorizontal.setAdapter(assignAdapterChildren);
            studentRecHorizontal.setNestedScrollingEnabled(false);

        } else if (iSelected == 1000) {

            assignAdapterChildren = new CommonAllParentChildAdapter(context, studentListItemArrayList,  0);
            studentRecHorizontal.setAdapter(assignAdapterChildren);
            studentRecHorizontal.setNestedScrollingEnabled(false);

        } else {

            assignAdapterChildren = new CommonAllParentChildAdapter(context, studentListItemArrayList,  0);//iSelected
            studentRecHorizontal.setAdapter(assignAdapterChildren);
            studentRecHorizontal.setNestedScrollingEnabled(false);

        }
        studentRecHorizontal.setHasFixedSize(true);
        assignAdapterChildren.setOnClickListener(new CommonAllParentChildAdapter.ChildDataClick() {
            @Override
            public void onChildDataClick(int id, String name) {
                strUserId= String.valueOf(id);
                getProfileDetailApi();
            }
        });

    }

}