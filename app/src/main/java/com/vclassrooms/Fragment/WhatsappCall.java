package com.vclassrooms.Fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Rahul on 21,July,2020
 */
public class WhatsappCall {
//        extends Fragment {
//    View mview;
//    AppUtils appUtils;
//    Constatnts constatnts;
//    Context context;
//    String strAuth,strRoleid,strUserId,strSchoolId,strAcademicId;
//    @BindView(R.id.tittle_tv)
//    TextView tittle_tv;
//    @BindView(R.id.relAdd)
//    RelativeLayout relAdd;
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mview=inflater.inflate(R.layout.common_recyclerview_layout,null);
//        ButterKnife.bind(this,mview);
//        context= getActivity();
//        appUtils=new AppUtils();
//        constatnts=new Constatnts();
//        init();
//        return mview;
//    }
//
//    private void init() {
//        // String mimeString = "vnd.android.cursor.item/vnd.com.whatsapp.voip.call";
//        String mimeString = "vnd.android.cursor.item/vnd.com.whatsapp.video.call";
//        tittle_tv.setText("E- Books");
//        relAdd.setVisibility(View.VISIBLE);
//        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
//        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
//        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
//        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
//        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
//
//        relAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ///browseDocuments();
//                String displayName = null;
//                String name="Shubham"; // here you can give static name.
//                Long _id;
//                ContentResolver resolver = getActivity().getContentResolver();
//                Cursor cursor = resolver.query(ContactsContract.Data.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
//                while (cursor.moveToNext()) {
//                    _id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data._ID));
//                    displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
//                    String mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
//                    if (displayName.equals(name)) {
//                        if (mimeType.equals(mimeString)) {
//                            String data = "content://com.android.contacts/data/" + _id;
//                            Intent sendIntent = new Intent();
//                            sendIntent.setAction(Intent.ACTION_VIEW);
//                            sendIntent.setDataAndType(Uri.parse(data), mimeString);
//                            sendIntent.setPackage("com.whatsapp");
//                            startActivity(sendIntent);
//                        }
//                    }
//                }
//            }
//        });
//
//    }
//    private void browseDocuments(){
//
//        String[] mimeTypes =
//                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
//                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
//                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
//                        "text/plain",
//                        "application/pdf",
//                        "application/zip"};
//
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        // intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
//            if (mimeTypes.length > 0) {
//                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//            }
//        } else {
//            String mimeTypesStr = "";
//            for (String mimeType : mimeTypes) {
//                mimeTypesStr += mimeType + "|";
//            }
//            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
//        }
//        startActivityForResult(Intent.createChooser(intent,"ChooseFile"), 1);
//
//    }
//    public void onActivityResult(int requestCode, int resultCode, Intent result) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == 1) {
//                String  path = String.valueOf(result.getData());
//                Log.e("Path",path);
//            }
//        }
//    }
}

