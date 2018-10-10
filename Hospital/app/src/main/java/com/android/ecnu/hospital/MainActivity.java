package com.android.ecnu.hospital;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    public static String version ;

    private String TAG = "hospital";

    private WebView webView;

    private DrawerLayout mDrawerLayout;

    private WebViewReceiver webViewReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        version = String.valueOf(getVersionCode(this));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.ecnu.hospital.WEBVIEW");
        webViewReceiver = new WebViewReceiver();
        registerReceiver(webViewReceiver, intentFilter);
        //toolbar设置
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }

        //webview设置
        webView = findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                Log.d(TAG, "开始");
                if ( uri.getScheme().equals("version")) {
                    if (uri.getAuthority().equals("update")) {
                        String path = uri.getQueryParameter("path");
                        download(path);
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                MainActivity.this.setTitle(title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        logout();
        webView.loadUrl("http://jzdc.ecnucpp.com:6100/app/login.html");


        //菜单项设置
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.ser_inter);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.log_out:
                        log_out();
                        break;
                    case R.id.ver_update:
                        ver_update();
                        break;
                    case R.id.edit_pass:
                        edit_pass();
                        break;
                    case R.id.med_situ:
                        med_situ();
                        break;
                    case R.id.health_situ:
                        health_situ();
                        break;
                    case R.id.health_monitor:
                        health_monitor();
                        break;
                    case R.id.four_diag:
                        four_diag();
                        break;
                    case R.id.ser_res:
                        ser_res();
                        break;
                    case R.id.ser_inter:
                        ser_inter();
                        break;
                    case R.id.ser_monitor:
                        ser_monitor();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        Intent intent = new Intent(this, MyService.class);
        this.startService(intent);

    }

    @Override
    protected void onRestart() {
        Intent intent = new Intent(this, MyService.class);
        this.startService(intent);
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(webViewReceiver);
        super.onDestroy();
    }

    //toolbar菜单按钮设置
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    //返回键返回上一个网页而不是退出app
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public void logout(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .cookieJar(new WebViewCookieHandler())
                            .build();
                    Request request = new Request.Builder()
                            .url("http://jzdc.ecnucpp.com:6100/api/users/logout")
                            .build();
                    client.newCall(request).execute();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void download(String path){
        final String packageName = "com.android.ecnu.hospital";
        int state = this.getPackageManager().getApplicationEnabledSetting(packageName);
        //检测下载管理器是否被禁用
        if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("温馨提示").setMessage
                    ("系统下载管理器被禁止，需手动打开").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    try {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + packageName));
                        MainActivity.this.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                        MainActivity.this.startActivity(intent);
                    }
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } else {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(path));
            request.setDescription("下载中");
            request.setTitle("曙光医院");
            request.allowScanningByMediaScanner();//设置可以被扫描到
            request.setVisibleInDownloadsUi(true);// 设置下载可见
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);//下载完成后通知栏任然可见
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "hospital.apk");
            Log.d(TAG, Environment.DIRECTORY_DOWNLOADS);
            DownloadManager manager = (DownloadManager) MainActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);

            // manager.enqueue(request);
            long Id = manager.enqueue(request);
            Cursor cursor = manager.query(new DownloadManager.Query().setFilterById(Id));
            if (cursor != null) {

                while (cursor.moveToNext()) {

                    String bytesDownload = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    String descrition = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));
                    String id = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
                    String localUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    String mimeType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
                    String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
                    String status = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    String totalSize = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    Log.i(TAG, "bytesDownload:" + bytesDownload);
                    Log.i(TAG, "descrition:" + descrition);
                    Log.i(TAG, "id:" + id);
                    Log.i(TAG, "localUri:" + localUri);
                    Log.i(TAG, "mimeType:" + mimeType);
                    Log.i(TAG, "title:" + title);
                    Log.i(TAG, "status:" + status);
                    Log.i(TAG, "totalSize:" + totalSize);

                }
            }
            Log.d(TAG, "开始下载任务:" + Id + " ...");
        }
    }

    public void log_out(){
        mDrawerLayout.closeDrawers();
        logout();
        webView.loadUrl("http://jzdc.ecnucpp.com:6100/app/login.html");
    }
    public void ver_update(){
        mDrawerLayout.closeDrawers();
        webView.loadUrl("http://jzdc.ecnucpp.com:6100/app/version_updating.html?version="+version);
    }
    public void edit_pass(){
        mDrawerLayout.closeDrawers();
        webView.loadUrl("http://jzdc.ecnucpp.com:6100/app/edit_password.html");
    }
    public void med_situ(){
        mDrawerLayout.closeDrawers();
        webView.loadUrl("http://jzdc.ecnucpp.com:6100/app/medicine_situation.html");
    }
    public void health_situ(){
        mDrawerLayout.closeDrawers();
        webView.loadUrl("http://jzdc.ecnucpp.com:6100/app/health_situation.html");
    }
    public void health_monitor(){
        mDrawerLayout.closeDrawers();
        webView.loadUrl("http://jzdc.ecnucpp.com:6100/app/health_monitor.html");
    }
    public void four_diag(){
        mDrawerLayout.closeDrawers();
        webView.loadUrl("http://jzdc.ecnucpp.com:6100/app/four_diagnostic.html");
    }
    public void ser_res(){
        mDrawerLayout.closeDrawers();
        webView.loadUrl("http://jzdc.ecnucpp.com:6100/app/service_reservation.html");
    }
    public void ser_inter(){
        mDrawerLayout.closeDrawers();
        webView.loadUrl("http://jzdc.ecnucpp.com:6100/app/service_interview.html");
    }
    public void ser_monitor(){
        mDrawerLayout.closeDrawers();
        webView.loadUrl("http://jzdc.ecnucpp.com:6100/app/service_monitor.html");
    }


    class WebViewReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.android.ecnu.hospital.WEBVIEW")){
                String type = intent.getStringExtra("Type");
                if(type != null){
                    switch (type){
                        case "Diagnose_Result":
                            webView.loadUrl("http://jzdc.ecnucpp.com:6100/app/four_diagnostic.html");
                            break;
                        case "Monitor_Timeout":
                            webView.loadUrl("http://jzdc.ecnucpp.com:6100/app/health_monitor.html");
                            break;
                        case "Get_Questionnaire":
                            webView.loadUrl("http://jzdc.ecnucpp.com:6100/app/service_interview.html");
                            break;
                        case "Version_Update":
                            webView.loadUrl("http://jzdc.ecnucpp.com:6100/app/version_updating.html");
                            break;
                        default:
                            break;
                    }
                }

            }

        }
    }
}


