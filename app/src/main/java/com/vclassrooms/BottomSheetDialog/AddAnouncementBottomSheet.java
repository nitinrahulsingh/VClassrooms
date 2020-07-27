package com.vclassrooms.BottomSheetDialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.AnnouncementEnum;
import com.vclassrooms.Entity.ApplyLeaveResponse;
import com.vclassrooms.Entity.CommonSuccessResponse;
import com.vclassrooms.Entity.LeaveRequest;
import com.vclassrooms.Entity.LeaveTypeResponse;
import com.vclassrooms.Interface.CommonInterface;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;
import com.vclassrooms.SearchableSpinner.SearchableSpinner;

import java.text.ParseException;
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
 * Created by Rahul on 25,July,2020
 */
public class AddAnouncementBottomSheet extends BottomSheetDialogFragment implements DatePickerDialog.OnDateSetListener {
    View mView;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth, strRoleid, strUserId, strSchoolId, strAcademicId;
    @BindView(R.id.heading_tv)
    TextView heading_tv;
    @BindView(R.id.starttxt_date)
    TextView starttxt_date;
    @BindView(R.id.endtxt_date)
    TextView endtxt_date;
    @BindView(R.id.et_tittle)
    EditText et_tittle;
    @BindView(R.id.et_comment)
    EditText et_comment;
    int DaysCount;
    Calendar calendarStartDate;
    DatePickerDialog startDatePicker,actiondatepicker;
    String lsEndDate, lsStartDate;
    private boolean isEndDate = false;
    List<LeaveTypeResponse.LeaveTypeDetail> leaveTypeDetails = new ArrayList<>();
    CommonInterface commonInterface;
    public AddAnouncementBottomSheet newInstance(Fragment fragment) {
        commonInterface = (CommonInterface) fragment;
        return new AddAnouncementBottomSheet();
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.add_announcement_bottomsheet, container, false);
        ButterKnife.bind(this, mView);
        context = getActivity();
        appUtils = new AppUtils();
        constatnts = new Constatnts();
        init();
        return mView;
    }

    private void init() {
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
        heading_tv.setText("Announcement");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        try {
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = df.format(c);
            starttxt_date.setText(formattedDate);

            calendarStartDate = Calendar.getInstance();
            calendarStartDate.set(year, (month), day);

            endtxt_date.setText(formattedDate);
            lsStartDate = formattedDate;
            lsEndDate = lsStartDate;


            startDatePicker = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
            startDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            startDatePicker.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Locale locale;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        locale = Resources.getSystem().getConfiguration().getLocales().get(0);
                    } else {
                        //noinspection deprecation
                        locale = Resources.getSystem().getConfiguration().locale;
                    }
                    Button buttonNeg = startDatePicker.getButton(DialogInterface.BUTTON_NEGATIVE);
                    buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    Button buttonPos = startDatePicker.getButton(DialogInterface.BUTTON_POSITIVE);
                    buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                }
            });

            actiondatepicker = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
            actiondatepicker.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Locale locale;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        locale = Resources.getSystem().getConfiguration().getLocales().get(0);
                    } else {
                        //noinspection deprecation
                        locale = Resources.getSystem().getConfiguration().locale;
                    }
                    Button buttonNeg = actiondatepicker.getButton(DialogInterface.BUTTON_NEGATIVE);
                    buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    Button buttonPos = actiondatepicker.getButton(DialogInterface.BUTTON_POSITIVE);
                    buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                }
            });
          //  getLeaveTypeApi();

            DaysCount= appUtils.getCountOfDays(starttxt_date.getText().toString(),endtxt_date.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClick({R.id.btnSubmit, R.id.minimize_relative,R.id.linearstartdate,R.id.linearenddate})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnSubmit:
                if (isValidate()){
                    InsertLeaveDetailApi();
                }
                break;
            case R.id.minimize_relative:
                dismiss();
                break;
            case R.id.linearstartdate:
                isEndDate = false;
                startDatePicker.show();
                break;
            case R.id.linearenddate:
                isEndDate = true;
                if(calendarStartDate != null)
                    actiondatepicker.getDatePicker().setMinDate(calendarStartDate.getTimeInMillis());
                actiondatepicker.show();
                break;
        }
    }
    public boolean isValidate() {
        if(!lsStartDate.contentEquals("") && lsStartDate!=null && !lsEndDate.contentEquals("") && lsEndDate!=null)
        {
            lsStartDate=starttxt_date.getText().toString();
            lsEndDate=endtxt_date.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = null;
            try {
                date1 = sdf.parse(starttxt_date.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date date2 = null;
            try {
                date2 = sdf.parse(endtxt_date.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date1.compareTo(date2) > 0) {
                appUtils.showToast(context, getString(R.string.date_error));
                return false;
            } else if (date1.compareTo(date2) < 0) {

            } else if (date1.compareTo(date2) == 0) {
            }
        }
        if(TextUtils.isEmpty(et_comment.getText().toString())){
            appUtils.showToast(context, "Please enter detail");
            return false;
        }
        if(TextUtils.isEmpty(et_tittle.getText().toString())){
            appUtils.showToast(context, "Please enter detail");
            return false;
        }


        return true;
    }

    private void InsertLeaveDetailApi() {
        try {
            AnnouncementEnum announcementEnum=new AnnouncementEnum();
            announcementEnum.setAnnouncement_Id(0);
            announcementEnum.setAnnouncement_Name(et_tittle.getText().toString());
            announcementEnum.setAnnouncement_Details(et_comment.getText().toString());
            announcementEnum.setFromDate(appUtils.convertDateFormat("dd/MM/yyyy","yyyy/MM/dd",starttxt_date.getText().toString()));
            announcementEnum.setToDate(appUtils.convertDateFormat("dd/MM/yyyy","yyyy/MM/dd",endtxt_date.getText().toString()));
            announcementEnum.setNoOfDays(DaysCount);
            announcementEnum.setSchool_Id(Integer.parseInt(strSchoolId));
            announcementEnum.setAcademic_Id(Integer.parseInt(strAcademicId));
            announcementEnum.setInsertedby(Integer.parseInt(strUserId));

            appUtils.showProgressbar(context);
            Call<CommonSuccessResponse> call = ApiService.buildService(context).InsertAnnouncementDetail(strAuth, "insert",announcementEnum);
            call.enqueue(new Callback<CommonSuccessResponse>() {
                @Override
                public void onResponse(Call<CommonSuccessResponse> call, Response<CommonSuccessResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {

                            commonInterface.OnCommonInterfaceClick(0,true);
                                    appUtils.showToast(context, getString(R.string.leave_apply_successfully));
                                    dismiss();



                        } else if (response.body().getStatusCode().equals(1)) {
                            appUtils.showToast(context, getString(R.string.error_message));
                        } else if (response.body().getStatusCode().equals(2)) {
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (isEndDate) {
            lsEndDate = "";
            lsEndDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (month + 1), year);

            if (!TextUtils.isEmpty(lsEndDate))
                endtxt_date.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (month + 1), year));

            lsEndDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (month + 1), year);
        } else {
            lsEndDate = "";
            lsStartDate = "";
            lsStartDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (month + 1), year);

            calendarStartDate = Calendar.getInstance();
            calendarStartDate.set(year, (month), dayOfMonth);

            if (!TextUtils.isEmpty(lsStartDate)){
                starttxt_date.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (month + 1), year));
                endtxt_date.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (month + 1), year));
            }


            lsStartDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (month + 1), year);
            lsEndDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (month + 1), year);

        }
        DaysCount= appUtils.getCountOfDays(starttxt_date.getText().toString(),endtxt_date.getText().toString());

    }

}
