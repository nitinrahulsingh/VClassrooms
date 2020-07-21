package com.vclassrooms.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.vclassrooms.R;

import java.util.ArrayList;

/**
 * Created by Rahul on 10,July,2020
 */
public class FilePreviewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FilePreviewActivity";

    WebView webView;
    String pdfUrl, pdfName;
    TextView txtTitle;
    ImageView imgView_Back_arrow, imgView_download, imgView_reload;
    ProgressBar progressBars;
    private String PreUrl = "https://docs.google.com/viewer?embedded=true&url=";
    String strPath = "", strExtension = "", strName = "", strPostdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_preview);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            strPath = bundle.getString("path");
            strExtension = bundle.getString("extension");
            strName = bundle.getString("name");
            strPostdate = bundle.getString("postdate");
        }

        imgView_Back_arrow = (ImageView) findViewById(R.id.imgView_Back_arrow);
        imgView_Back_arrow.setOnClickListener(this);
        imgView_reload = (ImageView) findViewById(R.id.imgView_reload);
        imgView_reload.setOnClickListener(this);
        imgView_download = (ImageView) findViewById(R.id.imgView_download);
        imgView_download.setOnClickListener(this);
        txtTitle = findViewById(R.id.toolbarHome_Title);
        webView = findViewById(R.id.webView_PdfViewer);
        progressBars = findViewById(R.id.progressBars);
        progressBars.setMax(100);
        load();

        txtTitle.setText(strName);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void load() {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        if (strExtension.equalsIgnoreCase(".pdf")) {
            webView.loadUrl(PreUrl + strPath);
        } else if (strExtension.equalsIgnoreCase(".jpeg")) {
            webView.loadUrl(strPath);
        } else if (strExtension.equalsIgnoreCase(".jpg")) {
            webView.loadUrl(strPath);
        } else if (strExtension.equalsIgnoreCase(".png")) {
            webView.loadUrl(strPath);
        } else if (strExtension.equalsIgnoreCase(".rpt")) {
            webView.loadUrl(PreUrl + strPath);
        } else {
            webView.loadUrl(PreUrl + strPath);
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressBars.setVisibility(View.VISIBLE);
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBars.setVisibility(View.GONE);
                progressBars.setProgress(100);
                webView.loadUrl("javascript:(function() { " +
                        "document.querySelector('[role=\"toolbar\"]').remove();})()");

            }

        });

    }

    private void downloadStudyMAterial(String material, String date,
                                       String extension, String filesItemName) {
        Log.d(TAG, material + " " + date + " " + extension + " " + " " + filesItemName);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(material));
        request.setDescription(getString(R.string.your_dowloads_will_be_available_soon));
        request.setTitle(filesItemName + " " +getString(R.string.file_dowloads));
        // in order for this if to run, you must use the android 3.2 to compile your app
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "VClassrooms" + "/" + filesItemName + "_" + date + extension);
        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        long refid = manager.enqueue(request);
        Log.d(TAG, refid + " Downloaded ");
        Toast.makeText(FilePreviewActivity.this,getString(R.string.document_saved),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.imgView_Back_arrow:
                finish();
                break;
            case R.id.imgView_reload:
                load();
                progressBars.setVisibility(View.GONE);
                break;
            case R.id.imgView_download:

                TedPermission();
                break;
        }
    }


    public void TedPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                downloadStudyMAterial(strPath, strPostdate, strExtension, strName);

            }
            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                //app.showToast(FilePreviewActivity.this, getString(R.string.Permission_Denied)+"\n" + deniedPermissions.toString());
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getString(R.string.Please_give_permission_for_app_functionality))
                .setDeniedMessage(getString(R.string.If_you_reject_permission_you_can_not_use_this_service)+"\n\n"+getString(R.string.Please_turn_on_permissions_at))
                .setGotoSettingButtonText("setting")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

    }

}

