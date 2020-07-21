package com.vclassrooms.Activity;

import android.content.Context;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.LoginResponse;
import com.vclassrooms.R;
import com.vclassrooms.Retrofit.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.edtSchoolID)
    MultiAutoCompleteTextView edtSchoolID;
    @BindView(R.id.edtUsername)
    TextInputEditText edtUsername;
    @BindView(R.id.edtPassword)
    TextInputEditText edtPassword;
    @BindView(R.id.loginCheckBox)
    CheckBox loginCheckBox;
    @BindView(R.id.txtForgotPassword)
    TextView txtForgotPassword;
    @BindView(R.id.btn_Login)
    AppCompatButton btn_Login;

    AppUtils appUtils;
    Constatnts constatnts;
    Context context;
    String strSchoolId="";
    ArrayList<String> myList;
    FirebaseAnalytics firebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(LoginActivity.this);
        context= LoginActivity.this;
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        init();
    }

    private void init() {
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid()){
                    onLoginApi();
                }
            }
        });
        String strA = appUtils.getStringPrefrences(LoginActivity.this, constatnts.SH_APPPREFSCHOOLLIST, constatnts.SH_SCHOOLList);
        if (!TextUtils.isEmpty(strA)) {
            myList = new ArrayList<String>(Arrays.asList(strA.substring(1, strA.length() - 1).replaceAll("\"", "").split(",")));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
            edtSchoolID.setThreshold(1);
            edtSchoolID.setAdapter(adapter);
        }
        edtSchoolID.setTokenizer(new MultiAutoCompleteTextView.Tokenizer() {
            @Override
            public int findTokenStart(CharSequence text, int cursor) {
                int i = cursor;

                while (i > 0 && text.charAt(i - 1) != '@') {
                    i--;
                }
                while (i < cursor && text.charAt(i) == ' ') {
                    i++;
                }

                return i;
            }

            @Override
            public int findTokenEnd(CharSequence text, int cursor) {
                int i = cursor;
                int len = text.length();

                while (i < len) {
                    if (text.charAt(i) == ',') {
                        return i;
                    } else {
                        i++;
                    }
                }

                return len;
            }

            @Override
            public CharSequence terminateToken(CharSequence text) {
                int i = text.length();

                while (i > 0 && text.charAt(i - 1) == ' ') {
                    i--;
                }

                if (i > 0 && text.charAt(i - 1) == ',') {
                    return text;
                } else {
                    if (text instanceof Spanned) {
                        SpannableString sp = new SpannableString(text + ", ");
                        TextUtils.copySpansFrom((Spanned) text, 0, text.length(),
                                Object.class, sp, 0);
                        return sp;
                    } else {
                        String str = (String) text;
                        str = str.replaceAll("[^\\d]", "");
                        return str;
                    }
                }
            }
        });
    }
    public boolean isPresentSchoolId(String sclID){
        try {
            boolean value = myList.contains(sclID);
            for (String item : myList) {
                String id = item.replaceAll("[^0-9]", "");
                if (id.equals(sclID)) {
                    strSchoolId=item.replaceAll("[0-9]","");
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public boolean isValid(){
        boolean isvalid=true;
        if (edtSchoolID.getText().toString().equals("")) {
            isvalid=false;
            appUtils.showToast(LoginActivity.this, getString(R.string.enter_school_id));
        } else if(isPresentSchoolId(edtSchoolID.getText().toString())==false){
            isvalid=false;
            appUtils.showToast(LoginActivity.this, getString(R.string.enter_school_id));
        }else if (edtUsername.getText().toString().equals("")) {
            isvalid=false;
            appUtils.showToast(LoginActivity.this, getString(R.string.enter_username));
        } else if (edtPassword.getText().toString().equals("")) {
            isvalid=false;
            appUtils.showToast(LoginActivity.this, getString(R.string.enter_userpassword));
        }
        return isvalid;
    }

    private void onLoginApi() {
        try {
            Log.d("Firebase", "token "+ FirebaseInstanceId.getInstance().getToken());
            appUtils.showProgressbar(context);
            Call<LoginResponse> call = ApiService.buildService(context).getLoginDetails(constatnts.LICENSEKEY,constatnts.COMMAND_LOGIN,edtUsername.getText().toString(),
                    edtPassword.getText().toString(),edtSchoolID.getText().toString(),FirebaseInstanceId.getInstance().getToken().toString());
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    appUtils.hideProgressbar();
                        if (response.body() != null) {
                            if (response.body().getStatusCode().equals(0)) {
                                if(response.body().getData().getLoginDetails()!=null){
                                    appUtils.setStringPrefrences(LoginActivity.this, constatnts.SH_APPPREF,"Authorization",constatnts.SH_FCM);
                                    if(String.valueOf(response.body().getData().getLoginDetails().get(0).getUserTypeId()).contentEquals("1")){
                                        //Parent Login
                                        appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getStudentId()),constatnts.SH_USERID);
                                        List<JSONObject> myJSONObjects = new ArrayList<JSONObject>(response.body().getData().getLoginDetails().size());


                                        for (int i = 0; i < response.body().getData().getLoginDetails().size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {

                                                jsonObject.put("id", response.body().getData().getLoginDetails().get(i).getStudentId());
                                                String firstNameText = Html.fromHtml(response.body().getData().getLoginDetails().get(i).getChildName()).toString();
                                                jsonObject.put("firstName", firstNameText);
                                                if( response.body().getData().getLoginDetails().get(i).getImageURL()!=null){
                                                    jsonObject.put("photoPath", response.body().getData().getLoginDetails().get(i).getImageURL());
                                                }else {
                                                    jsonObject.put("photoPath", "");
                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            myJSONObjects.add(jsonObject);
                                        }
                                        JSONArray jsonArray = new JSONArray(myJSONObjects);
                                        appUtils.setStringPrefrences(LoginActivity.this, constatnts.SH_APPPREF, String.valueOf(jsonArray),constatnts.SH_ALL_CHILD_DETAILS);

                                    }else  if(String.valueOf(response.body().getData().getLoginDetails().get(0).getUserTypeId()).contentEquals("2")){
                                        //StudentLogin
                                        appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getImageURL()),constatnts.SH_USER_PROFILE_IMAGE);
                                        appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getStandardId()),constatnts.SH_STANDARDID);
                                        appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getDivisionId()),constatnts.SH_DIVISIONID);
                                        appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getStudentId()),constatnts.SH_USERID);
                                    }else if(String.valueOf(response.body().getData().getLoginDetails().get(0).getUserTypeId()).contentEquals("3")){
                                        //Teacher Login
                                        appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getImageURL()),constatnts.SH_USER_PROFILE_IMAGE);
                                        appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getStandardId()),constatnts.SH_STANDARDID);
                                        appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getDivisionId()),constatnts.SH_DIVISIONID);
                                        appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getStandardName()),constatnts.SH_STANDARDNAME);
                                        appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getDivisionName()),constatnts.SH_DIVISIONNAME);
                                        appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getUserId()),constatnts.SH_USERID);
                                    }else if(String.valueOf(response.body().getData().getLoginDetails().get(0).getUserTypeId()).contentEquals("5")){
                                        //Admin
                                        appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getUserId()),constatnts.SH_USERID);
                                        appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getImageURL()),constatnts.SH_USER_PROFILE_IMAGE);
                                    }
                                    appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getUserTypeId()),constatnts.SH_USERTYPEID);
                                    appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getSchoolId()),constatnts.SH_SCHOOLID);
                                    appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getAcademicId()),constatnts.SH_ACADEMICYEAR);
                                    appUtils.setStringPrefrences(context,constatnts.SH_APPPREF, String.valueOf(response.body().getData().getLoginDetails().get(0).getUserName()),constatnts.SH_USERNAME);
                                    appUtils.simpleIntentFinish(context, MainActivity.class,Bundle.EMPTY);
                                }else {
                                    appUtils.showToast(context, getString(R.string.incorrect_username_password));
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
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    appUtils.hideProgressbar();
                    appUtils.showToast(context, getString(R.string.error_message));
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }


}