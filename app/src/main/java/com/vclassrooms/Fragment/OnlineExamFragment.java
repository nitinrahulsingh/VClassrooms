package com.vclassrooms.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vclassrooms.Activity.FilePreviewActivity;
import com.vclassrooms.Adapter.AssignmentAdapter;
import com.vclassrooms.Adapter.EBookAdapter;
import com.vclassrooms.Adapter.OnlineExamAdapter;
import com.vclassrooms.BottomSheetDialog.AddAssignmentBottomSheet;
import com.vclassrooms.BottomSheetDialog.AddOnlineExamQuestionBottomSheet;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.AssignmentResponse;
import com.vclassrooms.Entity.EbookDetailsResponse;
import com.vclassrooms.Entity.OnlineExaminationResponse;
import com.vclassrooms.Entity.Question;
import com.vclassrooms.Interface.CommonInterface;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * Created by Rahul on 22,July,2020
 */
public class OnlineExamFragment extends Fragment implements CommonInterface {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
    @BindView(R.id.tittle_tv)
    TextView tittle_tv;
    @BindView(R.id.relAdd)
    RelativeLayout relAdd;
    String strDivisionId,strStandardID;
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
    List<OnlineExaminationResponse.OnlineExamination> onlineExaminations=new ArrayList<>();
    OnlineExamAdapter onlineExamAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.common_recyclerview_layout,null);
        ButterKnife.bind(OnlineExamFragment.this,mview);
        context= getActivity();
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
        return mview;
    }
    private void init() {
        mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        appUtils.setText(tittle_tv, "Online Exam");
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
             strDivisionId=getArguments().getString("DivisionId");
             strStandardID=getArguments().getString("StandardID");
            relAdd.setVisibility(View.VISIBLE);
        }
        getEbookApi();
        edtSearch.setHint("Search");
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
                    ArrayList<OnlineExaminationResponse.OnlineExamination> searchFiterLs = filter(onlineExaminations, edtSearch.getText().toString());
                    if (searchFiterLs.size() > 0) {
                         onlineExamAdapter = new OnlineExamAdapter(getActivity(), searchFiterLs);
                        recyclerview.setAdapter(onlineExamAdapter);
                        onlineExamAdapter.notifyDataSetChanged();
                        searchClose.setVisibility(View.VISIBLE);
                    } else {
                        searchClose.setVisibility(View.VISIBLE);
                        appUtils.showToast(context, getString(R.string.no_result_found));
                    }

                }else {
                    onlineExamAdapter = new OnlineExamAdapter(getActivity(), onlineExaminations);
                    recyclerview.setAdapter(onlineExamAdapter);
                    onlineExamAdapter.notifyDataSetChanged();
                    searchClose.setVisibility(View.GONE);
                }
            }
        });
    }
    private ArrayList<OnlineExaminationResponse.OnlineExamination> filter(List<OnlineExaminationResponse.OnlineExamination>
                                                                    dirItemArrayList,
                                                            String query) {
        final ArrayList<OnlineExaminationResponse.OnlineExamination> filteredModelList = new ArrayList<>();
        try {
            query = query.toLowerCase().trim();
            if (query.equals("")) {
            }
            else {
                for (OnlineExaminationResponse.OnlineExamination model : dirItemArrayList) {
                    final String text = model.getTitle().toLowerCase();
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
                AddOnlineExamQuestionBottomSheet addOnlineExamQuestionBottomSheet = new
                        AddOnlineExamQuestionBottomSheet();
                addOnlineExamQuestionBottomSheet.newInstance(OnlineExamFragment.this);
                addOnlineExamQuestionBottomSheet.show(getActivity().getSupportFragmentManager(),
                        "add__dialog_fragment");
                break;
            case R.id.searchClose:
                edtSearch.setText("");
                onlineExamAdapter = new OnlineExamAdapter(getActivity(), onlineExaminations);
                recyclerview.setAdapter(onlineExamAdapter);
                onlineExamAdapter.notifyDataSetChanged();
                searchClose.setVisibility(View.GONE);
                break;

        }
    }
    private void getEbookApi() {
        try {

            appUtils.showProgressbar(context);
            Call<OnlineExaminationResponse> call = ApiService.buildService(context).getOnlineExamDeatil(strAuth,"GetExamDet",strRoleid,strUserId,strSchoolId,strAcademicId,strStandardID,strDivisionId);
            call.enqueue(new Callback<OnlineExaminationResponse>() {
                @Override
                public void onResponse(Call<OnlineExaminationResponse> call, Response<OnlineExaminationResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode()==0) {
                            if(response.body().getData().getOnlineExamination()!=null && response.body().getData().getOnlineExamination().size()>0 ){
                                txt_no_data.setVisibility(View.GONE);
                                recyclerview.setVisibility(View.VISIBLE);
                                onlineExaminations=response.body().getData().getOnlineExamination();
                                onlineExamAdapter = new OnlineExamAdapter(getActivity(),response.body().getData().getOnlineExamination());
                                recyclerview.setAdapter(onlineExamAdapter);
                                recyclerview.setHasFixedSize(true);
                                recyclerview.setNestedScrollingEnabled(false);

//                                ques=response.body().getData().getOnlineExamination().get(0).getQuestionAnswer();
                               onlineExamAdapter.setOnClickListener(new OnlineExamAdapter.OnlineExamDataClick() {
                                   @Override
                                   public void onExamClick(String details, int position) {
                                       if(strRoleid.contentEquals("1")||strRoleid.contentEquals("2")){
                                           OnlineExamStartFragment onlineExamStartFragment=new OnlineExamStartFragment();
                                           Bundle bundle=new Bundle();
                                           bundle.putString("QuestionAnswerSet",response.body().getData().getOnlineExamination().get(position).getQuestionAnswer());
                                           bundle.putString("Tittle",response.body().getData().getOnlineExamination().get(position).getTitle());
                                           bundle.putString("PassingMark",response.body().getData().getOnlineExamination().get(position).getPassingMark().toString());
                                           bundle.putString("TotalMark",response.body().getData().getOnlineExamination().get(position).getTotalMark().toString());
                                           bundle.putString("TotalTime",response.body().getData().getOnlineExamination().get(position).getTotalTime().toString());
                                           onlineExamStartFragment.setArguments(bundle);
                                           appUtils.onAddFragment(getActivity(),onlineExamStartFragment,R.id.fragment_mainLayout);
                                       }

                                   }
                               });
                            }else {

                                txt_no_data.setVisibility(View.VISIBLE);
                                recyclerview.setVisibility(View.GONE);
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
                public void onFailure(Call<OnlineExaminationResponse> call, Throwable t) {
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
        getEbookApi();
    }
}
