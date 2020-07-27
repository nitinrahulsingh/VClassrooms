package com.vclassrooms.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Adapter.AnnouncementAdapter;
import com.vclassrooms.Adapter.AssignmentAdapter;
import com.vclassrooms.BottomSheetDialog.AddAnouncementBottomSheet;
import com.vclassrooms.BottomSheetDialog.AddAssignmentBottomSheet;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.AnnouncementDetailResponse;
import com.vclassrooms.Entity.AssignmentResponse;
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
 * Created by Rahul on 25,July,2020
 */
public class AnnouncementFragment extends Fragment implements CommonInterface {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    @BindView(R.id.tittle_tv)
    TextView tittle_tv;
    @BindView(R.id.relAdd)
    RelativeLayout relAdd;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.txt_no_data)
    TextView txt_no_data;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.searchClose)
    ImageView searchClose;
    @BindView(R.id.linearSearches)
    LinearLayout linearSearches;
    LinearLayoutManager mLayoutManager;
    AnnouncementAdapter assignmentAdapter;
    List<AnnouncementDetailResponse.AnnouncementDetail> announcementDetails=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.common_recyclerview_layout,null);
        ButterKnife.bind(AnnouncementFragment.this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
        return mview;
    }

    private void init() {
        appUtils.setText(tittle_tv,"Announcement");
        mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
        if(strRoleid.contentEquals("3")){
            relAdd.setVisibility(View.VISIBLE);

        }else if(strRoleid.contentEquals("2")){
            relAdd.setVisibility(View.GONE);
        }else {
            relAdd.setVisibility(View.VISIBLE);
        }
        edtSearch.setHint("Search Announcement");
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!edtSearch.getText().toString().equals("")) {
                    ArrayList<AnnouncementDetailResponse.AnnouncementDetail> searchFiterLs = filter(announcementDetails, edtSearch.getText().toString());
                    if (searchFiterLs.size() > 0) {
                        assignmentAdapter = new AnnouncementAdapter(getActivity(), searchFiterLs);
                        recyclerview.setAdapter(assignmentAdapter);
                        assignmentAdapter.notifyDataSetChanged();
                        searchClose.setVisibility(View.VISIBLE);
                    } else {
                        searchClose.setVisibility(View.VISIBLE);
                        appUtils.showToast(context, getString(R.string.no_result_found));
                    }

                }else {
                    assignmentAdapter = new AnnouncementAdapter(getActivity(), announcementDetails);
                    recyclerview.setAdapter(assignmentAdapter);
                    assignmentAdapter.notifyDataSetChanged();
                    searchClose.setVisibility(View.GONE);
                }
            }
        });
        getAssignmentListApi();
    }
    private ArrayList<AnnouncementDetailResponse.AnnouncementDetail> filter(List<AnnouncementDetailResponse.AnnouncementDetail>
                                                                    dirItemArrayList,
                                                            String query) {
        final ArrayList<AnnouncementDetailResponse.AnnouncementDetail> filteredModelList = new ArrayList<>();
        try {
            query = query.toLowerCase().trim();
            if (query.equals("")) {
            }
            else {
                for (AnnouncementDetailResponse.AnnouncementDetail model : dirItemArrayList) {
                    final String text = model.getAnnouncementName().toLowerCase();
                    if (text.contains(query)) {
                        filteredModelList.add(model);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return filteredModelList;

    }

    @OnClick({R.id.relAdd,R.id.searchClose})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.relAdd:
                AddAnouncementBottomSheet addAnouncementBottomSheet = new
                        AddAnouncementBottomSheet();
                addAnouncementBottomSheet.newInstance(AnnouncementFragment.this);
                addAnouncementBottomSheet.show(getActivity().getSupportFragmentManager(),
                        "add__dialog_fragment");
                break;
            case R.id.searchClose:
                edtSearch.setText("");
                assignmentAdapter = new AnnouncementAdapter(getActivity(), announcementDetails);
                recyclerview.setAdapter(assignmentAdapter);
                assignmentAdapter.notifyDataSetChanged();
                searchClose.setVisibility(View.GONE);
                break;

        }
    }
    private void getAssignmentListApi() {
        try {
            appUtils.showProgressbar(context);
            Call<AnnouncementDetailResponse> call = ApiService.buildService(context).getAnnouncement(strAuth,"select",strSchoolId,strAcademicId);
            call.enqueue(new Callback<AnnouncementDetailResponse>() {
                @Override
                public void onResponse(Call<AnnouncementDetailResponse> call, Response<AnnouncementDetailResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode()==0) {
                            if(response.body().getData().getAnnouncementDetail()!=null && response.body().getData().getAnnouncementDetail().size()>0 ){
                                recyclerview.setVisibility(View.VISIBLE);
                                txt_no_data.setVisibility(View.GONE);

                                announcementDetails=response.body().getData().getAnnouncementDetail();
                                assignmentAdapter = new AnnouncementAdapter(getActivity(), response.body().getData().getAnnouncementDetail());
                                recyclerview.setAdapter(assignmentAdapter);
                                recyclerview.setHasFixedSize(true);
                                recyclerview.setNestedScrollingEnabled(false);
                            }else {
                                recyclerview.setVisibility(View.GONE);
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
                public void onFailure(Call<AnnouncementDetailResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void OnCommonInterfaceClick(int id, boolean isAdded) {
        getAssignmentListApi();
    }
}

