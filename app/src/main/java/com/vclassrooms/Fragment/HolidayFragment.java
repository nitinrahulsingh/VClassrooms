package com.vclassrooms.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Adapter.AttendanceStandardListAdapter;
import com.vclassrooms.Adapter.HolidayAdapter;
import com.vclassrooms.CollapseCalender.widget.CollapsibleCalendar;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.AttendanceDetail;
import com.vclassrooms.Entity.HolidayDetailResponse;
import com.vclassrooms.Entity.HolidayEnum;
import com.vclassrooms.Entity.StudentAttendanceDetailResponse;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 17,July,2020
 */
public class HolidayFragment extends Fragment {
    View mView;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    CollapsibleCalendar holiday_calender;
    TextView tv_no_data;
    RecyclerView allholidaylist;
    List<HolidayDetailResponse.HolidayEvent> holidayEventList=new ArrayList<>();
    ArrayList<String> dates = new ArrayList<String>();
    ArrayList<HolidayEnum> holidayList = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_holiday,null);
        ButterKnife.bind(this,mView);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
        return mView;
    }

    private void init() {
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);

        tv_no_data=mView.findViewById(R.id.tv_no_data);
        allholidaylist=mView.findViewById(R.id.allholidaylist);
        holiday_calender=mView.findViewById(R.id.holiday_calender);
        mLayoutManager = new LinearLayoutManager(context);
        allholidaylist.setLayoutManager(mLayoutManager);
        getHolidayListApi();
    }

    private void getHolidayListApi() {
        try {
            appUtils.showProgressbar(context);
            Call<HolidayDetailResponse> call = ApiService.buildService(context).getHolidayList(strAuth,"GetHolidays",strRoleid,strUserId,strSchoolId,strAcademicId);
            call.enqueue(new Callback<HolidayDetailResponse>() {
                @Override
                public void onResponse(Call<HolidayDetailResponse> call, Response<HolidayDetailResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if(response.body().getData().getHolidayEvent()!=null && response.body().getData().getHolidayEvent().size()>0){
                                holidayEventList=response.body().getData().getHolidayEvent();
                                 Holiday_list();
                                tv_no_data.setVisibility(View.GONE);
                                holiday_calender.setVisibility(View.VISIBLE);
                                allholidaylist.setVisibility(View.VISIBLE);
                                HolidayAdapter holidayadaptr = new HolidayAdapter(getActivity(),holidayEventList);
                                allholidaylist.setAdapter(holidayadaptr);
                                allholidaylist.setHasFixedSize(true);
                                allholidaylist.setNestedScrollingEnabled(false);
                            }else {
                                tv_no_data.setVisibility(View.VISIBLE);
                                holiday_calender.setVisibility(View.GONE);
                                allholidaylist.setVisibility(View.GONE);
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
                public void onFailure(Call<HolidayDetailResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
    public void Holiday_list() {
        String[] dateList ;
        for (int j = 0; j < holidayEventList.size(); j++) {
            String newDate="";
            HolidayEnum hol = new HolidayEnum();
          String  startdate = holidayEventList.get(j).getFromDate();
            int   dayscount = holidayEventList.get(j).getNoOfDays();
            String    fesival = holidayEventList.get(j).getHolidayDetails();
            hol.setFromDate(startdate);
            hol.setHoliday_name(fesival);
            for (int i = 0; i < dayscount - 1; i++) {
                HolidayEnum vac1 = new HolidayEnum();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                try {
                    //Setting the date to the given date
                    if (i == 0) {
                        c.setTime(sdf.parse(startdate));
                    } else {
                        c.setTime(sdf.parse(newDate));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Number of Days to add
                c.add(Calendar.DAY_OF_MONTH, 1);
                //Date after adding the days to the given date
                newDate = sdf.format(c.getTime());
                vac1.setFromDate(newDate);
                vac1.setHoliday_name(fesival);
                holidayList.add(vac1);
                dates.add(newDate);
                dateList = newDate.split("/");
                holiday_calender.addEventTag(Integer.parseInt(dateList[2]),Integer.parseInt(dateList[1])-1,Integer.parseInt(dateList[0]),getResources().getColor(R.color.red));
            }

            dates.add(startdate);
            holidayList.add(hol);
            dateList = startdate.split("/");
            holiday_calender.addEventTag(Integer.parseInt(dateList[2]),Integer.parseInt(dateList[1])-1,Integer.parseInt(dateList[0]),getResources().getColor(R.color.red));

        }
    }
}
