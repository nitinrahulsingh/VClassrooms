package com.vclassrooms.Fragment;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Activity.FilePreviewActivity;
import com.vclassrooms.Adapter.EBookAdapter;
import com.vclassrooms.Adapter.GalleryAdapter;
import com.vclassrooms.BottomSheetDialog.AddE_BookBottomSheet;
import com.vclassrooms.BottomSheetDialog.AddGalleryImagesBottomSheet;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.EbookDetailsResponse;
import com.vclassrooms.Entity.GalleryDetailResponse;
import com.vclassrooms.Interface.CommonInterface;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;
import com.vclassrooms.photopicker.utils.FileUtils;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static android.app.Activity.RESULT_OK;
import static com.vclassrooms.photopicker.utils.FileUtils.getDataColumn;
import static com.vclassrooms.photopicker.utils.FileUtils.getPath;
import static com.vclassrooms.photopicker.utils.FileUtils.isDownloadsDocument;
import static com.vclassrooms.photopicker.utils.FileUtils.isExternalStorageDocument;
import static com.vclassrooms.photopicker.utils.FileUtils.isGooglePhotosUri;
import static com.vclassrooms.photopicker.utils.FileUtils.isMediaDocument;

/**
 * Created by Rahul on 11,July,2020
 */
public class E_BooksFragment extends Fragment implements CommonInterface {
    View mview;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth, strRoleid, strUserId, strSchoolId, strAcademicId;
    @BindView(R.id.tittle_tv)
    TextView tittle_tv;
    @BindView(R.id.relAdd)
    RelativeLayout relAdd;
    @BindView(R.id.txt_no_data)
    TextView txt_no_data;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.searchClose)
    ImageView searchClose;
    @BindView(R.id.linearSearches)
    LinearLayout linearSearches;
    String strStandardID,strDivisonId;
    LinearLayoutManager mLayoutManager;
    List<EbookDetailsResponse.EbookDetail>ebookDetailList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.common_recyclerview_layout, null);
        ButterKnife.bind(this, mview);
        context = getActivity();
        appUtils = new AppUtils();
        constatnts = new Constatnts();
        init();
        getEbookApi();
        return mview;
    }

    private void init() {
        tittle_tv.setText("E- Books");
        mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
       if(strRoleid.contentEquals("2")){
            relAdd.setVisibility(View.GONE);
            strStandardID=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_STANDARDID);
           strDivisonId=appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_DIVISIONID);
        }else {
            relAdd.setVisibility(View.VISIBLE);
             strStandardID=getArguments().getString("StandardID");
             strDivisonId=getArguments().getString("DivisionId");

        }
        relAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddE_BookBottomSheet addE_bookBottomSheet = new
                        AddE_BookBottomSheet();
                addE_bookBottomSheet.newInstance(E_BooksFragment.this);
                addE_bookBottomSheet.show(getActivity().getSupportFragmentManager(),
                        "add__dialog_fragment");

            }
        });

    }
    private void getEbookApi() {
        try {
            String command="";
            if(strRoleid.contentEquals("3")){
                command="SelectForTeacher";
            }else {
                command="Select";
            }
            appUtils.showProgressbar(context);
            Call<EbookDetailsResponse> call = ApiService.buildService(context).getEbookDetail(strAuth,command,strSchoolId,strAcademicId,strStandardID,strDivisonId,strRoleid,strUserId);
            call.enqueue(new Callback<EbookDetailsResponse>() {
                @Override
                public void onResponse(Call<EbookDetailsResponse> call, Response<EbookDetailsResponse> response) {
                    appUtils.hideProgressbar();
                    if (response.body() != null) {
                        if (response.body().getStatusCode()==0) {
                            if(response.body().getData().getEbookDetails()!=null && response.body().getData().getEbookDetails().size()>0 ){
                                txt_no_data.setVisibility(View.GONE);
                                recyclerview.setVisibility(View.VISIBLE);
                                ebookDetailList=response.body().getData().getEbookDetails();
                                EBookAdapter eBookAdapter = new EBookAdapter(getActivity(),response.body().getData().getEbookDetails());
                                recyclerview.setAdapter(eBookAdapter);
                                recyclerview.setHasFixedSize(true);
                                recyclerview.setNestedScrollingEnabled(false);
                                eBookAdapter.setOnClickListener(new EBookAdapter.EbookDataClick() {
                                    @Override
                                    public void onEbooktClick(String details, int position) {
                                        String extension = ebookDetailList.get(position).getVchFilePath().substring(ebookDetailList.get(position).getVchFilePath().lastIndexOf("."));
                                        String filename = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss_SS", Locale.getDefault()).format(new Date());
                                        extension = filename + extension;
                                        Bundle bundle=new Bundle();
                                        bundle.putString("path",ebookDetailList.get(position).getVchFilePath());
                                        bundle.putString("name",extension);
                                        appUtils.simpleIntent(context, FilePreviewActivity.class,bundle);
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
                public void onFailure(Call<EbookDetailsResponse> call, Throwable t) {
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
