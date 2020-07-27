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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Adapter.OnlineLecAdapter;
import com.vclassrooms.BottomSheetDialog.AddAssignmentBottomSheet;
import com.vclassrooms.BottomSheetDialog.AddE_BookBottomSheet;
import com.vclassrooms.BottomSheetDialog.AddOnlineLecBottomSheet;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.AssignmentResponse;
import com.vclassrooms.Entity.OnlineLectureDetailsResponse;
import com.vclassrooms.Entity.OnlineLectureDetailsResponse;
import com.vclassrooms.Interface.CommonInterface;
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
 * Created by Rahul on 23,July,2020
 */
public class OnlineLecFragment extends Fragment implements CommonInterface {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    @BindView(R.id.tittle_tv)
    TextView tittle_tv;
    @BindView(R.id.relAdd)
    RelativeLayout relAdd;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.txt_no_data)
    TextView txt_no_data;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.searchClose)
    ImageView searchClose;
    @BindView(R.id.linearSearches)
    LinearLayout linearSearches;
    LinearLayoutManager mLayoutManager;
    List<OnlineLectureDetailsResponse.OnlineLecture> onlineLectureList=new ArrayList<>();
    String strClassName,strDivisionId,strStandardID;
    OnlineLecAdapter onlineLecAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.online_lect_fragment,null);
        ButterKnife.bind(OnlineLecFragment.this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
        return mview;
    }

    private void init() {
        appUtils.setText(tittle_tv, "Online Lectures");
        mLayoutManager = new GridLayoutManager(context,2);
        recyclerview.setLayoutManager(mLayoutManager);
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);

        if(strRoleid.contentEquals("2")){
            relAdd.setVisibility(View.GONE);
            strDivisionId=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_DIVISIONID);
            strStandardID=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_STANDARDID);
        }else {
            relAdd.setVisibility(View.VISIBLE);
            strClassName=getArguments().getString("ClassName");
            strDivisionId=getArguments().getString("DivisionId");
            strStandardID=getArguments().getString("StandardID");
        }
        edtSearch.setHint("Search Subject");
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
                    ArrayList<OnlineLectureDetailsResponse.OnlineLecture> searchFiterLs = filter(onlineLectureList, edtSearch.getText().toString());
                    if (searchFiterLs.size() > 0) {
                        onlineLecAdapter = new OnlineLecAdapter(getActivity(), searchFiterLs);
                        recyclerview.setAdapter(onlineLecAdapter);
                        onlineLecAdapter.notifyDataSetChanged();
                        searchClose.setVisibility(View.VISIBLE);
                    } else {
                        searchClose.setVisibility(View.VISIBLE);
                        appUtils.showToast(context, getString(R.string.no_result_found));
                    }

                }else {
                    onlineLecAdapter = new OnlineLecAdapter(getActivity(), onlineLectureList);
                    recyclerview.setAdapter(onlineLecAdapter);
                    onlineLecAdapter.notifyDataSetChanged();
                    searchClose.setVisibility(View.GONE);
                }
            }
        });
        getVideoListApi();
    }
    private ArrayList<OnlineLectureDetailsResponse.OnlineLecture> filter(List<OnlineLectureDetailsResponse.OnlineLecture>
                                                                    dirItemArrayList,
                                                            String query) {
        final ArrayList<OnlineLectureDetailsResponse.OnlineLecture> filteredModelList = new ArrayList<>();
        try {
            query = query.toLowerCase().trim();
            if (query.equals("")) {
            }
            else {
                for (OnlineLectureDetailsResponse.OnlineLecture model : dirItemArrayList) {
                    final String text = model.getSubjectName().toLowerCase();
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

    @OnClick({R.id.relAdd,R.id.searchClose})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.relAdd:
                AddOnlineLecBottomSheet addOnlineLecBottomSheet = new
                        AddOnlineLecBottomSheet();
                addOnlineLecBottomSheet.newInstance(OnlineLecFragment.this);
                addOnlineLecBottomSheet.show(getActivity().getSupportFragmentManager(),
                        "add__dialog_fragment");
                break;
            case R.id.searchClose:
                edtSearch.setText("");
                onlineLecAdapter = new OnlineLecAdapter(getActivity(), onlineLectureList);
                recyclerview.setAdapter(onlineLecAdapter);
                onlineLecAdapter.notifyDataSetChanged();
                searchClose.setVisibility(View.GONE);
                break;

        }
    }
    private void getVideoListApi() {
        try {
            appUtils.showProgressbar(context);
            Call<OnlineLectureDetailsResponse> call = ApiService.buildService(context).getOnlineLecList(strAuth,"GetOnlineLecture",strRoleid,strUserId,strSchoolId,strAcademicId,strStandardID,strDivisionId);
            call.enqueue(new Callback<OnlineLectureDetailsResponse>() {
                @Override
                public void onResponse(Call<OnlineLectureDetailsResponse> call, Response<OnlineLectureDetailsResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode()==0) {
                            if(response.body().getData().getOnlineLecture()!=null && response.body().getData().getOnlineLecture().size()>0 ){
                                recyclerview.setVisibility(View.VISIBLE);
                                txt_no_data.setVisibility(View.GONE);
                                onlineLectureList=response.body().getData().getOnlineLecture();
                                onlineLecAdapter = new OnlineLecAdapter(getActivity(), response.body().getData().getOnlineLecture());
                                recyclerview.setAdapter(onlineLecAdapter);
                                recyclerview.setHasFixedSize(true);
                                recyclerview.setNestedScrollingEnabled(false);
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
                public void onFailure(Call<OnlineLectureDetailsResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void OnCommonInterfaceClick(int id, boolean isAdded) {
        getVideoListApi();
    }
}
