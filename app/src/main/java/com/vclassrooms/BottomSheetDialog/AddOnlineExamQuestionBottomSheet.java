package com.vclassrooms.BottomSheetDialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.vclassrooms.Adapter.CommonAllParentChildAdapter;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.ApplyLeaveResponse;
import com.vclassrooms.Entity.CommonSuccessResponse;
import com.vclassrooms.Entity.LeaveRequest;
import com.vclassrooms.Entity.OnlineExamEnum;
import com.vclassrooms.Entity.Question;
import com.vclassrooms.Entity.StandardDivisionResponse;
import com.vclassrooms.Entity.SubjectDetailsResponse;
import com.vclassrooms.Interface.CommonInterface;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;
import com.vclassrooms.SearchableSpinner.SearchableSpinner;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 22,July,2020
 */
public class AddOnlineExamQuestionBottomSheet extends BottomSheetDialogFragment {
    View mView;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth, strRoleid, strUserId, strSchoolId, strAcademicId;
    @BindView(R.id.heading_tv)
    TextView heading_tv;
    @BindView(R.id.btnprev)
    AppCompatButton btnprev;
     @BindView(R.id.btnnext)
    AppCompatButton btnnext;
     @BindView(R.id.questionNo_tv)
    TextView questionNo_tv;
     @BindView(R.id.et_question)
     EditText et_question;
     @BindView(R.id.et_optiona)
     EditText et_optiona;
     @BindView(R.id.et_optionb)
     EditText et_optionb;
     @BindView(R.id.et_optionc)
     EditText et_optionc;
     @BindView(R.id.et_optiond)
     EditText et_optiond;
     @BindView(R.id.aRadio)
     RadioButton aRadio;
     @BindView(R.id.bRadio)
     RadioButton bRadio;
     @BindView(R.id.cRadio)
     RadioButton cRadio;
     @BindView(R.id.dRadio)
     RadioButton dRadio;
     @BindView(R.id.save_layout)
     FrameLayout save_layout;
     @BindView(R.id.quest_ans_layout)
     LinearLayout quest_ans_layout;
     @BindView(R.id.et_tittle)
     EditText et_tittle;
     @BindView(R.id.spinner)
     SearchableSpinner std_spinner;
     @BindView(R.id.spinner2)
     SearchableSpinner sub_spinner;
     @BindView(R.id.et_Time)
     TextView et_Time;
     @BindView(R.id.et_TotalMarks)
     EditText et_TotalMarks;
     @BindView(R.id.et_passingMarks)
     EditText et_passingMarks;
     @BindView(R.id.std_sub_time_detail_linear)
     LinearLayout std_sub_time_detail_linear;
     @BindView(R.id.btnSubmit)
     AppCompatButton btnSubmit;

    int currentQuestion = 1;
    int previousQuestion = 1;
    ArrayList<Question> ques;
    String selectedOption = "";

    List<StandardDivisionResponse.Division> mStandardDataList = new ArrayList<>();
    List<SubjectDetailsResponse.Subject> subjectList = new ArrayList<>();
    String strStandardId,strSubjectId,strDivisionId,strQuestionAns="",strTotalTime;

    private BottomSheetBehavior mBehavior;
    CommonInterface commonInterface;
    public AddOnlineExamQuestionBottomSheet newInstance(Fragment fragment) {
        commonInterface = (CommonInterface) fragment;
        return new AddOnlineExamQuestionBottomSheet();
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = View.inflate(getContext(), R.layout.add_online_exam_question, null);

        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        ButterKnife.bind(this,dialog);
        context = getActivity();
        appUtils = new AppUtils();
        constatnts = new Constatnts();
        quest_ans_layout.setVisibility(View.VISIBLE);
        questionNo_tv.setVisibility(View.VISIBLE);
        save_layout.setVisibility(View.VISIBLE);
        std_sub_time_detail_linear.setVisibility(View.GONE);
        init();
        setListeners();
        getStandardDetailApi();
        std_spinner.setHint("Select Standard");
        sub_spinner.setHint("Select Subject");
        std_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strStandardId=mStandardDataList.get(position).getStandardId().toString();
                strDivisionId=mStandardDataList.get(position).getDivisionId().toString();
                getSubjectApi();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sub_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strSubjectId=subjectList.get(position).getSubjectId().toString();

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        et_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog picker = new TimePickerDialog(getContext(),android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                et_Time.setText(sHour+" hr:"+sMinute+" min");
                                strTotalTime=sHour+":"+String.valueOf(sMinute);
                            }
                        }, 2, 3, true);
                picker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                picker .show();
            }
        });
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return new Dialog(getActivity(), getTheme()){
//            @Override
//            public void onBackPressed() {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setMessage("If you exist test will get sumbit");
//                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                builder.show();
//            }
//        };
//
//    }
//

    private void init() {
        ques = new ArrayList<>();
        selectedOption = "";
        currentQuestion = 1;
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
        heading_tv.setText("Online Exam");

        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(previousQuestion>1) {
                    previousQuestion--;
                    setAllData(previousQuestion);
                }
                if(previousQuestion==1)
                    btnprev.setVisibility(View.INVISIBLE);
                //Question question1 = new Question();
                Toast.makeText(context, String.valueOf(previousQuestion), Toast.LENGTH_SHORT).show();
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(previousQuestion!=currentQuestion) {
                    previousQuestion++;
                    if(previousQuestion!=currentQuestion)
                        setAllData(previousQuestion);
                    else {
                        clearAllData();
                        questionNo_tv.setText(String.valueOf("Q"+currentQuestion));
                    }
                    if(previousQuestion>1)
                        btnprev.setVisibility(View.VISIBLE);
                }
                boolean cont = getEnteredQuestionsValue();
                if (cont)
                {
                    previousQuestion++;
                    currentQuestion++;
                    Toast.makeText(context, "QUESTION " + currentQuestion, Toast.LENGTH_SHORT).show();
                    questionNo_tv.setText(String.valueOf("Q"+currentQuestion));
                    clearAllData();
                    btnprev.setVisibility(View.VISIBLE);
                }
            }
        });
        save_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean cont = getEnteredQuestionsValue();
                if (cont) {
                    //Log.e("QuestionJson", new Gson().toJson(ques));
                    OnQuestionAnsSetComplete();

                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid()){
                    InsertLeaveDetailApi();
                }
            }
        });
    }
    public boolean isValid(){
        if(TextUtils.isEmpty(et_tittle.getText().toString())){
            appUtils.showToast(context,"Please enter detail");
            return false;
        }
        if(TextUtils.isEmpty(et_Time.getText().toString())){
            appUtils.showToast(context,"Please enter total time of exam");
            return false;
        }
        if(TextUtils.isEmpty(et_TotalMarks.getText().toString())){
            appUtils.showToast(context,"Please enter total marks of exam");
            return false;
        }
        if(TextUtils.isEmpty(et_passingMarks.getText().toString())){
            appUtils.showToast(context,"Please enter passing marks of exam");
            return false;
        }
        if(strStandardId==null && TextUtils.isEmpty(strStandardId)){
            appUtils.showToast(context,"Please select standard");
            return false;
        }if(strSubjectId==null && TextUtils.isEmpty(strSubjectId)){
            appUtils.showToast(context,"Please select subject");
            return false;
        }
        return true;
    }
    private boolean getEnteredQuestionsValue() {
        boolean cont = false;
        if (TextUtils.isEmpty(et_question.getText().toString().trim())) {
            et_question.setError("Please fill in a question");
        }
        else if (TextUtils.isEmpty(et_optiona.getText().toString().trim())) {
            et_optiona.setError("Please fill in option A");
        }
        else if (TextUtils.isEmpty(et_optionb.getText().toString().trim())) {
            et_optionb.setError("Please fill in option B");
        }
        else if (TextUtils.isEmpty(et_optionc.getText().toString().trim())) {
            et_optionc.setError("Please fill in option C");
        }
        else if (TextUtils.isEmpty(et_optiond.getText().toString().trim())) {
            et_optiond.setError("Please fill in option D");
        }
        else if (selectedOption.equals("")) {
            appUtils.showToast(context,"Please select the correct answer");
        }
        else {
            Question quest = new Question();
            quest.setId(currentQuestion);
            quest.setQuestion(et_question.getText().toString());
            quest.setOpt_A(et_optiona.getText().toString());
            quest.setOpt_B(et_optionb.getText().toString());
            quest.setOpt_C(et_optionc.getText().toString());
            quest.setOpt_D(et_optiond.getText().toString());
            quest.setAnswer(selectedOption);
            ques.add(quest);
            cont = true;
        }
        return cont;
    }
    public void setAllData(int position) {
        clearAllData();
        Question question1 = new Question();
        question1 = ques.get(position-1);
        questionNo_tv.setText(String.valueOf("Q"+question1.getId()));
        et_question.setText(question1.getQuestion());
        et_optiona.setText(question1.getOpt_A());
        et_optionb.setText(question1.getOpt_B());
        et_optionc.setText(question1.getOpt_C());
        et_optiond.setText(question1.getOpt_D());
        switch (question1.getAnswer()){
            case "A":
                aRadio.setChecked(true);
                break;
            case "B":
                bRadio.setChecked(true);
                break;
            case "C":
                cRadio.setChecked(true);
                break;
            case "D":
                dRadio.setChecked(true);
                break;
        }
    }
    private void clearAllData() {

        aRadio.setChecked(false);
        bRadio.setChecked(false);
        cRadio.setChecked(false);
        dRadio.setChecked(false);
        et_optiona.setText(null);
        et_optionb.setText(null);
        et_optionc.setText(null);
        et_optiond.setText(null);
        et_question.setText(null);
        selectedOption = "";
    }
    private void setListeners() {
        aRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "A";
                bRadio.setChecked(false);
                cRadio.setChecked(false);
                dRadio.setChecked(false);
            }
        });
        bRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "B";
                aRadio.setChecked(false);
                cRadio.setChecked(false);
                dRadio.setChecked(false);
            }
        });
        cRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "C";
                bRadio.setChecked(false);
                aRadio.setChecked(false);
                dRadio.setChecked(false);
            }
        });
        dRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "D";
                bRadio.setChecked(false);
                cRadio.setChecked(false);
                aRadio.setChecked(false);
            }
        });

    }
    public void OnQuestionAnsSetComplete(){
        quest_ans_layout.setVisibility(View.GONE);
        questionNo_tv.setVisibility(View.GONE);
        save_layout.setVisibility(View.GONE);
        std_sub_time_detail_linear.setVisibility(View.VISIBLE);


    }
    private void getStandardDetailApi() {
        try {
            appUtils.showProgressbar(context);
            Call<StandardDivisionResponse> call = ApiService.buildService(context).getStandardDivisionList(strAuth, "GetStandardDivision", strRoleid, strUserId, strSchoolId, strAcademicId, "0");
            call.enqueue(new Callback<StandardDivisionResponse>() {
                @Override
                public void onResponse(Call<StandardDivisionResponse> call, Response<StandardDivisionResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if (response.body().getData().getDivision() != null) {
                                mStandardDataList = response.body().getData().getDivision();
                                ArrayAdapter<StandardDivisionResponse.Division> adapter =
                                        new ArrayAdapter<StandardDivisionResponse.Division>(context, android.R.layout.simple_spinner_item, mStandardDataList);
                                std_spinner.setAdapter(adapter);
                                strStandardId=mStandardDataList.get(0).getStandardId().toString();
                                strDivisionId=mStandardDataList.get(0).getDivisionId().toString();
                            } else {
                                appUtils.showToast(context, getString(R.string.no_data));
                            }
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
                public void onFailure(Call<StandardDivisionResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
    private void getSubjectApi() {
        try {
            appUtils.showProgressbar(context);
            Call<SubjectDetailsResponse> call = ApiService.buildService(context).getSubjectList(strAuth, "getSubject", strRoleid, strUserId, strSchoolId, strAcademicId, strStandardId);
            call.enqueue(new Callback<SubjectDetailsResponse>() {
                @Override
                public void onResponse(Call<SubjectDetailsResponse> call, Response<SubjectDetailsResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if (response.body().getData().getSubject() != null && response.body().getData().getSubject().size()>0) {
                                subjectList = response.body().getData().getSubject();
                                ArrayAdapter<SubjectDetailsResponse.Subject> adapter =
                                        new ArrayAdapter<SubjectDetailsResponse.Subject>(context, android.R.layout.simple_spinner_item, subjectList);
                                sub_spinner.setAdapter(adapter);
                                strSubjectId=subjectList.get(0).getSubjectId().toString();
                            } else {
                                appUtils.showToast(context, getString(R.string.no_data));
                            }
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
                public void onFailure(Call<SubjectDetailsResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void InsertLeaveDetailApi() {
        try {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c);
            String totaltime=formattedDate+" "+strTotalTime+":00";
            strQuestionAns=new Gson().toJson(ques);
            OnlineExamEnum onlineExamEnum=new OnlineExamEnum();
            onlineExamEnum.setDivision_Id(Integer.parseInt(strDivisionId));
            onlineExamEnum.setRole_Id(Integer.parseInt(strRoleid));
            onlineExamEnum.setUser_Id(Integer.parseInt(strUserId));
            onlineExamEnum.setSchool_Id(Integer.parseInt(strSchoolId));
            onlineExamEnum.setAcademic_Id(Integer.parseInt(strAcademicId));
            onlineExamEnum.setSubject_Id(Integer.parseInt(strSubjectId));
            onlineExamEnum.setTitle(et_tittle.getText().toString());
            onlineExamEnum.setQuestion_Answer(strQuestionAns);
            onlineExamEnum.setStandard_Id(Integer.parseInt(strStandardId));
            onlineExamEnum.setTotal_Mark(Integer.parseInt(et_TotalMarks.getText().toString()));
            onlineExamEnum.setPassing_Mark(Integer.parseInt(et_passingMarks.getText().toString()));
            onlineExamEnum.setTotal_Time(totaltime);

            appUtils.showProgressbar(context);
            Call<CommonSuccessResponse> call = ApiService.buildService(context).InsertOnlineExamination(strAuth, "Insert",onlineExamEnum);
            call.enqueue(new Callback<CommonSuccessResponse>() {
                @Override
                public void onResponse(Call<CommonSuccessResponse> call, Response<CommonSuccessResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                                    appUtils.showToast(context, getString(R.string.success));
                            commonInterface.OnCommonInterfaceClick(0,true);
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
}
