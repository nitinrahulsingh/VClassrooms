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

import com.vclassrooms.Adapter.AssignmentAdapter;
import com.vclassrooms.Adapter.LeaveDetailAdapter;
import com.vclassrooms.BottomSheetDialog.AddAssignmentBottomSheet;
import com.vclassrooms.BottomSheetDialog.ApplyLeaveBottomSheet;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.AssignmentResponse;
import com.vclassrooms.Entity.LeaveDeatailsResponse;
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
 * Created by Rahul on 18,July,2020
 */
public class LeaveDetailFragment extends Fragment {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth, strRoleid, strUserId, strSchoolId, strAcademicId;
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
    String strClassName, strDivisionId, strStandardID;
    LinearLayoutManager mLayoutManager;
    LeaveDetailAdapter leaveDetailAdapter;
    List<LeaveDeatailsResponse.LeaveDetail> leaveDetailList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.common_recyclerview_layout, null);
        ButterKnife.bind(LeaveDetailFragment.this, mview);
        context = getActivity();
        appUtils = new AppUtils();
        constatnts = new Constatnts();
        init();
        return mview;
    }

    private void init() {
        appUtils.setText(tittle_tv, "Leave");
        mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        relAdd.setVisibility(View.VISIBLE);
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);

        edtSearch.setHint("Search");
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
                    ArrayList<LeaveDeatailsResponse.LeaveDetail> searchFiterLs = filter(leaveDetailList, edtSearch.getText().toString());
                    if (searchFiterLs.size() > 0) {
                        leaveDetailAdapter = new LeaveDetailAdapter(getActivity(), searchFiterLs);
                        recyclerview.setAdapter(leaveDetailAdapter);
                        leaveDetailAdapter.notifyDataSetChanged();
                        searchClose.setVisibility(View.VISIBLE);
                    } else {
                        searchClose.setVisibility(View.VISIBLE);
                        appUtils.showToast(context, getString(R.string.no_result_found));
                    }

                } else {
                    leaveDetailAdapter = new LeaveDetailAdapter(getActivity(), leaveDetailList);
                    recyclerview.setAdapter(leaveDetailAdapter);
                    leaveDetailAdapter.notifyDataSetChanged();
                    searchClose.setVisibility(View.GONE);
                }
            }
        });
        getAssignmentListApi();
    }

    private ArrayList<LeaveDeatailsResponse.LeaveDetail> filter(List<LeaveDeatailsResponse.LeaveDetail>
                                                                        dirItemArrayList,
                                                                String query) {
        final ArrayList<LeaveDeatailsResponse.LeaveDetail> filteredModelList = new ArrayList<>();
        try {
            query = query.toLowerCase().trim();
            if (query.equals("")) {
            } else {
                for (LeaveDeatailsResponse.LeaveDetail model : dirItemArrayList) {
                    final String text = model.getLeaveName().toLowerCase();
                    if (text.contains(query)) {
                        filteredModelList.add(model);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filteredModelList;

    }

    @OnClick({R.id.relAdd, R.id.searchClose})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.relAdd:
                ApplyLeaveBottomSheet applyLeaveBottomSheet = new
                        ApplyLeaveBottomSheet();
                applyLeaveBottomSheet.show(getActivity().getSupportFragmentManager(),
                        "add__dialog_fragment");
                break;
            case R.id.searchClose:
                edtSearch.setText("");
                leaveDetailAdapter = new LeaveDetailAdapter(getActivity(), leaveDetailList);
                recyclerview.setAdapter(leaveDetailAdapter);
                leaveDetailAdapter.notifyDataSetChanged();
                searchClose.setVisibility(View.GONE);
                break;

        }
    }

    private void getAssignmentListApi() {
        try {
            appUtils.showProgressbar(context);
            Call<LeaveDeatailsResponse> call = ApiService.buildService(context).getLeaveDetailList(strAuth, "getLeaveApplication", strRoleid, strUserId, strAcademicId, strSchoolId);
            call.enqueue(new Callback<LeaveDeatailsResponse>() {
                @Override
                public void onResponse(Call<LeaveDeatailsResponse> call, Response<LeaveDeatailsResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode() == 0) {
                            if (response.body().getData().getLeaveDetail() != null && response.body().getData().getLeaveDetail().size() > 0) {
                                recyclerview.setVisibility(View.VISIBLE);
                                txt_no_data.setVisibility(View.GONE);

                                leaveDetailList = response.body().getData().getLeaveDetail();
                                leaveDetailAdapter = new LeaveDetailAdapter(getActivity(), response.body().getData().getLeaveDetail());
                                recyclerview.setAdapter(leaveDetailAdapter);
                                recyclerview.setHasFixedSize(true);
                                recyclerview.setNestedScrollingEnabled(false);
                            } else {
                                recyclerview.setVisibility(View.GONE);
                                txt_no_data.setVisibility(View.VISIBLE);

                            }
                        } else if (response.body().getStatusCode() == 1) {
                            appUtils.showToast(context, getString(R.string.error_message));
                        } else if (response.body().getStatusCode() == 2) {
                            appUtils.showToast(context, getString(R.string.unauthorize_message));
                        }
                    } else {
                        appUtils.showToast(context, getString(R.string.error_message));
                    }

                }

                @Override
                public void onFailure(Call<LeaveDeatailsResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
