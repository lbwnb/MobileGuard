package cn.edu.gdmec.android.mobileguard.m1home.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

import cn.edu.gdmec.android.mobileguard.m5virusscan.dao.AntiVirusDao;

/**
 * Created by asus on 2017/9/9.
 */

public class MyUtils {
    /**
     * 获取版本号
     * @param context
     * @return 返回版本号
     */
    public static String getVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(),0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 安装新版本
     * @param activity
     * @param filename
     */
    public static void installApk(Activity activity, String filename){
        Intent intent = new Intent("android.intent.action.VIEW");
        //添加默认分类
        intent.addCategory("android.intent.category.DEFAULT");
        //设置数据和类型
        intent.setDataAndType(Uri.fromFile(

                new File(Environment.getExternalStoragePublicDirectory("/download/").getPath()+"/"+filename)),

                "application/vnd.android.package-archive");
        //如果开启的Activity退出时会回调当前Activity的onActivityResult
        activity.startActivityForResult(intent,0);
    }

}
