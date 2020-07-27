package com.vclassrooms.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.AssignmentResponse;
import com.vclassrooms.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rahul on 08,July,2020
 */
public class TeacherHomeFragment extends Fragment {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;

    //All Cardview
    @BindView(R.id.cardView1)
    CardView cardView1;
    @BindView(R.id.cardView2)
    CardView cardView2;
    @BindView(R.id.cardView3)
    CardView cardView3;
    @BindView(R.id.cardView4)
    CardView cardView4;
    @BindView(R.id.cardView5)
    CardView cardView5;
    @BindView(R.id.cardView6)
    CardView cardView6;
    @BindView(R.id.cardView7)
    CardView cardView7;
    @BindView(R.id.cardView8)
    CardView cardView8;

    //All Imageview
    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.imageView6)
    ImageView imageView6;
    @BindView(R.id.imageView7)
    ImageView imageView7;
    @BindView(R.id.imageView8)
    ImageView imageView8;

    //All Textview
    @BindView(R.id.textview1)
    TextView textview1;
    @BindView(R.id.textview2)
    TextView textview2;
    @BindView(R.id.textview3)
    TextView textview3;
    @BindView(R.id.textview4)
    TextView textview4;
    @BindView(R.id.textview5)
    TextView textview5;
    @BindView(R.id.textview6)
    TextView textview6;
    @BindView(R.id.textview7)
    TextView textview7;
    @BindView(R.id.textview8)
    TextView textview8;

    @BindView(R.id.linearfour)
    LinearLayout linearfour;
    @BindView(R.id.linearFive)
    LinearLayout linearFive;

    @BindView(R.id.lineartab)
    LinearLayout lineartab;
    @BindView(R.id.relself)
    RelativeLayout relself;
    @BindView(R.id.selfImg)
    ImageView selfImg;
    @BindView(R.id.selfTxt)
    TextView selfTxt;
    @BindView(R.id.relTeacher)
    RelativeLayout relTeacher;
    @BindView(R.id.TeacherImg)
    ImageView TeacherImg;
    @BindView(R.id.teacherTxt)
    TextView teacherTxt;
    @BindView(R.id.imageViewCurve)
    ImageView imageViewCurve;
    @BindView(R.id.linearPrayerAndSaint)
    LinearLayout linearPrayerAndSaint;
    String strUserTypeID="";
    Fragment currentFragment = null;
    boolean isSelfTab=true;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.fragment_home,null);
        ButterKnife.bind(this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        linearFive.setVisibility(View.GONE);

        init();

        return mview;
    }

    private void init() {
        strUserTypeID=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_USERTYPEID);
        setAllOption();
        relTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelfTab=false;
                lineartab.setVisibility(View.VISIBLE);
                linearfour.setVisibility(View.GONE);
                cardView6.setVisibility(View.VISIBLE);
                imageView1.setImageResource(R.drawable.home_calender);
                textview1.setText("Attendance");
                imageView2.setImageResource(R.drawable.home_classes);
                textview2.setText("Online Lec");
                imageView3.setImageResource(R.drawable.home_classes);
                textview3.setText("Event Calendar");
                imageView4.setImageResource(R.drawable.e_study);
                textview4.setText("Exam Timetable");
                imageView5.setImageResource(R.drawable.e_study);
                textview5.setText("Online Exam");
                imageView6.setImageResource(R.drawable.home_classes);
                textview6.setText("My Classes");

                selfTxt.setTypeface(Typeface.DEFAULT);
                selfTxt.setTextColor(context.getResources().getColor(R.color.colorGrey));
                teacherTxt.setTextColor(context.getResources().getColor(R.color.gray_300));

//            selfImg.setImageResource(R.drawable.teacher_grayicon);
//            TeacherImg.setImageResource(R.drawable.teacher_grayicon);
            }
        });
        relself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelfTab=true;
                lineartab.setVisibility(View.VISIBLE);
                linearfour.setVisibility(View.GONE);
                cardView6.setVisibility(View.GONE);
                imageView1.setImageResource(R.drawable.home_attendance);
                textview1.setText("Attendance");
                imageView2.setImageResource(R.drawable.home_classes);
                textview2.setText("My Classes");
                imageView3.setImageResource(R.drawable.home_classes);
                textview3.setText("Leave");
                imageView4.setImageResource(R.drawable.e_study);
                textview4.setText("E-Books");
                imageView5.setImageResource(R.drawable.e_study);
                textview5.setText("Assignments");

                selfTxt.setTypeface(Typeface.DEFAULT);
                selfTxt.setTextColor(context.getResources().getColor(R.color.gray_300));
                teacherTxt.setTextColor(context.getResources().getColor(R.color.colorGrey));

//            selfImg.setImageResource(R.drawable.teacher_grayicon);
//            TeacherImg.setImageResource(R.drawable.teacher_grayicon);
            }
        });

    }

    private void setAllOption() {
        if(strUserTypeID.contentEquals("3")){
            //Teacher
            boolean isSelfTab=true;
            linearfour.setVisibility(View.GONE);
            cardView6.setVisibility(View.GONE);
            lineartab.setVisibility(View.VISIBLE);
            imageView1.setImageResource(R.drawable.home_attendance);
            textview1.setText("Attendance");
            imageView2.setImageResource(R.drawable.home_classes);
            textview2.setText("My Classes");
            imageView3.setImageResource(R.drawable.home_classes);
            textview3.setText("Leave");
            imageView4.setImageResource(R.drawable.e_study);
            textview4.setText("E-Books");
            imageView5.setImageResource(R.drawable.e_study);
            textview5.setText("Assignments");

            selfTxt.setTypeface(Typeface.DEFAULT);
            selfTxt.setTextColor(context.getResources().getColor(R.color.gray_300));
            teacherTxt.setTextColor(context.getResources().getColor(R.color.colorGrey));

//            selfImg.setImageResource(R.drawable.teacher_grayicon);
//            TeacherImg.setImageResource(R.drawable.teacher_grayicon);

        }
    }


    public void animation(Fragment fragment) {

        imageViewCurve.setVisibility(View.GONE);

        YoYo.with(Techniques.SlideOutUp)
                .playOn(linearPrayerAndSaint);

        YoYo.with(Techniques.ZoomOut)
                .playOn(cardView1);

        YoYo.with(Techniques.ZoomOut)
                .playOn(cardView2);

        YoYo.with(Techniques.ZoomOut)
                .playOn(cardView3);


        YoYo.with(Techniques.ZoomOut)
                .playOn(cardView4);

        YoYo.with(Techniques.ZoomOut)
                .playOn(cardView5);

        YoYo.with(Techniques.ZoomOut)
                .playOn(cardView6);
        YoYo.with(Techniques.ZoomOut)
                .playOn(cardView7);
        YoYo.with(Techniques.ZoomOut)
                .playOn(cardView8);


        YoYo.with(Techniques.SlideOutLeft)
                .playOn(cardView1);
        YoYo.with(Techniques.SlideOutLeft)
                .playOn(cardView2);
        YoYo.with(Techniques.SlideOutLeft)
                .playOn(cardView3);
        YoYo.with(Techniques.SlideOutRight)
                .playOn(cardView4);
        YoYo.with(Techniques.SlideOutRight)
                .playOn(cardView5);
        YoYo.with(Techniques.SlideOutRight)
                .playOn(cardView6);
        YoYo.with(Techniques.SlideOutRight)
                .playOn(cardView7);
        YoYo.with(Techniques.SlideOutRight)
                .playOn(cardView8);
        //transitionActivityDelay(fragment);
        transitionActivity(fragment);
    }

    private void transitionActivity(final Fragment fragment) {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                assert getFragmentManager() != null;
                currentFragment = getFragmentManager().findFragmentById(R.id.container);
                if (fragment.getClass().getSimpleName().equals(currentFragment + "")) {

                } else {
                    appUtils.onAddFragment(getActivity(),fragment,R.id.container);
                }
            }
        }, 730);

    }
    @OnClick({R.id.cardView1,R.id.cardView2,R.id.cardView3,R.id.cardView4,R.id.cardView5,R.id.cardView6})
    public void onClick(View v) {
        Bundle bundle;
        if(cardView6.getVisibility()==View.VISIBLE){
            isSelfTab=false;
        }else {
            isSelfTab=true;
        }
        switch (v.getId()) {
            default:
                break;
            case R.id.cardView1:
                if(isSelfTab){
                    SelfAttendanceFragment fragment = new SelfAttendanceFragment();
                    bundle=new Bundle();
                    bundle.putString("isEmployeeSelf","True");
                    fragment.setArguments(bundle);
                    animation(fragment);
                }else {
                    StandardDivisionListFragment fragment = new StandardDivisionListFragment();
                    bundle=new Bundle();
                    bundle.putString("Module",constatnts.AttendanceModule);
                    fragment.setArguments(bundle);
                    animation(fragment);
                }

                break;
            case R.id.cardView2:
                if(isSelfTab){
                    TeacherTimeTable classTimeTableFragment = new TeacherTimeTable();
                    animation(classTimeTableFragment);
                }else {
                    StandardDivisionListFragment fragment = new StandardDivisionListFragment();
                    bundle=new Bundle();
                    bundle.putString("Module",constatnts.OnlineLec);
                    fragment.setArguments(bundle);
                    animation(fragment);
                }
                break;
            case R.id.cardView3:
                if (isSelfTab){
                    LeaveDetailFragment leaveDetailFragment = new LeaveDetailFragment();
                    animation(leaveDetailFragment);
                }else {
                    HolidayFragment holidayFragment = new HolidayFragment();
                    animation(holidayFragment);
                }
                break;
            case R.id.cardView4:
                if (isSelfTab){
                    StandardDivisionListFragment standardDivisionListFragment2 = new StandardDivisionListFragment();
                    bundle=new Bundle();
                    bundle.putString("Module",constatnts.EBook);
                    standardDivisionListFragment2.setArguments(bundle);
                    animation(standardDivisionListFragment2);
                }else {
                    StandardDivisionListFragment standardDivisionListFragment2 = new StandardDivisionListFragment();
                    bundle=new Bundle();
                    bundle.putString("Module",constatnts.ExamTimetable);
                    standardDivisionListFragment2.setArguments(bundle);
                    animation(standardDivisionListFragment2);
                }
                break;
            case R.id.cardView5:
                if (isSelfTab){
                    StandardDivisionListFragment fragment = new StandardDivisionListFragment();
                    bundle=new Bundle();
                    bundle.putString("Module",constatnts.Assignment);
                    fragment.setArguments(bundle);
                    animation(fragment);
                }else {
                    StandardDivisionListFragment fragment = new StandardDivisionListFragment();
                    bundle=new Bundle();
                    bundle.putString("Module",constatnts.OnlineExam);
                    fragment.setArguments(bundle);
                    animation(fragment);
                }
                break;
            case R.id.cardView6:
                if (isSelfTab){

                }else {
                    StandardDivisionListFragment fragment = new StandardDivisionListFragment();
                    bundle=new Bundle();
                    bundle.putString("Module",constatnts.ClassTimetable);
                    fragment.setArguments(bundle);
                    animation(fragment);
                }
                break;
        }
    }

}
