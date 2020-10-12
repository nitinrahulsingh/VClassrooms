package com.vclassrooms.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 12,August,2020
 */
public class VideoConferenceFragment extends Fragment {
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

    @BindView(R.id.parentCardView)
    CardView parentCardView;

    List<Timetable> timetableList=new ArrayList<>();
    String dayOfTheWeek;
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
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
         dayOfTheWeek = sdf.format(d);
        parentCardView.setVisibility(View.GONE);
        tittle_tv.setText("Video Conference");
        mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
        if(strRoleid.contentEquals("2")){
            strClassName=(appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_STANDARDNAME).replace(" ","").toString())+appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_DIVISIONNAME);
            strDivisionId=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_DIVISIONID);
            strStandardID=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_STANDARDID);
        }else {
            strClassName=getArguments().getString("ClassName");
            strDivisionId=getArguments().getString("DivisionId");
            strStandardID=getArguments().getString("StandardID");
        }
        resetBgColor();
        URL serverURL;
        try {
            serverURL = new URL("https://meet.jit.si");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid server URL!");
        }
        JitsiMeetConferenceOptions defaultOptions
                = new JitsiMeetConferenceOptions.Builder()
                .setServerURL(serverURL)
                .setWelcomePageEnabled(false)
                .build();
        JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        getExamTimetableListApi();
    }
    public void resetBgColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mon_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
            tue_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
            wed_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
            thur_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
            fri_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
            sat_text.setBackgroundTintList(getResources().getColorStateList(R.color.gray_600));
        }
    }
    public void onSetBgColor(TextView textView){
        textView.setBackgroundTintList(getResources().getColorStateList(R.color.pink));
    }

//    public void AdapaterNotify(List<Timetable> timetable) {
//        try {
//            if (timetable != null) {
//                recyclerview.setVisibility(View.VISIBLE);
//                txt_no_data.setVisibility(View.GONE);
//                classTimeTableAdapter = new ClassTimeTableAdapter(getActivity(), timetable);
//                recyclerview.setAdapter(classTimeTableAdapter);
//                recyclerview.setHasFixedSize(true);
//                recyclerview.setNestedScrollingEnabled(false);
//            }else {
//                recyclerview.setVisibility(View.GONE);
//                txt_no_data.setVisibility(View.VISIBLE);
//            }
//        } catch (Exception e) {
//
//        }
//
//    }
    private void getExamTimetableListApi() {
        try {
            appUtils.showProgressbar(context);
            Call<ClasTimeTableResponse> call = ApiService.buildService(context).getClassTimeTableList(strAuth,"getTimeTable",strRoleid,strUserId,strSchoolId,strAcademicId,strStandardID,strDivisionId,"0");
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
        Timetable timetable1;
        for(int i=0;i<timetable.size();i++){
            if(timetable.get(i).getDay_Name().contentEquals(dayOfTheWeek)){
                timetable1=new Timetable(timetable.get(i).getSubjectName(), timetable.get(i).getDay_Name(), timetable.get(i).getFirst_Name(),
                        timetable.get(i).getStartTime(), timetable.get(i).getEndTime());
                timetableList.add(timetable1);
            }

        }
        switch (dayOfTheWeek) {
            case "Monday":
                onSetBgColor(mon_text);
                break;
            case "Tuesday":
                onSetBgColor(tue_text);
                break;
            case "Wednesday":
                onSetBgColor(wed_text);
                break;
            case "Thursday":
                onSetBgColor(thur_text);
                break;
            case "Friday":
                onSetBgColor(fri_text);
                break;
            case "Saturday":
                onSetBgColor(sat_text);
                break;
            default:
                break;
        }
        classTimeTableAdapter = new ClassTimeTableAdapter(getActivity(), timetableList);
        recyclerview.setAdapter(classTimeTableAdapter);
        recyclerview.setHasFixedSize(true);
        recyclerview.setNestedScrollingEnabled(false);
        classTimeTableAdapter.setOnClickListener(new ClassTimeTableAdapter.TimetableDataClick() {
            @Override
            public void onTimetableClick(String details, int position) {
                onStartVideo("VCLASSROOMS"+strClassName);
            }
        });
    }

    public void onStartVideo(String strRoomname){
        JitsiMeetConferenceOptions options
                = new JitsiMeetConferenceOptions.Builder()
                .setRoom(strRoomname)
                .build();
        // Launch the new activity with the given options. The launch() method takes care
        // of creating the required Intent and passing the options.
        JitsiMeetActivity.launch(getActivity(), options);
    }
}