package com.vclassrooms.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.vclassrooms.Adapter.AttendanceStandardListAdapter;
import com.vclassrooms.Adapter.StdListBottomAdapter;
import com.vclassrooms.Adapter.StudentlistAdapter;
import com.vclassrooms.Adapter.StudentlistAdapter;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.StandardDivisionResponse;
import com.vclassrooms.Entity.StudentListResponse;
import com.vclassrooms.Entity.StudentListResponse;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;

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
 * Created by Rahul on 08,July,2020
 */
public class StudentListStandardWiseFragment extends Fragment {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    DatePickerDialog datePickerDialog;
    @BindView(R.id.relStdDate)
    RelativeLayout relStdDate;
    @BindView(R.id.txt_standard)
    TextView txt_standard;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.txt_no_data)
    TextView txt_no_data;
    @BindView(R.id.txt_no_Internet)
    TextView txt_no_Internet;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.searchClose)
    ImageView searchClose;
    @BindView(R.id.linearSearches)
    LinearLayout linearSearches;
   @BindView(R.id.textViewChangeclass)
    TextView textViewChangeclass;
   @BindView(R.id.tittle_tv)
    TextView tittle_tv;

    BottomSheetDialog dialog;
    LinearLayoutManager mLayoutManager;
    StudentlistAdapter studentlistAdapter;
    List<StudentListResponse.StudentList> studentLists=new ArrayList<>();
    List<StandardDivisionResponse.Division> standardDetailList=new ArrayList<>();
    String strDate,strClassName,strDivisionId,strStandardID;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.studentlist_fragment,null);
        ButterKnife.bind(this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
        return mview;
    }
    private void init() {
        tittle_tv.setText("Student");
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
        if(strRoleid.contentEquals("3")){
            relStdDate.setVisibility(View.GONE);
            textViewChangeclass.setVisibility(View.GONE);
            strClassName=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_STANDARDNAME)+" ("
                    +appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_DIVISIONNAME)+")";
            strDivisionId=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_DIVISIONID);
            strStandardID=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_STANDARDID);
        }else {
            relStdDate.setVisibility(View.VISIBLE);
            textViewChangeclass.setVisibility(View.VISIBLE);
            strClassName=getArguments().getString("ClassName");
            strDivisionId=getArguments().getString("DivisionId");
            strStandardID=getArguments().getString("StandardID");
        }
        txt_standard.setText("Class:"+strClassName);
        mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        getStudentListApi();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!edtSearch.getText().toString().equals("")) {
                    ArrayList<StudentListResponse.StudentList> searchFiterLs = filter(studentLists, edtSearch.getText().toString());
                    if (searchFiterLs.size() > 0) {
                        studentlistAdapter = new StudentlistAdapter(getActivity(), searchFiterLs);
                        recyclerview.setAdapter(studentlistAdapter);
                        studentlistAdapter.notifyDataSetChanged();
                        searchClose.setVisibility(View.VISIBLE);
                    } else {
                        searchClose.setVisibility(View.VISIBLE);
                        appUtils.showToast(context, getString(R.string.no_result_found));
                    }

                }else {
                    studentlistAdapter = new StudentlistAdapter(getActivity(), studentLists);
                    recyclerview.setAdapter(studentlistAdapter);
                    studentlistAdapter.notifyDataSetChanged();
                    searchClose.setVisibility(View.GONE);
                }
            }
        });


    }
    private ArrayList<StudentListResponse.StudentList> filter(List<StudentListResponse.StudentList>
                                                                            dirItemArrayList,
                                                                    String query) {
        final ArrayList<StudentListResponse.StudentList> filteredModelList = new ArrayList<>();
        try {
            query = query.toLowerCase().trim();
            if (query.equals("")) {
            }
            else {
                for (StudentListResponse.StudentList model : dirItemArrayList) {
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
    @OnClick({R.id.searchClose,R.id.relStdDate})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.searchClose:
                edtSearch.setText("");
                studentlistAdapter = new StudentlistAdapter(getActivity(), studentLists);
                recyclerview.setAdapter(studentlistAdapter);
                studentlistAdapter.notifyDataSetChanged();
                searchClose.setVisibility(View.GONE);
                break;
            case R.id.relStdDate:
                if(standardDetailList!=null && standardDetailList.size()>0){
                    showBottomSheetDialog(getActivity(),standardDetailList);
                }else {
                    getStandardDetailApi();
                }
                break;
        }
    }
    private void getStudentListApi() {
        try {
            appUtils.showProgressbar(context);
            Call<StudentListResponse> call = ApiService.buildService(context).getStudentStandardWiseList(strAuth,"GetStudentList",strRoleid,strUserId,strSchoolId,strAcademicId,strDivisionId,strStandardID);
            call.enqueue(new Callback<StudentListResponse>() {
                @Override
                public void onResponse(Call<StudentListResponse> call, Response<StudentListResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if(response.body().getData().getStudentList()!=null){
                                recyclerview.setVisibility(View.VISIBLE);
                                linearSearches.setVisibility(View.VISIBLE);
                                txt_no_data.setVisibility(View.GONE);
                                studentLists=response.body().getData().getStudentList();
                                studentlistAdapter = new StudentlistAdapter(getActivity(), response.body().getData().getStudentList());
                                recyclerview.setAdapter(studentlistAdapter);
                                recyclerview.setHasFixedSize(true);
                                recyclerview.setNestedScrollingEnabled(false);
                                studentlistAdapter.setOnClickListener(new StudentlistAdapter.StudentDataClick() {
                                    @Override
                                    public void onStudentDataClick(int id, int position) {
                                        SelfAttendanceFragment fragment = new SelfAttendanceFragment();
                                        Bundle   bundle=new Bundle();
                                        bundle.putString("ClassName", studentLists.get(position).getStandardName()+" ("+
                                                studentLists.get(position).getDivisionName()+")");
                                        bundle.putString("DivisionId",strDivisionId);
                                        bundle.putString("StandardID", strStandardID);
                                        fragment.setArguments(bundle);
                                        appUtils.onAddFragment(getActivity(), fragment,R.id.fragment_mainLayout);
                                    }
                                });
                            }else {
                                recyclerview.setVisibility(View.GONE);
                                linearSearches.setVisibility(View.GONE);
                                txt_no_data.setVisibility(View.VISIBLE);

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
                public void onFailure(Call<StudentListResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }


    public void showBottomSheetDialog(Activity activity, List<StandardDivisionResponse.Division> standardDetails) {
        View view = activity.getLayoutInflater().inflate(R.layout.std_bottomsheet, null);
        RecyclerView std_recycler = view.findViewById(R.id.std_recycler);
        RelativeLayout minimize_relative = view.findViewById(R.id.minimize_relative);
        dialog = new BottomSheetDialog(view.getContext());
        dialog.setContentView(view);
        dialog.show();
        dialog.setCancelable(false);
        minimize_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        StdListBottomAdapter stdBottomAdapter = new StdListBottomAdapter(getContext(), standardDetails);
        std_recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        std_recycler.setAdapter(stdBottomAdapter);

        stdBottomAdapter.setOnClickListener(new StdListBottomAdapter.StandardDataClick() {
            @Override
            public void onStandardDataClick(int id, int position) {
                strDivisionId= String.valueOf(standardDetails.get(position).getDivisionId());
                strStandardID= String.valueOf(standardDetails.get(position).getStandardId());
                getStudentListApi();
                dialog.dismiss();
            }
        });
    }

    private void getStandardDetailApi() {
        try {
            appUtils.showProgressbar(context);
            Call<StandardDivisionResponse> call = ApiService.buildService(context).getStandardDivisionList(strAuth,"GetStandardDivision",strRoleid,strUserId,strSchoolId,strAcademicId,"0");
            call.enqueue(new Callback<StandardDivisionResponse>() {
                @Override
                public void onResponse(Call<StandardDivisionResponse> call, Response<StandardDivisionResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode().equals(0)) {
                            if(response.body().getData().getDivision()!=null){
                                standardDetailList=response.body().getData().getDivision();
                                showBottomSheetDialog(getActivity(),response.body().getData().getDivision());
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
                public void onFailure(Call<StandardDivisionResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
}