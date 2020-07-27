package com.vclassrooms.BottomSheetDialog;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Interface.CommonInterface;
import com.vclassrooms.R;
import com.vclassrooms.photopicker.activity.PickImageActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rahul on 22,July,2020
 */
public class PhoneCallOptionBottomSheet extends BottomSheetDialogFragment {
    View mView;
    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strAuth, strRoleid, strUserId, strSchoolId, strAcademicId;
    @BindView(R.id.layoutWhatsApp)
    LinearLayout layoutWhatsApp;
    @BindView(R.id.layoutCall)
    LinearLayout layoutCall;
    @BindView(R.id.layoutEmail)
    LinearLayout layoutEmail;
    @BindView(R.id.layoutVoiceCAll)
    CardView layoutVoiceCAll;
    @BindView(R.id.layoutVideoCall)
    CardView layoutVideoCall;
    @BindView(R.id.layoutchat)
    CardView layoutchat;
    @BindView(R.id.imgIndicator)
    ImageView imgIndicator;
    @BindView(R.id.layoutWhatsApp_Selection)
    LinearLayout layoutWhatsApp_Selection;

    String strPhone,strMailId,strName;
    boolean isVoiceCall=false;

    public PhoneCallOptionBottomSheet newInstance(String Phone,String Mail,String Name) {
        this.strMailId=Mail;
        this.strPhone=Phone;
        this.strName=Name;
        return new PhoneCallOptionBottomSheet();
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.phonecall_option_bottomsheet, container, false);
        ButterKnife.bind(this, mView);
        context = getActivity();
        appUtils = new AppUtils();
        constatnts = new Constatnts();
        init();
        return mView;
    }

    private void init() {
        strAuth = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_FCM);
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        strUserId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERID);
        strSchoolId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_SCHOOLID);
        strAcademicId = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_ACADEMICYEAR);
    }
    @OnClick({R.id.layoutWhatsApp, R.id.layoutCall, R.id.layoutEmail,R.id.layoutVoiceCAll,R.id.layoutVideoCall,R.id.layoutchat})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.layoutWhatsApp:
                if(imgIndicator.getVisibility()==View.GONE){
                    imgIndicator.setVisibility(View.VISIBLE);
                    layoutWhatsApp_Selection.setVisibility(View.VISIBLE);
                }else {
                    imgIndicator.setVisibility(View.GONE);
                    layoutWhatsApp_Selection.setVisibility(View.GONE);
                }

                break;
            case R.id.layoutEmail:
                if(strMailId!=null){
                    Intent intent = new Intent (Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{strMailId});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
                    intent.setPackage("com.google.android.gm");
                    if (intent.resolveActivity(getActivity().getPackageManager())!=null) {
                        startActivity(intent);
                    }
                    else {
                        appUtils.showToast(context, "Gmail App is not installed");
                    }
                    dismiss();
                }else {
                    appUtils.showToast(context,"EmailId is not Available");
                }

                break;
            case R.id.layoutCall:
                if(strPhone!=null){
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+Uri.encode(strPhone.trim())));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(callIntent);
                    dismiss();
                }else {
                    appUtils.showToast(context,"Phone No is not Available");
                }

                break;
            case R.id.layoutVoiceCAll:
                isVoiceCall=true;
                if(contactExists(context,strPhone)){
                    onWhatsappVoiceVideoCall("vnd.android.cursor.item/vnd.com.whatsapp.voip.call");
                }else {
                    AddContactNo();
                }

                dismiss();
                break;
              case R.id.layoutVideoCall:
                  isVoiceCall=false;
                  if(contactExists(context,strPhone)){
                      onWhatsappVoiceVideoCall("vnd.android.cursor.item/vnd.com.whatsapp.video.call");
                  }else {
                 AddContactNo();
                  }
                dismiss();
                break;
              case R.id.layoutchat:
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                      try {
                          sendOverWhatsapp(strPhone);
                      } catch (UnsupportedEncodingException e) {
                          e.printStackTrace();
                      }
                  }
                  dismiss();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendOverWhatsapp(String mobileNo) throws UnsupportedEncodingException {
        String smsNumber = mobileNo;
        try {
            if (mobileNo.contains("+91")) {

            } else {
                mobileNo = "+91" + mobileNo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {

            PackageManager packageManager = getActivity().getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + mobileNo + "&text=" + URLEncoder.encode("", "UTF-8");
            Log.e("whatsappurl",url);
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                getActivity().startActivity(i);
            }
        } else {
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(getContext(), "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            startActivity(goToMarket);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = requireContext().getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
    public void AddContactNo(){
        Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
        contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        contactIntent
                .putExtra(ContactsContract.Intents.Insert.NAME, strName)
                .putExtra(ContactsContract.Intents.Insert.PHONE, strPhone);

        startActivityForResult(contactIntent, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 1)
        {
            if (resultCode == Activity.RESULT_OK) {
                if(isVoiceCall){
                    onWhatsappVoiceVideoCall("vnd.android.cursor.item/vnd.com.whatsapp.voip.call");
                }else {
                    onWhatsappVoiceVideoCall("vnd.android.cursor.item/vnd.com.whatsapp.video.call");
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
    ///
    public boolean contactExists(Context context, String number) {
/// number is the phone number
        Uri lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = context.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                //Toast.makeText(getActivity(), "Exception: ", Toast.LENGTH_SHORT).show();
                return true;
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return false;
    }
    public void onWhatsappVoiceVideoCall(String mimeString){
        String displayName = null;
        String name=strName; // here you can give static name.
        Long _id;
        ContentResolver resolver = getActivity().getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Data.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
        while (cursor.moveToNext()) {
            _id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data._ID));
            displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            String mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
            if (displayName.equals(name)) {
                if (mimeType.equals(mimeString)) {
                    String data = "content://com.android.contacts/data/" + _id;
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_VIEW);
                    sendIntent.setDataAndType(Uri.parse(data), mimeString);
                    sendIntent.setPackage("com.whatsapp");
                    startActivity(sendIntent);
                }
            }
        }
    }
}
