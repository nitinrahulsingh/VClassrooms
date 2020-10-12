package com.vclassrooms.Fragment;

import android.content.Context;
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
import com.vclassrooms.BottomSheetDialog.AddOnlineExamQuestionBottomSheet;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rahul on 27,June,2020
 */
public class AdminHomeFragment extends Fragment {
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
    @BindView(R.id.cardView9)
    CardView cardView9;
    @BindView(R.id.cardView10)
    CardView cardView10;

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
    @BindView(R.id.imageView9)
    ImageView imageView9;
    @BindView(R.id.imageView10)
    ImageView imageView10;

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
    @BindView(R.id.textview9)
    TextView textview9;
    @BindView(R.id.textview10)
    TextView textview10;

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
    String strUserTypeID = "";
    Fragment currentFragment = null;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, mview);
        context = getActivity();
        appUtils = new AppUtils();
        constatnts = new Constatnts();
        init();
        cardView10.setVisibility(View.GONE);
        return mview;
    }

    private void init() {
        strUserTypeID = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        setAllOption();

    }

    private void setAllOption() {
        if (strUserTypeID.contentEquals("5")) {
            //Admin
            relself.setVisibility(View.GONE);
            relTeacher.setVisibility(View.GONE);
            imageView1.setImageResource(R.drawable.attendance);
            textview1.setText("Attendance");
            imageView2.setImageResource(R.drawable.classes_icon);
            textview2.setText("Classes");
            imageView3.setImageResource(R.drawable.leave_icon);
            textview3.setText("Leave");
            imageView4.setImageResource(R.drawable.event_calender);
            textview4.setText("Event Calender");
            imageView5.setImageResource(R.drawable.online_lec);
            textview5.setText("Video Tutorials");
            imageView6.setImageResource(R.drawable.assignment);
            textview6.setText("Assignments");
            imageView7.setImageResource(R.drawable.examtimetable);
            textview7.setText("ExamTimetable");
            imageView8.setImageResource(R.drawable.online_exam);
            textview8.setText("Online Exam");

            imageView9.setImageResource(R.drawable.attendance);
            textview9.setText("Teacher Attendance");
            imageView10.setImageResource(R.drawable.ebook);
            textview10.setText("E-Book");

        }
    }


    public void animation(Fragment fragment) {

        imageViewCurve.setVisibility(View.GONE);


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
        YoYo.with(Techniques.ZoomOut)
                .playOn(cardView9);
        YoYo.with(Techniques.ZoomOut)
                .playOn(cardView10);


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
        YoYo.with(Techniques.SlideOutRight)
                .playOn(cardView9);
        YoYo.with(Techniques.SlideOutRight)
                .playOn(cardView10);
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
                    appUtils.onAddFragment(getActivity(), fragment, R.id.container);
                }
            }
        }, 730);

    }

    @OnClick({R.id.cardView1, R.id.cardView2, R.id.cardView3, R.id.cardView4, R.id.cardView5, R.id.cardView6, R.id.cardView7, R.id.cardView8
            , R.id.cardView9, R.id.cardView10})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.cardView1:
                StandardDivisionListFragment fragment = new StandardDivisionListFragment();
                bundle = new Bundle();
                bundle.putString("Module", constatnts.AttendanceModule);
                fragment.setArguments(bundle);
                animation(fragment);
                break;
            case R.id.cardView2:
                StandardDivisionListFragment standardDivisionListFragment = new StandardDivisionListFragment();
                bundle = new Bundle();
                bundle.putString("Module", constatnts.ClassTimetable);
                standardDivisionListFragment.setArguments(bundle);
                animation(standardDivisionListFragment);
                break;
            case R.id.cardView3:
                LeaveDetailFragment leaveDetailFragment = new LeaveDetailFragment();
                animation(leaveDetailFragment);
                break;
            case R.id.cardView4:
                HolidayFragment holidayFragment = new HolidayFragment();
                animation(holidayFragment);
                break;
            case R.id.cardView5:
                StandardDivisionListFragment standardDivisionListFragment4 = new StandardDivisionListFragment();
                bundle = new Bundle();
                bundle.putString("Module", constatnts.OnlineLec);
                standardDivisionListFragment4.setArguments(bundle);
                animation(standardDivisionListFragment4);
                break;
            case R.id.cardView6:
                StandardDivisionListFragment standardDivisionListFragment1 = new StandardDivisionListFragment();
                bundle = new Bundle();
                bundle.putString("Module", constatnts.Assignment);
                standardDivisionListFragment1.setArguments(bundle);
                animation(standardDivisionListFragment1);
                break;
            case R.id.cardView7:
                StandardDivisionListFragment standardDivisionListFragment2 = new StandardDivisionListFragment();
                bundle = new Bundle();
                bundle.putString("Module", constatnts.ExamTimetable);
                standardDivisionListFragment2.setArguments(bundle);
                animation(standardDivisionListFragment2);
                break;
            case R.id.cardView8:
                StandardDivisionListFragment standardDivisionListFragment3 = new StandardDivisionListFragment();
                bundle = new Bundle();
                bundle.putString("Module", constatnts.OnlineExam);
                standardDivisionListFragment3.setArguments(bundle);
                animation(standardDivisionListFragment3);
                break;
            case R.id.cardView9:
                TeacherListFragment teacherListFragment = new TeacherListFragment();
                animation(teacherListFragment);
                break;
            case R.id.cardView10:
                StandardDivisionListFragment standardDivisionListFragment5 = new StandardDivisionListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Module", constatnts.EBook);
                standardDivisionListFragment5.setArguments(bundle);
                animation(standardDivisionListFragment5);
                break;
        }
    }
}
