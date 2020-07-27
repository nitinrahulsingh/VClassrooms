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

import com.vclassrooms.Adapter.StudentlistAdapter;
import com.vclassrooms.Adapter.TeacherDirectoryAdapter;
import com.vclassrooms.Adapter.TeacherListAdapter;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.StudentListResponse;
import com.vclassrooms.Entity.TeacherDirectoryResponse;
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
 * Created by Rahul on 24,July,2020
 */
public class TeacherListFragment extends Fragment {
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
    List<TeacherDirectoryResponse.DirectoryeDetail> mTeacherDataList=new ArrayList<>();
    TeacherListAdapter teacherListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.common_recyclerview_layout,null);
        ButterKnife.bind(TeacherListFragment.this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
        return mview;
    }
    private void init() {
        appUtils.setText(tittle_tv, "Teacher");
        mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
            relAdd.setVisibility(View.VISIBLE);
        getTeacherDirectoryDetailApi();
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
                    ArrayList<TeacherDirectoryResponse.DirectoryeDetail> searchFiterLs = filter(mTeacherDataList, edtSearch.getText().toString());
                    if (searchFiterLs.size() > 0) {
                        teacherListAdapter = new TeacherListAdapter(getActivity(), searchFiterLs);
                        recyclerview.setAdapter(teacherListAdapter);
                        teacherListAdapter.notifyDataSetChanged();
                        searchClose.setVisibility(View.VISIBLE);
                    } else {
                        searchClose.setVisibility(View.VISIBLE);
                        appUtils.showToast(context, getString(R.string.no_result_found));
                    }

                }else {
                    teacherListAdapter = new TeacherListAdapter(getActivity(), mTeacherDataList);
                    recyclerview.setAdapter(teacherListAdapter);
                    teacherListAdapter.notifyDataSetChanged();
                    searchClose.setVisibility(View.GONE);
                }
            }
        });
    }
    private ArrayList<TeacherDirectoryResponse.DirectoryeDetail> filter(List<TeacherDirectoryResponse.DirectoryeDetail>
                                                                      dirItemArrayList,
                                                              String query) {
        final ArrayList<TeacherDirectoryResponse.DirectoryeDetail> filteredModelList = new ArrayList<>();
        try {
            query = query.toLowerCase().trim();
            if (query.equals("")) {
            }
            else {
                for (TeacherDirectoryResponse.DirectoryeDetail model : dirItemArrayList) {
                    final String text = model.getFirstName().toLowerCase();
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
    @OnClick({R.id.searchClose,R.id.relAdd})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.searchClose:
                edtSearch.setText("");
                teacherListAdapter = new TeacherListAdapter(getActivity(), mTeacherDataList);
                recyclerview.setAdapter(teacherListAdapter);
                teacherListAdapter.notifyDataSetChanged();
                searchClose.setVisibility(View.GONE);
                break;
            case R.id.relAdd:
                TeacherMarkAttendance teacherListFragment=new TeacherMarkAttendance();
                appUtils.onAddFragment(getActivity(),teacherListFragment,R.id.fragment_mainLayout);
                break;
        }
    }
    private void getTeacherDirectoryDetailApi() {
        try {
            appUtils.showProgressbar(context);
            Call<TeacherDirectoryResponse> call = ApiService.buildService(context).getTeacherDirDetails(strAuth,constatnts.GetEmployeeDetails,strRoleid,strUserId,strSchoolId,strAcademicId);
            call.enqueue(new Callback<TeacherDirectoryResponse>() {
                @Override
                public void onResponse(Call<TeacherDirectoryResponse> call, Response<TeacherDirectoryResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if(response.body().getData().getDirectoryeDetail()!=null && response.body().getData().getDirectoryeDetail().size()>0){
                                txt_no_data.setVisibility(View.GONE);
                                recyclerview.setVisibility(View.VISIBLE);
                                mTeacherDataList=response.body().getData().getDirectoryeDetail();
                                teacherListAdapter = new TeacherListAdapter(context, response.body().getData().getDirectoryeDetail());
                                recyclerview.setAdapter(teacherListAdapter);
                                recyclerview.setHasFixedSize(true);
                                recyclerview.setNestedScrollingEnabled(false);
                                teacherListAdapter.setOnClickListener(new TeacherListAdapter.TeacherDataClick() {
                                    @Override
                                    public void onTeachertDataClick(int id, int position) {
                                        SelfAttendanceFragment fragment = new SelfAttendanceFragment();
                                        Bundle   bundle=new Bundle();
                                        bundle.putString("ClassName", "0");
                                        bundle.putString("DivisionId","0");
                                        bundle.putString("StandardID", "0");
                                        bundle.putString("UserId",mTeacherDataList.get(position).getEmpId().toString());
                                        bundle.putString("isEmployeeSelf","True");
                                        fragment.setArguments(bundle);
                                        appUtils.onAddFragment(getActivity(), fragment,R.id.fragment_mainLayout);
                                    }
                                });
                            }else {
                                txt_no_data.setVisibility(View.VISIBLE);
                                recyclerview.setVisibility(View.GONE);
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
                public void onFailure(Call<TeacherDirectoryResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
