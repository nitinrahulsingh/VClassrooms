package com.vclassrooms.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import com.vclassrooms.CollapseCalender.widget.CollapsibleCalendar;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.AttendanceDetail;
import com.vclassrooms.Entity.StudentAttendanceDetailResponse;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 30,June,2020
 */
public class SelfAttendanceFragment extends Fragment  implements
        OnChartValueSelectedListener {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    Date holidayDay;
    CollapsibleCalendar collapsibleCalendar;
    private PieChart chart;
    public int[] yValues = {0, 0, 0,0};
    public String[] xValues = {"Present", "Absent", "Holiday","Excused"};
    TextView tv_no_data;
    String strClassName,strDivisionId,strStandardID;;
    List<AttendanceDetail> studentLists=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.single_student_attendance_activity,null);
        ButterKnife.bind(this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
        return mview;
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
        }else if(strRoleid.contentEquals("2")){
            strDivisionId=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_DIVISIONID);
            strStandardID=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_STANDARDID);
        } else {
            strClassName=getArguments().getString("ClassName");
            strDivisionId=getArguments().getString("DivisionId");
            strStandardID=getArguments().getString("StandardID");
        }
        tv_no_data=(TextView)mview.findViewById(R.id.tv_no_data);
        chart = mview.findViewById(R.id.chart1);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);


        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        chart.setOnChartValueSelectedListener(this);


        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);


        // entry label styling
        chart.setEntryLabelColor(Color.WHITE);
        //chart.setEntryLabelTypeface(tfRegular);
        chart.setEntryLabelTextSize(12f);
        collapsibleCalendar = mview.findViewById(R.id.collapsibleCalendarView);
//        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
//            @Override
//            public void onDaySelect() {
//            }
//
//            @Override
//            public void onItemClick(View view) {
//
//            }
//
//            @Override
//            public void onDataUpdate() {
//
//            }
//
//            @Override
//            public void onMonthChange() {
//
//            }
//
//            @Override
//            public void onWeekChange(int i) {
//
//            }
//        });
//        if (appUtils.isNetworkAvailableWithToast(getContext())) {
//            Calendar c = Calendar.getInstance();
//            int Year = c.get(Calendar.YEAR);
//            int  Month = c.get(Calendar.MONTH)+1;
//            getAttendanceDetals(Month, Year);
//        }
        getAttendanceStudentListApi();
    }


    private void getAttendanceStudentListApi() {
        try {
            String command="";
            if(strRoleid.contentEquals("2")){
                command="getStudentAttendance" ;
            }else {
                command="getEmployeeAttendance";
            }
            appUtils.showProgressbar(context);
            Call<StudentAttendanceDetailResponse> call = ApiService.buildService(context).getStudentAttendanceDatewise(strAuth,command,constatnts.StudentRole,"0",strSchoolId,strAcademicId,strDivisionId,strAcademicId,strUserId);
            call.enqueue(new Callback<StudentAttendanceDetailResponse>() {
                @Override
                public void onResponse(Call<StudentAttendanceDetailResponse> call, Response<StudentAttendanceDetailResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if(response.body().getData().getAttendanceDetail()!=null && response.body().getData().getAttendanceDetail().size()>0){
                                studentLists=response.body().getData().getAttendanceDetail();
                                Attendance_list();
                            }else {

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
    // colors for different sections in pieChart
    public static  final int[] MY_COLORS = {
            Color.rgb(0,128,0), Color.rgb(255,165,0), Color.rgb(255,255,0),
            Color.rgb(128,0,128)
    };
    public void Attendance_list() {
        int day = 0,present_count=0,absent_count=0,holiday_count=0,excused_count=0;
        for (int i = 0; i < studentLists.size(); i++) {
            String Attendance_status = studentLists.get(i).getStatus();
            String date_ = studentLists.get(i).getDate();
            String[] dateList = date_.split("/");
            switch(Attendance_status){
                case "P":
                    present_count=present_count+1;
                    collapsibleCalendar.addEventTag(Integer.parseInt(dateList[2]),Integer.parseInt(dateList[1])-1,Integer.parseInt(dateList[0]),getResources().getColor(R.color.green));
                    break;
                case "A":
                    absent_count=absent_count+1;
                    collapsibleCalendar.addEventTag(Integer.parseInt(dateList[2]),Integer.parseInt(dateList[1])-1,Integer.parseInt(dateList[0]),getResources().getColor(R.color.orange));
                    break;
                case "H":
                    holiday_count=holiday_count+1;
                    collapsibleCalendar.addEventTag(Integer.parseInt(dateList[2]),Integer.parseInt(dateList[1])-1,Integer.parseInt(dateList[0]),getResources().getColor(R.color.yellow));
                    break;
                case "L":
                    excused_count=excused_count+1;
                    collapsibleCalendar.addEventTag(Integer.parseInt(dateList[2]),Integer.parseInt(dateList[1])-1,Integer.parseInt(dateList[0]),getResources().getColor(R.color.purple));
                    break;
            }
        }
        yValues= new int[]{present_count, absent_count, holiday_count, excused_count};
        setDataForPieChart();
    }
    private void setDataForPieChart() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < yValues.length ; i++) {
            if (Integer.valueOf(yValues[i]) > 0) {
                entries.add(new PieEntry(yValues[i], xValues[i]));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "Attendance");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors


        dataSet.setColors(MY_COLORS);
        chart.setDrawSliceText(false);
        chart.getDescription().setEnabled(false);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        // data.setValueTypeface(tfLight);
        chart.setData(data);
        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
        chart.getLegend().setEnabled(false);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
        String s= String.valueOf("Your "+xValues[h.getDataSetIndex()]+" Count is "+e.getY());
        appUtils.showToast(getContext(), String.valueOf(s));
    }

    @Override
    public void onNothingSelected() {

    }
}
