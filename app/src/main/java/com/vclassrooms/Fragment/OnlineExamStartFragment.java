package com.vclassrooms.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vclassrooms.Adapter.AssignmentAdapter;
import com.vclassrooms.Adapter.OnlineExamAdapter;
import com.vclassrooms.Adapter.OnlineExamStartAdapter;
import com.vclassrooms.BottomSheetDialog.AddOnlineExamQuestionBottomSheet;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.AssignmentResponse;
import com.vclassrooms.Entity.Question;
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
 * Created by Rahul on 25,July,2020
 */
public class OnlineExamStartFragment extends Fragment {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    LinearLayoutManager mLayoutManager;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
     @BindView(R.id.start_txt)
     TextView start_txt;
     @BindView(R.id.startexam_relative)
     RelativeLayout startexam_relative;
      @BindView(R.id.time_tv)
     TextView time_tv;
       @BindView(R.id.btnSubmit)
       AppCompatButton btnSubmit;
        @BindView(R.id.ttquestion_tv)
       TextView ttquestion_tv;
    String strQusetionAnsSet="",Tittle="",PassingMark="",TotalMark="",TotalTime="";
    OnlineExamStartAdapter onlineExamAdapter;
    List<Question> questionList;
    List<Question> questionAnsList;
    int MarksOptain=0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.online_exam_start_fragment,null);
        ButterKnife.bind(OnlineExamStartFragment.this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        btnSubmit.setVisibility(View.GONE);
        init();
        return mview;
    }
    private void init() {
        startexam_relative.setVisibility(View.VISIBLE);
        mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
        strQusetionAnsSet=getArguments().getString("QuestionAnswerSet");
        Tittle=getArguments().getString("Tittle");
        PassingMark=getArguments().getString("PassingMark");
        TotalMark=getArguments().getString("TotalMark");
        TotalTime=getArguments().getString("TotalTime");
        questionList = new Gson().fromJson( strQusetionAnsSet, new TypeToken<List<Question>>() {
        }.getType());
        questionAnsList=questionList;
        Log.e("qusetion",new Gson().toJson(questionList));
        ttquestion_tv.setText("Total:Q"+questionList.size());
        start_txt.setText(Tittle+"\n\n"+"Touch screen to Begin Exam");

    }
    @OnClick({R.id.startexam_relative,R.id.btnSubmit})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.startexam_relative:
                String h=TotalTime.replace("hrs","").trim();
                String[] h1=h.split(":");

                int hour=Integer.parseInt(h1[0]);
                int minute=Integer.parseInt(h1[1]);

                int temp;
                temp = (60 * minute) + (3600 * hour);
                startexam_relative.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.VISIBLE);
                onlineExamAdapter = new OnlineExamStartAdapter(getActivity(),questionList);
                recyclerview.setAdapter(onlineExamAdapter);
                recyclerview.setHasFixedSize(true);
                recyclerview.setNestedScrollingEnabled(false);
                 examTimer(temp);
                break;
            case R.id.btnSubmit:
              onSumbitAns();
                break;

        }
    }
    public  void showAlertDialogOneButton(Context context, String title, String message) {
        if (!((Activity) context).isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);

            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    OnlineExamFragment onlineExamFragment=new OnlineExamFragment();
                    appUtils.onAddFragment(getActivity(),onlineExamFragment,R.id.frame_layout);

                }
            });

            alertDialog.show();
        }
    }
    private void onSumbitAns() {
        List<Question> ansList=((OnlineExamStartAdapter)onlineExamAdapter).getSubmitedAns();
        for(int i=0;i<ansList.size();i++) {
            Question questionans = ansList.get(i);
            if(questionans.getAnswer().contentEquals(questionAnsList.get(i).getAnswer())){
                MarksOptain=MarksOptain+1;
            }else {

            }
        }

        String totalMarksOptain= String.valueOf(appUtils.getIntRoundoffString(String.valueOf(MarksOptain*(Integer.parseInt(TotalMark)/ansList.size()))));
        String strMarksOptaimMsg="You have scored "+totalMarksOptain+" out of"+TotalMark;
       showAlertDialogOneButton(context,"Online Exam",strMarksOptaimMsg);
    }

    public void examTimer(int Seconds){

        new CountDownTimer(Seconds* 1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                time_tv.setText("Time " +String.format("%02d", minutes)+"min"+ String.format("%02d", seconds)+"sec");
            }

            public void onFinish() {
                appUtils.showAlertDialogOneButton(context,"Online Exam","Your Exam time is Finish and Paper will get Sumbitted");
                onSumbitAns();
            }
        }.start();
    }
}
