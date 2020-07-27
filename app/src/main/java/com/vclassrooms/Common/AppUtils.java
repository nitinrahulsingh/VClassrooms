package com.vclassrooms.Common;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.textfield.TextInputLayout;
import com.vclassrooms.R;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.droidsonroids.gif.GifImageView;

public class AppUtils {
    public   final String TAG = "Milagro";
    public   SharedPreferences sharedPreferences;
    public AlertDialog progressAnimate;
    Constatnts constatnts;
    /**
     * Hide keyboard
     *
     * @param activity
     */
    @SuppressWarnings("ConstantConditions")

    public  void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }



    public  void setStringPrefrences(Context context, String preferenceName, String value, String preferenceKey) {
        SharedPreferences sp = context.getSharedPreferences(preferenceName, 0);
        sp.edit().putString(preferenceKey, value).apply();
    }

    public  String getStringPrefrences(Context context, String preferenceName, String preferenceKey) {

        String stringValue = null;
        try {
            SharedPreferences sp = context.getSharedPreferences(preferenceName, 0);
            return sp.getString(preferenceKey, "");
        } catch (NullPointerException e) {

        }
        return stringValue;
    }


    /**
     * clear all preference in a preference key
     *
     * @param context       Context
     * @param preferenceKey Key name of the Preference
     */
    public  void removeAllPrefrences(Context context, String preferenceKey) {
        sharedPreferences = context.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
    public  boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean isNetworkAvailableWithToast(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }
        showAlertDialogBox(context,context.getString(R.string.Alert),context.getString(R.string.no_internet));
        //showToast(context, context.getString(R.string.no_internet));
        return false;
    }

    /**
     * @param emailId
     * @return true - if emailId is valid
     */
    public  boolean isValidEmailID(String emailId) {
        return !TextUtils.isEmpty(emailId) && Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
    }

    /**
     * @param phoneNumber
     * @return true - if phone Number is valid
     */
    public  boolean isValidMobileNumber(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches();
    }
    public  int getIntFromString(String s) {
        int i = 0;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            i = getIntRoundoffString(s);
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return i;
    }
    public  int getIntRoundoffString(String value) {
        int valuedata = 0;
        try {
            valuedata = Math.round(Float.parseFloat(value));
        } catch (NumberFormatException | NullPointerException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return valuedata;
    }
    public  Double getDoubleFromString(String s) {
        Double i = 0.00;
        try {
            i = Double.parseDouble(s.trim());
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Convert String to float value
     *
     * @param s
     * @return Float value from String
     * 0.0 - if value is not valid
     */
    public  float getFloatfromString(String s) {
        float f = 0.0f;
        try {
            f = Float.parseFloat(s);
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
        }
        return f;
    }

    /**
     * Get today's date in any format.
     *
     * @param dateFormat pass format for get like: "yyyy-MM-dd hh:mm:ss"
     * @return current date in string format
     */
    public  String getCurrentDate(String dateFormat) {
        Date d = new Date();
        return new SimpleDateFormat(dateFormat, Locale.US).format(d.getTime());
    }


    public  boolean isAfterToday(int year, int month, int day) {

        Calendar today = Calendar.getInstance();

        int cday = Integer.parseInt(getCurrentDate("dd"));
        int cmonth = Integer.parseInt(getCurrentDate("MM"));
        int cyear = Integer.parseInt(getCurrentDate("yyyy"));
        today.set(cyear, cmonth, cday);

        Calendar myDate = Calendar.getInstance();

        myDate.set(year, month, day);

        return !myDate.after(today);
    }


    /**
     * Convert date in string format to Date format
     *
     * @param strDate which you have to convert in Date format
     * @param format  of the date like "yyyy-MM-dd hh:mm:ss"
     * @return date in Date format
     */
    public  Date stringToDate(String strDate, String format) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.US);
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Convert Date format
     *
     * @param sourceFormat source format which you want to convert from eg. "yyyy-MM-dd hh:mm"
     * @param outputFormat output format in which you want to convert into eg. "dd/MM/yyyy"
     * @param date         - which you wanto convert
     * @return converted date
     */
    public  String convertDateFormat(String sourceFormat, String outputFormat, String date) {
        if(date!=null && !TextUtils.isEmpty(date)){
            SimpleDateFormat source = new SimpleDateFormat(sourceFormat, Locale.US);
            SimpleDateFormat output = new SimpleDateFormat(outputFormat, Locale.US);
            try {
                return output.format(source.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }else {
            return "";
        }

    }

    /**
     * generate base64 from bitmap image
     *
     * @param mBitmap bitmap image
     * @param quality bitmap Quality For eg. "100"
     * @return base64 string
     */
    public  String createBase64(Bitmap mBitmap, int quality) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        byte[] b1 = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(b1, Base64.DEFAULT);
    }


    /**
     * Show Alert Dialog Single button with a Title & Message
     *
     * @param context Context
     * @param title
     * @param message
     */

    public  void showAlertDialogOneButton(Context context, String title, String message) {
        if (!((Activity) context).isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);

            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialog.show();
        }
    }

    public  void showAlertDialogBox(Context context, String title, String message) {

        if (!((Activity) context).isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);

            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialog.show();
        }
    }
    public byte[] getFileDataFromImagePath(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public  final void setAppFont(ViewGroup mContainer, Typeface mFont) {
        if (mContainer == null || mFont == null)
            return;
        final int mCount = mContainer.getChildCount();
        // Loop through all of the children.
        for (int i = 0; i < mCount; ++i) {
            final View mChild = mContainer.getChildAt(i);
            if (mChild instanceof TextView) {
                // Set the font if it is a TextView.
                ((TextView) mChild).setTypeface(mFont);
            }
            if (mChild instanceof EditText) {
                // Set the font if it is a EditText.
                ((EditText) mChild).setTypeface(mFont);
            }
            if (mChild instanceof AutoCompleteTextView) {
                // Set the font if it is a EditText.
                ((AutoCompleteTextView) mChild).setTypeface(mFont);
            }
            if (mChild instanceof Button) {
                // Set the font if it is a Button.
                ((Button) mChild).setTypeface(mFont);
            }
            if (mChild instanceof RadioButton) {
                // Set the font if it is a RadioButton.
                ((RadioButton) mChild).setTypeface(mFont);
            }
            if (mChild instanceof ViewGroup) {
                // Recursively attempt another ViewGroup.
                setAppFont((ViewGroup) mChild, mFont);
            }
        }
    }
    public  void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public  void simpleIntent(Context context, Class aClass, Bundle bundle) {
        Intent intent = new Intent(context, aClass);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public  void simpleIntentForResult(Activity activity, Class aClass, Bundle bundle, int RequestCode) {
        Intent intent = new Intent(activity, aClass);
        intent.putExtras(bundle);
        ((AppCompatActivity) activity).startActivityForResult(intent, RequestCode);
    }
    public  void simpleIntentFinish(Context context, Class aClass, Bundle bundle) {
        Intent intent = new Intent(context, aClass);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
    public void showProgressbar(Context context) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.progress_dialog_activity, null);
        GifImageView gif_image = dialogView.findViewById(R.id.imgProgressDialogLoader);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        if (progressAnimate == null)
            progressAnimate = dialogBuilder.create();

        progressAnimate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        try
        {
            if (!progressAnimate.isShowing()) {
                progressAnimate.show();
            }
        }catch (Exception e)
        {

        }

    }
    public void hideProgressbar() {
        try {
            if (progressAnimate.isShowing()) {
                progressAnimate.dismiss();
            }
        } catch (Exception e) {

        }
    }
    public  boolean isStringEmpty(String str) {
        if (str == null)
            return true;
        else if (str.equals(null))
            return true;
        else if (str.equals(""))
            return true;
        else return str.trim().length() == 0;
    }
    public  void setErrorToEditText(EditText editText, String msg) {
        editText.setError(msg);
        editText.requestFocus();
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
    }
    public  boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=])(?=\\S+$).{3,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
    public String getMonth3Letter(int month) {
        String[] arrayMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        return arrayMonths[month];
    }
    public String roundTwoDecimalsandConvertToString(double d)
    {
        return String.format("%.2f",d);
    }
   public double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    ///set Lang for whole Project
    public void setLang(Context context)
    {
        String lang=getStringPrefrences(context,"AppData","LANG");
        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        if (Build.VERSION.SDK_INT >= 17) {
            conf.setLayoutDirection(myLocale);
        }
        res.updateConfiguration(conf, dm);
        res.getDisplayMetrics();
    }
    ///set lang for particular Page
    public void setLangOfPage(Context context,String lang)
    {
        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        if (Build.VERSION.SDK_INT >= 17) {
            conf.setLayoutDirection(myLocale);
        }
        res.updateConfiguration(conf, dm);
        res.getDisplayMetrics();
    }
    //////Device Model
    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
    public void onSetEditextEditable(EditText editText,Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    public void onAddFragment(Activity activity, Fragment fragment, int containerViewId){
        Log.e("fragmentname",fragment.getClass().getSimpleName());
        ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fade_out)
                .replace(containerViewId, fragment,fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
    }
    public void onReplaceFragment(Activity activity, Fragment fragment, int containerViewId){
        ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fade_out)
                .replace(containerViewId, fragment,fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
    }
    public void setError(TextInputLayout text_input_layout, String Message)
    {
        text_input_layout.setError(Message);
    }
    public void removeError(TextInputLayout text_input_layout)
    {
        text_input_layout.setError(null);
    }

    public void setSpinnerError(Context context,Spinner spinner,TextInputLayout text_input_layout, String error) {
        try {
            text_input_layout.setError(error);
            spinner.setBackground(context.getResources().getDrawable(R.drawable.bg_spinner_error));
        }catch (Exception ex)
        {

        }

    }
    public void setSpinnerDrawable(Context context,Spinner spinner) {
        try {
            spinner.setBackground(context.getResources().getDrawable(R.drawable.bg_spinner));
        }catch (Exception ex)
        {

        }

    } public void setTextviewDrawable(Context context,TextView textView) {
        try {
            textView.setBackground(context.getResources().getDrawable(R.drawable.bg_spinner));
        }catch (Exception ex)
        {

        }

    }
    public void setTextviewrError(Context context,TextView textView,TextInputLayout text_input_layout, String error) {
        try {
            text_input_layout.setError(error);
            textView.setBackground(context.getResources().getDrawable(R.drawable.bg_spinner_error));
        }catch (Exception ex)
        {

        }

    }
    public int getFragmentCount(Activity activity) {
        return  ((AppCompatActivity) activity).getSupportFragmentManager().getBackStackEntryCount();
    }
    public  void onsetEditextEditablefalse(Activity activity,EditText editText){
        hideSoftKeyboard(activity);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }
    public int getSelectedSpinnerIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
    public String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public void setText(TextView textView,String text){
        if(TextUtils.isEmpty(text)){
            textView.setText("-");
        }else {
            textView.setText(text);
        }

    }

    public int onImageRotateAnimation(ImageView imageView,int rotationAngle){
        ObjectAnimator anim = ObjectAnimator.ofFloat(imageView, "rotation",rotationAngle, rotationAngle + 180);
        anim.setDuration(500);
        anim.start();
        rotationAngle += 180;
        rotationAngle = rotationAngle%360;
        return  rotationAngle;
    }

    public void setImage(ImageView view,Context context,String url){
        Glide.with(context)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        //on load failed
                        view.setVisibility(View.VISIBLE);
                        view.setBackgroundResource(R.drawable.profile);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //on load success

                        view.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }
    public int getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }


        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
        int DayCount= (int) (dayCount+1);

        return DayCount;
    }

    public static String formatSize(float size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
                if (size >= 1024) {
                    suffix = "GB";
                    size /= 1024;
                }
            }
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        StringBuilder resultBuffer = new StringBuilder(df.format(size));

//
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }
}
