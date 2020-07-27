package com.vclassrooms.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Adapter.ClassTimeTableAdapter;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.ClasTimeTableResponse;
import com.vclassrooms.Entity.Timetable;
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
 * Created by Rahul on 26,July,2020
 */
public class TeacherTimeTable extends Fragment {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    @BindView(R.id.tittle_tv)
    TextView tittle_tv;
    @BindView(R.id.dir_RecyclerView)
    RecyclerView recyclerview;
    @BindView(R.id.txt_no_data)
    TextView txt_no_data;
    LinearLayoutManager mLayoutManager;
    ClassTimeTableAdapter classTimeTableAdapter;

    String strClassName,strDivisionId,strStandardID;
    @BindView(R.id.mon_text)
    TextView mon_text;
    @BindView(R.id.tue_text)
    TextView tue_text;
    @BindView(R.id.wed_text)
    TextView wed_text;
    @BindView(R.id.thur_text)
    TextView thur_text;
    @BindView(R.id.fri_text)
    TextView fri_text;
    @BindView(R.id.sat_text)
    TextView sat_text;

    List<Timetable> timetableListMon=new ArrayList<>();
    List<Timetable> timetableListTue=new ArrayList<>();
    List<Timetable> timetableListWed=new ArrayList<>();
    List<Timetable> timetableListThu=new ArrayList<>();
    List<Timetable> timetableListFri=new ArrayList<>();
    List<Timetable> timetableListSat=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.class_time_table_fragment,null);
        ButterKnife.bind(this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
        return mview;
    }

    private void init() {
        tittle_tv.setText("Class TimeTable");
        mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
        if(strRoleid.contentEquals("2")){
            strDivisionId=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_DIVISIONID);
            strStandardID=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_STANDARDID);
        }else {
            strClassName=getArguments().getString("ClassName");
            strDivisionId=getArguments().getString("DivisionId");
            strStandardID=getArguments().getString("StandardID");
        }

        getExamTimetableListApi();
    }
    @OnClick({R.id.mon_text,R.id.tue_text,R.id.wed_text,R.id.thur_text,R.id.fri_text,R.id.sat_text})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.mon_text:
                try {
                    appUtils.showProgressbar(context);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mon_text.setBackgroundTintList(getResources().getColorStateList(R.color.pink));
                        tue_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        wed_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        thur_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        fri_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        sat_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                    }
                    AdapaterNotify(timetableListMon);
                    appUtils.hideProgressbar();
                } catch (Exception e) {
                    appUtils.hideProgressbar();
                }
                break;
            case R.id.tue_text:
                try {
                    Log.e("Student_Schedule", "Clicktue");
                    appUtils.showProgressbar(context);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mon_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        tue_text.setBackgroundTintList(getResources().getColorStateList(R.color.pink));
                        wed_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        thur_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        fri_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        sat_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                    }
                    AdapaterNotify(timetableListTue);
                    appUtils.hideProgressbar();
                } catch (Exception e) {
                    Log.e("onStudentDayClick", e.getMessage());
                    appUtils.hideProgressbar();
                }
                break;
            case R.id.wed_text:
                try {
                    appUtils.showProgressbar(context);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mon_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        tue_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        wed_text.setBackgroundTintList(getResources().getColorStateList(R.color.pink));
                        thur_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        fri_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                    }
                    AdapaterNotify(timetableListWed);
                    appUtils.hideProgressbar();
                } catch (Exception e) {
                    appUtils.hideProgressbar();
                }
                break;
            case R.id.thur_text:
                try {
                    appUtils.showProgressbar(context);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mon_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        tue_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        wed_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        thur_text.setBackgroundTintList(getResources().getColorStateList(R.color.pink));
                        fri_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        sat_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                    }
                    AdapaterNotify(timetableListThu);
                    appUtils.hideProgressbar();
                } catch (Exception e) {
                    appUtils.hideProgressbar();
                }
                break;
            case R.id.fri_text:
                try {
                    appUtils.showProgressbar(context);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mon_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        tue_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        wed_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        thur_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        fri_text.setBackgroundTintList(getResources().getColorStateList(R.color.pink));
                        sat_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                    }
                    AdapaterNotify(timetableListFri);
                    appUtils.hideProgressbar();
                } catch (Exception e) {
                    appUtils.hideProgressbar();
                }
                break;
            case R.id.sat_text:
                try {
                    appUtils.showProgressbar(context);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mon_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        tue_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        wed_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        thur_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        fri_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
                        sat_text.setBackgroundTintList(getResources().getColorStateList(R.color.pink));
                    }
                    AdapaterNotify(timetableListSat);
                    appUtils.hideProgressbar();
                } catch (Exception e) {
                    appUtils.hideProgressbar();
                }
                break;
        }
    }
    public void AdapaterNotify(List<Timetable> timetable) {
        try {
            if (timetable != null) {
                recyclerview.setVisibility(View.VISIBLE);
                txt_no_data.setVisibility(View.GONE);
                classTimeTableAdapter = new ClassTimeTableAdapter(getActivity(), timetable);
                recyclerview.setAdapter(classTimeTableAdapter);
                recyclerview.setHasFixedSize(true);
                recyclerview.setNestedScrollingEnabled(false);
            }else {
                recyclerview.setVisibility(View.GONE);
                txt_no_data.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {

        }

    }
    private void getExamTimetableListApi() {
        try {
            appUtils.showProgressbar(context);
            Call<ClasTimeTableResponse> call = ApiService.buildService(context).getClassTimeTableList(strAuth,"getTeacherTimeTable",strRoleid,strUserId,strSchoolId,strAcademicId,strStandardID,strDivisionId,"0");
            call.enqueue(new Callback<ClasTimeTableResponse>() {
                @Override
                public void onResponse(Call<ClasTimeTableResponse> call, Response<ClasTimeTableResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode()==0) {
                            if(response.body().getData().getTimetable()!=null && response.body().getData().getTimetable().size()>0){
                                recyclerview.setVisibility(View.VISIBLE);
                                txt_no_data.setVisibility(View.GONE);
                                setData(response.body().getData().getTimetable());

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
                public void onFailure(Call<ClasTimeTableResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void setData(List<Timetable> timetable) {
        String strStandardname="";
        Timetable timetable1;
        for(int i=0;i<timetable.size();i++){
            switch (timetable.get(i).getDay_Name()) {
                case "Monday":
                    if(timetable.get(i).getDivision_Name()!=null){
                        strStandardname=timetable.get(i).getStandard_name()+" ("+timetable.get(i).getDivision_Name()+")";
                    }else {
                        strStandardname=timetable.get(i).getStandard_name();
                    }

                    timetable1=new Timetable(timetable.get(i).getSubjectName(), timetable.get(i).getDay_Name(),strStandardname,
                            timetable.get(i).getStartTime(), timetable.get(i).getEndTime());
                    timetableListMon.add(timetable1);
                    break;
                case "Tuesday":
                    if(timetable.get(i).getDivision_Name()!=null){
                        strStandardname=timetable.get(i).getStandard_name()+" ("+timetable.get(i).getDivision_Name()+")";
                    }else {
                        strStandardname=timetable.get(i).getStandard_name();
                    }
                    timetable1=new Timetable(timetable.get(i).getSubjectName(), timetable.get(i).getDay_Name(),strStandardname,
                            timetable.get(i).getStartTime(), timetable.get(i).getEndTime());
                    timetableListTue.add(timetable1);
                    break;
                case "Wednesday":
                    if(timetable.get(i).getDivision_Name()!=null){
                        strStandardname=timetable.get(i).getStandard_name()+" ("+timetable.get(i).getDivision_Name()+")";
                    }else {
                        strStandardname=timetable.get(i).getStandard_name();
                    }
                    timetable1=new Timetable(timetable.get(i).getSubjectName(), timetable.get(i).getDay_Name(), strStandardname,
                            timetable.get(i).getStartTime(), timetable.get(i).getEndTime());
                    timetableListWed.add(timetable1);
                    break;
                case "Thursday":
                    if(timetable.get(i).getDivision_Name()!=null){
                        strStandardname=timetable.get(i).getStandard_name()+" ("+timetable.get(i).getDivision_Name()+")";
                    }else {
                        strStandardname=timetable.get(i).getStandard_name();
                    }
                    timetable1=new Timetable(timetable.get(i).getSubjectName(), timetable.get(i).getDay_Name(),strStandardname,
                            timetable.get(i).getStartTime(), timetable.get(i).getEndTime());
                    timetableListThu.add(timetable1);
                    break;
                case "Friday":
                    if(timetable.get(i).getDivision_Name()!=null){
                        strStandardname=timetable.get(i).getStandard_name()+" ("+timetable.get(i).getDivision_Name()+")";
                    }else {
                        strStandardname=timetable.get(i).getStandard_name();
                    }
                    timetable1=new Timetable(timetable.get(i).getSubjectName(), timetable.get(i).getDay_Name(), strStandardname,
                            timetable.get(i).getStartTime(), timetable.get(i).getEndTime());
                    timetableListFri.add(timetable1);
                    break;
                case "Saturday":
                    if(timetable.get(i).getDivision_Name()!=null){
                        strStandardname=timetable.get(i).getStandard_name()+" ("+timetable.get(i).getDivision_Name()+")";
                    }else {
                        strStandardname=timetable.get(i).getStandard_name();
                    }
                    timetable1=new Timetable(timetable.get(i).getSubjectName(), timetable.get(i).getDay_Name(), strStandardname,
                            timetable.get(i).getStartTime(), timetable.get(i).getEndTime());
                    timetableListSat.add(timetable1);
                    break;
                default:
                    break;
            }
        }
        classTimeTableAdapter = new ClassTimeTableAdapter(getActivity(), timetableListMon);
        recyclerview.setAdapter(classTimeTableAdapter);
        recyclerview.setHasFixedSize(true);
        recyclerview.setNestedScrollingEnabled(false);
    }
}
