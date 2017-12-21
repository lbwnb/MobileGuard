package cn.edu.gdmec.android.mobileguard.m1home;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import cn.edu.gdmec.android.mobileguard.HomeActivity;
import cn.edu.gdmec.android.mobileguard.m1home.utils.MyUtils;
import cn.edu.gdmec.android.mobileguard.m1home.utils.VersionUpdateUtils;
import cn.edu.gdmec.android.mobileguard.m5virusscan.VirusScanActivity;

public class SplashActivity extends AppCompatActivity {
    /**
     * 应用的版本号
     * @param savedInstanceState
     */
    private TextView mVersionTV;
    /** 本地版本号*/
    private String mVersion;
    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置该Activity没有标题栏，在加载布局之前调用

       setContentView(cn.edu.gdmec.android.mobileguard.R.layout.activity_splash);
        getSupportActionBar().hide();
        mVersion = MyUtils.getVersion(getApplicationContext());
        mVersionTV = (TextView)findViewById(cn.edu.gdmec.android.mobileguard.R.id.tv_splash_version);
        mVersionTV.setText("版本号："+mVersion);
        if (!hasPermission()) {
            //若用户未开启权限，则引导用户开启“Apps with usage access” 权限
            startActivityForResult(
                    new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                    MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS
            );
        }
        VersionUpdateUtils.DownloadCallback downloadCallback = new VersionUpdateUtils.DownloadCallback() {
            @Override
            public void afterDownload(String filename) {
              MyUtils.installApk(SplashActivity.this,filename);
            }
        };

        final VersionUpdateUtils versionUpdateUtils = new VersionUpdateUtils(mVersion,SplashActivity.this,downloadCallback,HomeActivity.class);
        new Thread(){
            @Override
            public void run() {
                versionUpdateUtils.getCloudVersion("http://android2017.duapp.com/updateinfo.html");
            }
        }.start();
    }

    private boolean hasPermission(){
        AppOpsManager appOps = (AppOpsManager)getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(),getPackageName());
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS){
            if (!hasPermission()){
                //若用户未开启权限，则引导用户开启“Apps with usage access ”权限
                startActivityForResult(
                        new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                        MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
            }

        }
    }
}
