package com.vclassrooms.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Adapter.MarkAttendanceAdapter;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.AttendanceDetail;
import com.vclassrooms.Entity.CommonSuccessResponse;
import com.vclassrooms.Entity.MarkAttendanceEnum;
import com.vclassrooms.Entity.StudentAttendanceDetailResponse;
import com.vclassrooms.Entity.StudentListResponse;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 08,July,2020
 */
public class MarkAttendanceFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    DatePickerDialog datePickerDialog;
    @BindView(R.id.relChangeDate)
    RelativeLayout relChangeDate;
    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.txt_standard)
    TextView txt_standard;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.txt_no_data)
    TextView txt_no_data;
    @BindView(R.id.txt_no_Internet)
    TextView txt_no_Internet;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    String strDate,strClassName,strDivisionId,strStandardID;;

    LinearLayoutManager mLayoutManager;
    MarkAttendanceAdapter markAttendanceAdapter;
    List<AttendanceDetail> studentLists=new ArrayList<>();
    int intPcount=0,intAcount=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.attendance_fragment,null);
        ButterKnife.bind(this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
        return mview;
    }
    @OnClick({R.id.btnSubmit,R.id.relChangeDate})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnSubmit:
                MarkAttendancedata();
                break;
            case R.id.relChangeDate:
                datePickerDialog.show();
                break;
        }
    }
    public void MarkAttendancedata()
    {
        String strDateSelected=appUtils.convertDateFormat("dd/MM/yyyy","yyyy/MM/dd",strDate);
        appUtils.showProgressbar(context);
        List<MarkAttendanceEnum> markListStudents = new ArrayList<>();
        List<AttendanceDetail> studentlist=((MarkAttendanceAdapter)markAttendanceAdapter).getStudentList();
        MarkAttendanceEnum markAttendanceEnum;

        for(int i=0;i<studentlist.size();i++){
            markAttendanceEnum=new MarkAttendanceEnum();
            markAttendanceEnum.setAttendance_Source("Android");
            markAttendanceEnum.setIntSchool_id(Integer.parseInt(strSchoolId));
            markAttendanceEnum.setRole_Id(constatnts.StudentRole);
            markAttendanceEnum.setStandard_id(Integer.parseInt(strStandardID)) ;
            markAttendanceEnum.setIntDivision_id(Integer.parseInt(strDivisionId));
            markAttendanceEnum.setIntAcademic_id(Integer.parseInt(strAcademicId)); ;
            markAttendanceEnum.setDtDate(strDateSelected); ;
            markAttendanceEnum.setStatus(studentlist.get(i).getStatus()); ;
            if(studentlist.get(i).getStatus().contentEquals("P")){
                intPcount=intPcount+1;
            }else {
                intAcount=intAcount+1;
            }
            markAttendanceEnum.setUserId(studentlist.get(i).getUserId()); ;

            markAttendanceEnum.setFCMToken(""); ;

            markAttendanceEnum.setLoginTime(strDateSelected); ;

            markListStudents.add(markAttendanceEnum);
        }
        String strAttendanceCountMsg="Present Count="+intPcount +"\n"+"Absent Count="+intAcount;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(strAttendanceCountMsg)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       dialog.cancel();
                        MarkAttendanceApi(markListStudents);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        appUtils.hideProgressbar();
    }
    private void MarkAttendanceApi(List<MarkAttendanceEnum> markListStudents) {
        try {
            appUtils.showProgressbar(context);
            Call<CommonSuccessResponse> call = ApiService.buildService(context).MarkAttendanceDetail(strAuth,"InsertStudent",markListStudents);
            call.enqueue(new Callback<CommonSuccessResponse>() {
                @Override
                public void onResponse(Call<CommonSuccessResponse> call, Response<CommonSuccessResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                                appUtils.showToast(context, getString(R.string.success));
                            btnSubmit.setText("Update");

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

    private void init() {
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
        }else {
            strClassName=getArguments().getString("ClassName");
            strDivisionId=getArguments().getString("DivisionId");
            strStandardID=getArguments().getString("StandardID");
        }
        appUtils.setText(txt_standard,strClassName);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        strDate=formattedDate;
        appUtils.setText(txt_date,formattedDate);
        datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Locale locale;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    locale = Resources.getSystem().getConfiguration().getLocales().get(0);
                } else {
                    //noinspection deprecation
                    locale = Resources.getSystem().getConfiguration().locale;
                }
                Button buttonNeg = datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });

        mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        getMarkAttendanceStudentListApi(strDate);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        strDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (month + 1), year);
        getMarkAttendanceStudentListApi(strDate);
        appUtils.setText(txt_date,strDate);
    }
    private void getMarkAttendanceStudentListApi(String strDateSelected) {
        try {
            if(studentLists!=null){
                studentLists.clear();
            }
            strDateSelected=appUtils.convertDateFormat("dd/MM/yyyy","yyyy/MM/dd",strDateSelected);
            appUtils.showProgressbar(context);
            Call<StudentAttendanceDetailResponse> call = ApiService.buildService(context).getStudentAttendanceDatewise(strAuth,"getAllStudentAttendance",constatnts.StudentRole, strDateSelected,strSchoolId,strAcademicId,strDivisionId,strAcademicId,strUserId);
            call.enqueue(new Callback<StudentAttendanceDetailResponse>() {
                @Override
                public void onResponse(Call<StudentAttendanceDetailResponse> call, Response<StudentAttendanceDetailResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if(response.body().getData().getAttendanceDetail()!=null && response.body().getData().getAttendanceDetail().size()>0){
                                recyclerview.setVisibility(View.VISIBLE);
                                txt_no_data.setVisibility(View.GONE);
                                btnSubmit.setText("Update");
                                studentLists=response.body().getData().getAttendanceDetail();
                                markAttendanceAdapter = new MarkAttendanceAdapter(getActivity(), studentLists);
                                recyclerview.setAdapter(markAttendanceAdapter);
                                recyclerview.setHasFixedSize(true);
                                recyclerview.setNestedScrollingEnabled(false);
                            }else {
                                btnSubmit.setText("Submit");
                                getStudentListApi();
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
                public void onFailure(Call<StudentAttendanceDetailResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
    private void getStudentListApi() {
        try {
            appUtils.showProgressbar(context);
            Call<StudentListResponse> call = ApiService.buildService(context).getStudentStandardWiseList(strAuth,"GetStudentList",strRoleid,strUserId,strSchoolId,strAcademicId,strDivisionId,strStandardID);
            call.enqueue(new Callback<StudentListResponse>() {
                @Override
                public void onResponse(Call<StudentListResponse> call, Response<StudentListResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if(response.body().getData().getStudentList()!=null){
                                recyclerview.setVisibility(View.VISIBLE);
                                txt_no_data.setVisibility(View.GONE);
                                AttendanceDetail attendanceDetail;
                                for(int i=0;i<response.body().getData().getStudentList().size();i++){
                                    attendanceDetail=new AttendanceDetail();
                                    attendanceDetail.setUserId(response.body().getData().getStudentList().get(i).getStudentId());
                                    attendanceDetail.setDivisionId(Integer.valueOf(strDivisionId));
                                    attendanceDetail.setStandardId(Integer.valueOf(strStandardID));
                                    attendanceDetail.setImageURL(response.body().getData().getStudentList().get(i).getImageURL());
                                    attendanceDetail.setName(response.body().getData().getStudentList().get(i).getFirstName());
                                    attendanceDetail.setStatus("P");
                                    studentLists.add(attendanceDetail);
                                }
                                markAttendanceAdapter = new MarkAttendanceAdapter(getActivity(), studentLists);
                                recyclerview.setAdapter(markAttendanceAdapter);
                                recyclerview.setHasFixedSize(true);
                                recyclerview.setNestedScrollingEnabled(false);
                            }else {
                                recyclerview.setVisibility(View.GONE);
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
                public void onFailure(Call<StudentListResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
