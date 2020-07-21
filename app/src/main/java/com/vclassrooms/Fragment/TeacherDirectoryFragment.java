package com.vclassrooms.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Adapter.StudentDirectoryAdapter;
import com.vclassrooms.Adapter.TeacherDirectoryAdapter;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Common.CustomSwipeToRefresh;
import com.vclassrooms.Entity.TeacherDirectoryResponse;
import com.vclassrooms.Entity.TeacherDirectoryResponse;
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
 * Created by Rahul on 29,June,2020
 */
public class TeacherDirectoryFragment extends Fragment {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    @BindView(R.id.tittle_tv)
    TextView tittle_tv;
    @BindView(R.id.heading_tv)
    TextView heading_tv;
    @BindView(R.id.dir_RecyclerView)
    RecyclerView dir_RecyclerView;
    @BindView(R.id.Swipe_toRefresh)
    CustomSwipeToRefresh Swipe_toRefresh;
    @BindView(R.id.edtSearchStudent)
    EditText edtSearchStudent;
    @BindView(R.id.searchClose)
    ImageView searchClose;
    LinearLayoutManager mLayoutManager;
    TeacherDirectoryAdapter teacherDirectoryAdapter;
    List<TeacherDirectoryResponse.DirectoryeDetail> mTeacherDataList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.fragment_directory,null);
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
        tittle_tv.setText("Teacher Directory");
        heading_tv.setText("Teacher");
        mLayoutManager = new GridLayoutManager(context,2);
        dir_RecyclerView.setLayoutManager(mLayoutManager);;
        getTeacherDirectoryDetailApi();
        edtSearchStudent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!edtSearchStudent.getText().toString().equals("")) {
                    ArrayList<TeacherDirectoryResponse.DirectoryeDetail> searchFiterLs = filter(mTeacherDataList, edtSearchStudent.getText().toString());
                    if (searchFiterLs.size() > 0) {
                        teacherDirectoryAdapter = new TeacherDirectoryAdapter(context, searchFiterLs);
                        dir_RecyclerView.setAdapter(teacherDirectoryAdapter);
                        teacherDirectoryAdapter.notifyDataSetChanged();
                        searchClose.setVisibility(View.VISIBLE);
                    } else {
                        searchClose.setVisibility(View.VISIBLE);
                        appUtils.showToast(context, getString(R.string.no_result_found));
                    }

                }else {
                    edtSearchStudent.setText("");
                    teacherDirectoryAdapter = new TeacherDirectoryAdapter(context, mTeacherDataList);
                    dir_RecyclerView.setAdapter(teacherDirectoryAdapter);
                    teacherDirectoryAdapter.notifyDataSetChanged();
                    searchClose.setVisibility(View.GONE);
                }
            }
        });
    }
    private ArrayList<TeacherDirectoryResponse.DirectoryeDetail> filter(List<TeacherDirectoryResponse.DirectoryeDetail>
                                                                                dirStudentListItemArrayList,
                                                                        String query) {
        final ArrayList<TeacherDirectoryResponse.DirectoryeDetail> filteredModelList = new ArrayList<>();
        try {
            query = query.toLowerCase().trim();
            if (query.equals("")) {
            }
            else {
                for (TeacherDirectoryResponse.DirectoryeDetail model : dirStudentListItemArrayList) {
                    final String text = model.getFirstName().toLowerCase();
                    if (text.contains(query)) {
                        filteredModelList.add(model);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return filteredModelList;

    }
    private void getTeacherDirectoryDetailApi() {
        try {
            appUtils.showProgressbar(context);
            Call<TeacherDirectoryResponse> call = ApiService.buildService(context).getTeacherDirDetails(strAuth,constatnts.GetEmployeeDetails,strRoleid,strUserId,strSchoolId,strAcademicId);
            call.enqueue(new Callback<TeacherDirectoryResponse>() {
                @Override
                public void onResponse(Call<TeacherDirectoryResponse> call, Response<TeacherDirectoryResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if(response.body().getData().getDirectoryeDetail()!=null){
                                mTeacherDataList=response.body().getData().getDirectoryeDetail();
                                teacherDirectoryAdapter = new TeacherDirectoryAdapter(context, response.body().getData().getDirectoryeDetail());
                                dir_RecyclerView.setAdapter(teacherDirectoryAdapter);
                                dir_RecyclerView.setHasFixedSize(true);
                                dir_RecyclerView.setNestedScrollingEnabled(false);
                            }else {
                                appUtils.showToast(context, getString(R.string.no_data));
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
                public void onFailure(Call<TeacherDirectoryResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }


    @OnClick({R.id.searchClose,R.id.linear_1})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.searchClose:
                edtSearchStudent.setText("");
                teacherDirectoryAdapter = new TeacherDirectoryAdapter(context, mTeacherDataList);
                dir_RecyclerView.setAdapter(teacherDirectoryAdapter);
                teacherDirectoryAdapter.notifyDataSetChanged();
                searchClose.setVisibility(View.GONE);
                break;
            case R.id.linear_1:
                alertDialg();
                break;
        }
    }

    private void alertDialg() {

        final AlertDialog loginDialog;

        TextView txtParent, txtStudent, txtStaff,txtClose,txtProspects;
        View view_Prospects;
        final AlertDialog.Builder dilaog = new AlertDialog.Builder(context,R.style.CustomDialog);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.layout_directory_lis, null);
        dilaog.setView(view);
        loginDialog = dilaog.create();

        view_Prospects=(View)view.findViewById(R.id.view_Prospects);
        txtProspects = (TextView) view.findViewById(R.id.txtProspects);

        txtParent = (TextView) view.findViewById(R.id.txtParent);
        txtStudent = (TextView) view.findViewById(R.id.txtStudent);
        txtStaff = (TextView) view.findViewById(R.id.txtStaff);
        txtClose = (TextView) view.findViewById(R.id.txtClose);


        txtProspects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                TeacherDirectoryFragment a2Fragment = new TeacherDirectoryFragment();
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_mainLayout, a2Fragment).commit();
                loginDialog.dismiss();
            }
        });

        txtParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                ParentDirectoryFragment a2Fragment = new ParentDirectoryFragment();
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_mainLayout, a2Fragment).commit();
                loginDialog.dismiss();
            }
        });

        txtStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                StudentDirecoryFragment a2Fragment = new StudentDirecoryFragment();
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_mainLayout, a2Fragment).commit();
                loginDialog.dismiss();
            }
        });

        txtStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                StaffDirectoryFragment a2Fragment = new StaffDirectoryFragment();
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_mainLayout, a2Fragment).commit();
                loginDialog.dismiss();
            }
        });
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.dismiss();
            }
        });
        dilaog.setCancelable(true);
        loginDialog.setCancelable(true);

        loginDialog.show();

    }
}
