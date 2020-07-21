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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.vclassrooms.Adapter.AssignmentAdapter;
import com.vclassrooms.Adapter.ExamTimetableAdapter;
import com.vclassrooms.Adapter.StudentlistAdapter;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.ExamTimeTableResponse;
import com.vclassrooms.Entity.ExamTimeTableResponse;
import com.vclassrooms.Entity.StudentListResponse;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 11,July,2020
 */
public class ClassExamTimeTableFragment extends Fragment {
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
    LinearLayoutManager mLayoutManager;
    ExamTimetableAdapter examTimetableAdapter;
    List<ExamTimeTableResponse.Timetable> timetableList=new ArrayList<>();
    String strClassName,strDivisionId,strStandardID;

    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.searchClose)
    ImageView searchClose;
    @BindView(R.id.linearSearches)
    LinearLayout linearSearches;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.common_recyclerview_layout,null);
        ButterKnife.bind(this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
        return mview;
    }

    private void init() {
        tittle_tv.setText("Exam TimeTable");
        relAdd.setVisibility(View.GONE);
        mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
        if(strRoleid.contentEquals("3")){

            strClassName=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_STANDARDNAME)+" ("
                    +appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_DIVISIONNAME)+")";
            strDivisionId=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_DIVISIONID);
            strStandardID=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_STANDARDID);
        }else if(strRoleid.contentEquals("2")){
            strDivisionId=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_DIVISIONID);
            strStandardID=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_STANDARDID);
        }else {
            strClassName=getArguments().getString("ClassName");
            strDivisionId=getArguments().getString("DivisionId");
            strStandardID=getArguments().getString("StandardID");
        }
        edtSearch.setHint("Search Subject");
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
                    ArrayList<ExamTimeTableResponse.Timetable> searchFiterLs = filter(timetableList, edtSearch.getText().toString());
                    if (searchFiterLs.size() > 0) {
                        examTimetableAdapter = new ExamTimetableAdapter(getActivity(), searchFiterLs);
                        recyclerview.setAdapter(examTimetableAdapter);
                        examTimetableAdapter.notifyDataSetChanged();
                        searchClose.setVisibility(View.VISIBLE);
                    } else {
                        searchClose.setVisibility(View.VISIBLE);
                        appUtils.showToast(context, getString(R.string.no_result_found));
                    }

                }else {
                    examTimetableAdapter = new ExamTimetableAdapter(getActivity(), timetableList);
                    recyclerview.setAdapter(examTimetableAdapter);
                    examTimetableAdapter.notifyDataSetChanged();
                    searchClose.setVisibility(View.GONE);
                }
            }
        });
        getExamTimetableListApi();
    }
    private ArrayList<ExamTimeTableResponse.Timetable> filter(List<ExamTimeTableResponse.Timetable>
                                                                      dirItemArrayList,
                                                              String query) {
        final ArrayList<ExamTimeTableResponse.Timetable> filteredModelList = new ArrayList<>();
        try {
            query = query.toLowerCase().trim();
            if (query.equals("")) {
            }
            else {
                for (ExamTimeTableResponse.Timetable model : dirItemArrayList) {
                    final String text = model.getSubjectName().toLowerCase();
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
                examTimetableAdapter = new ExamTimetableAdapter(getActivity(), timetableList);
                recyclerview.setAdapter(examTimetableAdapter);
                examTimetableAdapter.notifyDataSetChanged();
                searchClose.setVisibility(View.GONE);
                break;
        }
    }
    private void getExamTimetableListApi() {
        try {
            appUtils.showProgressbar(context);
            Call<ExamTimeTableResponse> call = ApiService.buildService(context).getExamTimeTableList(strAuth,"getExamTimeTable",strRoleid,strUserId,strSchoolId,strAcademicId,strStandardID,strDivisionId,"1");
            call.enqueue(new Callback<ExamTimeTableResponse>() {
                @Override
                public void onResponse(Call<ExamTimeTableResponse> call, Response<ExamTimeTableResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode()==0) {
                            if(response.body().getData().getTimetable()!=null && response.body().getData().getTimetable().size()>0){
                                recyclerview.setVisibility(View.VISIBLE);
                                txt_no_data.setVisibility(View.GONE);

                                timetableList=response.body().getData().getTimetable();
                                examTimetableAdapter = new ExamTimetableAdapter(getActivity(), response.body().getData().getTimetable());
                                recyclerview.setAdapter(examTimetableAdapter);
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
                public void onFailure(Call<ExamTimeTableResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
}