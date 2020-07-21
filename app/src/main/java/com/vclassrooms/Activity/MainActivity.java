package com.vclassrooms.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.vclassrooms.Adapter.ExpandableListAdapter;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Fragment.AdminHomeFragment;
import com.vclassrooms.Fragment.ChangePasswordFragment;
import com.vclassrooms.Fragment.E_BooksFragment;
import com.vclassrooms.Fragment.GalleryFragment;
import com.vclassrooms.Fragment.ParentProfileFragment;
import com.vclassrooms.Fragment.ParentStudentHomeFragment;
import com.vclassrooms.Fragment.ProfileFragment;
import com.vclassrooms.Fragment.StaffDirectoryFragment;
import com.vclassrooms.Fragment.StudentDirecoryFragment;
import com.vclassrooms.Fragment.StudentHomeFragment;
import com.vclassrooms.Fragment.TeacherDirectoryFragment;
import com.vclassrooms.Fragment.TeacherHomeFragment;
import com.vclassrooms.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rahul on 25,June,2020
 */
public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtSchoolName)
    TextView txtSchoolName;
    @BindView(R.id.txtYear)
    TextView txtYear;
    @BindView(R.id.linearProfile)
    RelativeLayout linearProfile;
    @BindView(R.id.lv_drawer)
    ExpandableListView lv_drawer;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.e_books_relative)
    RelativeLayout e_books_relative;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    ActionBarDrawerToggle toggle;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            ButterKnife.bind(this);
            context = MainActivity.this;
            appUtils = new AppUtils();
            constatnts = new Constatnts();
            strAuth=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_FCM);
            strRoleid=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_USERTYPEID);
            strUserId=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_USERID);
            strSchoolId=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_SCHOOLID);
            strAcademicId=appUtils.getStringPrefrences(context,constatnts.SH_APPPREF,constatnts.SH_ACADEMICYEAR);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.bringToFront();
           // txtSchoolName.setText(appUtils.getStringPrefrences(MainActivity.this, constatnts.SH_APPPREF, constatnts.SH_SCHOOLNAME));
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            toggle = new
                    ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                            R.string.navigation_drawer_close) {
                        @Override
                        public void onDrawerSlide(View drawerView, float slideOffset) {
                            appUtils.hideSoftKeyboard(MainActivity.this);
                        }
                    };
            drawer.addDrawerListener(toggle);

            toggle.syncState();
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.hamburger_icon);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));
            }

//            txtName.setText(appUtils.getStringPrefrences(MainActivity.this, constatnts.SH_APPPREF, constatnts.SH_USERFIRSTNAME) + " "
//                    + appUtils.getStringPrefrences(MainActivity.this, constatnts.SH_APPPREF, constatnts.SH_USERLASTNAME));
//            txtYear.setText(appUtils.getStringPrefrences(MainActivity.this, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR));

            prepareListData();
            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

            // setting list adapter
            lv_drawer.setAdapter(listAdapter);

            lv_drawer.setGroupIndicator(null);
            //Expandable listview Click events are handle according to their position
            ExpandableListviewClicks();
            if(strRoleid.contentEquals("5")){
                //Admin
                e_books_relative.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new AdminHomeFragment()).commitAllowingStateLoss();
                drawer_layout.closeDrawer(GravityCompat.START);
            }else if(strRoleid.contentEquals("3")){
                //teacher
                e_books_relative.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new TeacherHomeFragment()).commitAllowingStateLoss();
                drawer_layout.closeDrawer(GravityCompat.START);
            }else if(strRoleid.contentEquals("2")){
                //Student
                e_books_relative.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new StudentHomeFragment()).commitAllowingStateLoss();
                drawer_layout.closeDrawer(GravityCompat.START);
            }else if(strRoleid.contentEquals("1")){
                //Parent
                e_books_relative.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ParentStudentHomeFragment()).commitAllowingStateLoss();
                drawer_layout.closeDrawer(GravityCompat.START);
            }

            e_books_relative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new E_BooksFragment()).commitAllowingStateLoss();
                    drawer_layout.closeDrawer(GravityCompat.START);
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void prepareListData() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add(getString(R.string.Home));
        listDataHeader.add(getString(R.string.dashboard));
        listDataHeader.add(getString(R.string.directory));
        listDataHeader.add(getString(R.string.Profile));
        listDataHeader.add(getString(R.string.gallery));
        listDataHeader.add(getString(R.string.Setting));
        listDataHeader.add(getString(R.string.Logout));

        // Adding home child
        List<String> homeChild = new ArrayList<String>();
        List<String> dashboardChild = new ArrayList<String>();
        List<String> galleryChild = new ArrayList<String>();
        List<String> directoriesChild = new ArrayList<String>();
        if(strRoleid.contentEquals("5")||strRoleid.contentEquals("3")){
            directoriesChild.add(getString(R.string.student));
        }
//        directoriesChild.add(getString(R.string.parent));
        directoriesChild.add(getString(R.string.teacher));
        directoriesChild.add(getString(R.string.staff));

        List<String> profileChild = new ArrayList<String>();
        //adding setting child
        List<String> settingChild = new ArrayList<String>();
        settingChild.add(getString(R.string.change_password));
        List<String> homeLogout = new ArrayList<String>();

        listDataChild.put(listDataHeader.get(0), homeChild);
        listDataChild.put(listDataHeader.get(1), dashboardChild);
        listDataChild.put(listDataHeader.get(2), directoriesChild);
        listDataChild.put(listDataHeader.get(3), profileChild);
        listDataChild.put(listDataHeader.get(4), galleryChild);
        listDataChild.put(listDataHeader.get(5), settingChild);
        listDataChild.put(listDataHeader.get(6), homeLogout);


    }

    public void onTwiceClick() {
        if (doubleBackToExitPressedOnce) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Are you sure want to Exit?");

            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });

            alertDialog.show();

            return;
        }

        this.doubleBackToExitPressedOnce = true;
        appUtils.showToast(MainActivity.this, getString(R.string.Please_click_back_again_to_exit));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    /*
     * ExpandableListview Click listener are handle
     * here for their groups and their child click also handled
     */
    private void ExpandableListviewClicks() {

        // Listview Group expanded listener
        lv_drawer.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

                if (listDataHeader.get(groupPosition).contentEquals(getString(R.string.Home))) {
                    if(strRoleid.contentEquals("5")){
                        //Admin
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new AdminHomeFragment()).commitAllowingStateLoss();
                        drawer_layout.closeDrawer(GravityCompat.START);
                    }else if(strRoleid.contentEquals("3")){
                        //teacher
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new TeacherHomeFragment()).commitAllowingStateLoss();
                        drawer_layout.closeDrawer(GravityCompat.START);
                    }else if(strRoleid.contentEquals("2")){
                        //Student
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ParentStudentHomeFragment()).commitAllowingStateLoss();
                        drawer_layout.closeDrawer(GravityCompat.START);
                    }else if(strRoleid.contentEquals("1")){
                        //Parent
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ParentStudentHomeFragment()).commitAllowingStateLoss();
                        drawer_layout.closeDrawer(GravityCompat.START);
                    }
                } else if (listDataHeader.get(groupPosition).contentEquals(getString(R.string.dashboard))) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new AdminHomeFragment()).commitAllowingStateLoss();
                    drawer_layout.closeDrawer(GravityCompat.START);
                } else if (listDataHeader.get(groupPosition).contentEquals(getString(R.string.Profile))) {
                    if(strRoleid.contentEquals("1")){
                        //parent
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ParentProfileFragment()).commitAllowingStateLoss();
                        drawer_layout.closeDrawer(GravityCompat.START);
                    }else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commitAllowingStateLoss();
                        drawer_layout.closeDrawer(GravityCompat.START);
                    }

                }
                else if (listDataHeader.get(groupPosition).contentEquals(getString(R.string.Logout))) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Are you sure");
                    alertDialog.setMessage("Are you sure want to Logout?");

                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            try {
                               appUtils.removeAllPrefrences(MainActivity.this, constatnts.SH_APPPREF);
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } catch (Exception ex) {

                            }
                        }
                    });

                    alertDialog.show();
                } else if (listDataHeader.get(groupPosition).contentEquals(getString(R.string.gallery))) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new GalleryFragment()).commitAllowingStateLoss();
                    drawer_layout.closeDrawer(GravityCompat.START);
                }

            }
        });


        // Listview on child click listener
        lv_drawer.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).contentEquals(getString(R.string.teacher))) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new TeacherDirectoryFragment()).commitAllowingStateLoss();
                    drawer_layout.closeDrawer(GravityCompat.START);
                } else if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).contentEquals(getString(R.string.staff))) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new StaffDirectoryFragment()).commitAllowingStateLoss();
                    drawer_layout.closeDrawer(GravityCompat.START);
                } else if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).contentEquals(getString(R.string.student))) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new StudentDirecoryFragment()).commitAllowingStateLoss();
                    drawer_layout.closeDrawer(GravityCompat.START);
//                } else if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).contentEquals(getString(R.string.parent))) {
//                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ParentDirectoryFragment()).commitAllowingStateLoss();
//                    drawer_layout.closeDrawer(GravityCompat.START);
                } else if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).contentEquals(getString(R.string.change_password))) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ChangePasswordFragment()).commitAllowingStateLoss();
                    drawer_layout.closeDrawer(GravityCompat.START);
                }
                return false;
            }
        });

    }



}
