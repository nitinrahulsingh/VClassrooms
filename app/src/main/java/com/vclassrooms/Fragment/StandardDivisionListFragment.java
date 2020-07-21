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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Adapter.AttendanceStandardListAdapter;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.StandardDivisionResponse;
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
 * Created by Rahul on 08,July,2020
 */
public class StandardDivisionListFragment extends Fragment {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    @BindView(R.id.classesRecyclerView)
    RecyclerView classesRecyclerView;
    @BindView(R.id.txt_no_data)
    TextView txt_no_data;
    @BindView(R.id.txt_no_Internet)
    TextView txt_no_Internet;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.searchClose)
    ImageView searchClose;
    @BindView(R.id.linearSearches)
    LinearLayout linearSearches;
    @BindView(R.id.tittle_tv)
    TextView tittle_tv;
    LinearLayoutManager mLayoutManager;
    AttendanceStandardListAdapter attendanceStandardListAdapter;
    List<StandardDivisionResponse.Division> mStandardDataList=new ArrayList<>();
    String strModuleName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.standard_list_fragment,null);
        ButterKnife.bind(this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
        return mview;
    }
    private void init() {
        tittle_tv.setText("Standard");
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
        strModuleName=getArguments().getString("Module");
        mLayoutManager = new LinearLayoutManager(context);
        classesRecyclerView.setLayoutManager(mLayoutManager);
        getStandardDetailApi();
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
                    ArrayList<StandardDivisionResponse.Division> searchFiterLs = filter(mStandardDataList, edtSearch.getText().toString());
                    if (searchFiterLs.size() > 0) {
                        attendanceStandardListAdapter = new AttendanceStandardListAdapter(getActivity(), searchFiterLs,strModuleName);
                        classesRecyclerView.setAdapter(attendanceStandardListAdapter);
                        attendanceStandardListAdapter.notifyDataSetChanged();
                        searchClose.setVisibility(View.VISIBLE);
                    } else {
                        searchClose.setVisibility(View.VISIBLE);
                        appUtils.showToast(context, getString(R.string.no_result_found));
                    }

                }else {
                    attendanceStandardListAdapter = new AttendanceStandardListAdapter(getActivity(), mStandardDataList,strModuleName);
                    classesRecyclerView.setAdapter(attendanceStandardListAdapter);
                    attendanceStandardListAdapter.notifyDataSetChanged();
                    searchClose.setVisibility(View.GONE);
                }
            }
        });
    }
    private ArrayList<StandardDivisionResponse.Division> filter(List<StandardDivisionResponse.Division>
                                                                                dirItemArrayList,
                                                                        String query) {
        final ArrayList<StandardDivisionResponse.Division> filteredModelList = new ArrayList<>();
        try {
            query = query.toLowerCase().trim();
            if (query.equals("")) {
            }
            else {
                for (StandardDivisionResponse.Division model : dirItemArrayList) {
                    final String text = model.getStandardName().toLowerCase();
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
    @OnClick({R.id.searchClose})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.searchClose:
                edtSearch.setText("");
                attendanceStandardListAdapter = new AttendanceStandardListAdapter(getActivity(), mStandardDataList,strModuleName);
                classesRecyclerView.setAdapter(attendanceStandardListAdapter);
                attendanceStandardListAdapter.notifyDataSetChanged();
                searchClose.setVisibility(View.GONE);
                break;
        }
    }
    private void getStandardDetailApi() {
        try {
            appUtils.showProgressbar(context);
            Call<StandardDivisionResponse> call = ApiService.buildService(context).getStandardDivisionList(strAuth,"GetStandardDivision",strRoleid,strUserId,strSchoolId,strAcademicId,"0");
            call.enqueue(new Callback<StandardDivisionResponse>() {
                @Override
                public void onResponse(Call<StandardDivisionResponse> call, Response<StandardDivisionResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if(response.body().getData().getDivision()!=null){
                                classesRecyclerView.setVisibility(View.VISIBLE);
                                linearSearches.setVisibility(View.VISIBLE);
                                txt_no_data.setVisibility(View.GONE);
                                mStandardDataList=response.body().getData().getDivision();
                                attendanceStandardListAdapter = new AttendanceStandardListAdapter(getActivity(), response.body().getData().getDivision(),strModuleName);
                                classesRecyclerView.setAdapter(attendanceStandardListAdapter);
                                classesRecyclerView.setHasFixedSize(true);
                                classesRecyclerView.setNestedScrollingEnabled(false);
                            }else {
                               classesRecyclerView.setVisibility(View.GONE);
                                linearSearches.setVisibility(View.GONE);
                                txt_no_data.setVisibility(View.VISIBLE);

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
                public void onFailure(Call<StandardDivisionResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
}