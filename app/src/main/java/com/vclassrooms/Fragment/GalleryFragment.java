package com.vclassrooms.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vclassrooms.Adapter.AssignmentAdapter;
import com.vclassrooms.Adapter.GalleryAdapter;
import com.vclassrooms.Adapter.HolidayAdapter;
import com.vclassrooms.BottomSheetDialog.AddAssignmentBottomSheet;
import com.vclassrooms.BottomSheetDialog.AddGalleryImagesBottomSheet;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.GalleryDetailResponse;
import com.vclassrooms.Interface.CommonInterface;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 19,July,2020
 */
public class GalleryFragment extends Fragment implements CommonInterface {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    @BindView(R.id.add_images)
    FloatingActionButton add_images;
    @BindView(R.id.txt_no_data)
    TextView txt_no_data;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    List<GalleryDetailResponse.GalleryDetails1> galleryDetailResponses = new ArrayList<GalleryDetailResponse.GalleryDetails1>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.gallery_fragment,null);
        ButterKnife.bind(GalleryFragment.this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
        getImagesApi();
        return mview;
    }
    private void getImagesApi() {
        try {
            appUtils.showProgressbar(context);
            Call<GalleryDetailResponse> call = ApiService.buildService(context).getGalleryDetails(strAuth,"SelectFolder",strSchoolId,strAcademicId,"0");
            call.enqueue(new Callback<GalleryDetailResponse>() {
                @Override
                public void onResponse(Call<GalleryDetailResponse> call, Response<GalleryDetailResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode()==0) {
                            if(response.body().getData().getGalleryDetails1()!=null && response.body().getData().getGalleryDetails1().size()>0 ){
                                txt_no_data.setVisibility(View.GONE);
                                GalleryAdapter galleryAdapter = new GalleryAdapter(getActivity(),response.body().getData().getGalleryDetails1());
                                recyclerView.setAdapter(galleryAdapter);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setNestedScrollingEnabled(false);
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
    private void init() {
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
        recyclerView=mview.findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
    }
    @OnClick({R.id.add_images})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.add_images:
                AddGalleryImagesBottomSheet galleryImagesBottomSheet = new
                        AddGalleryImagesBottomSheet();
                galleryImagesBottomSheet.newInstance(GalleryFragment.this);
                galleryImagesBottomSheet.show(getActivity().getSupportFragmentManager(),
                        "add__dialog_fragment");
                break;
        }
    }

    @Override
    public void OnCommonInterfaceClick(int id, boolean isAdded) {
        getImagesApi();
    }
}
