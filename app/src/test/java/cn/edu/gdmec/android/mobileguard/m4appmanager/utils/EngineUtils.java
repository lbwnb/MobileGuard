package cn.edu.gdmec.android.mobileguard.m4appmanager.utils;


import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import android.content.pm.Signature;
import android.net.Uri;

import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.List;

import cn.edu.gdmec.android.mobileguard.m4appmanager.entity.AppInfo;

/**
 * Created by student on 17/11/6.
 */

public class EngineUtils {
    /*分享应用*/
    public static void shareApplication(Context context, AppInfo appInfo){
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT," 推荐您使用一款软件，名称叫："+appInfo.appName+"下载路径：https://play.google.com/store/apps/details?id="+appInfo.packageName);
        context.startActivity(intent);

    }
     /*开启应用程序*/
    public static  void startApplication(Context context,AppInfo appInfo){
        //  打开这个应用程序的入口activity
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(appInfo.packageName);
        if (intent!=null){
            context.startActivity(intent);
        }else{
            Toast.makeText(context,"该应用没有启动页面",Toast.LENGTH_LONG).show();
        }
    }
    /*开启应用的设置页面*/
     public static void SettingAppDetail(Context context,AppInfo appInfo){
         Intent intent = new Intent();
         intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
         intent.addCategory(Intent.CATEGORY_DEFAULT);
         intent.setData(Uri.parse("package:"+appInfo.packageName));
         context.startActivity(intent);
     }
     /*卸载*/
     public static void uninstallApplication(Context context,AppInfo appInfo){
         if (appInfo.isUserApp){
             Intent intent = new Intent();
             intent.setAction(Intent.ACTION_DELETE);
             intent.setData(Uri.parse("package:"+appInfo.packageName));
             context.startActivity(intent);
         }else {
             Toast.makeText(context,"系统应用无法卸载 ",Toast.LENGTH_LONG).show();
         }
     }
    /*获取版本号*/
    public static void About(Context context,AppInfo appInfo) throws PackageManager.NameNotFoundException {
        Intent intent = new Intent();
        intent.setType("text/plain");
        PackageManager packageManager = context.getPackageManager();
        StringBuilder builer = new StringBuilder();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        PackageInfo packageInfo = packageManager.getPackageInfo(appInfo.packageName,0);
        try {


            long firstInstallTime = packageInfo.firstInstallTime;
            PackageInfo pi = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] perssion = pi.requestedPermissions;
            for(int i=0;i<perssion.length;i++){
                builer.append(perssion[i]).toString();
            }
            String version = packageInfo.versionName;

            /*发布者信息*/
            PackageInfo packinfo = packageManager.getPackageInfo(appInfo.packageName, PackageManager.GET_SIGNATURES);


           final Signature[] singatures = packinfo.signatures;
            for(final Signature sig:singatures){
                final  byte[] rawCert = sig.toByteArray();
                InputStream certStream = new ByteArrayInputStream(rawCert);
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
                X509Certificate x509Certificate = (X509Certificate)certificateFactory.generateCertificate(certStream);
                appInfo.signature = "Certificate issuer: "+x509Certificate.getIssuerDN()+"+\n";
            }
            /* 应用权限*/
            intent.putExtra(Intent.EXTRA_TEXT,version+firstInstallTime+singatures+perssion);
            builder.setTitle("MobileGuard");
            /*时间*/
            SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 a hh:mm:ss");
            builder.setMessage("Version："+version+"\n"+"install time:"+format.format(firstInstallTime)+"\n"+appInfo.signature+"\n"+"Perssions:\n"+builer);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();

        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }

    public static String getpackageName(Context context, AppInfo appInfo) throws PackageManager.NameNotFoundException {
        Intent intent = new Intent();
        intent.setType("text/plain");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        PackageManager packageManager = context.getPackageManager();

        ActivityInfo[] pi = packageManager.getPackageInfo(appInfo.packageName, PackageManager.GET_ACTIVITIES).activities;

        StringBuilder builer = new StringBuilder();
        for(int i=0;i<pi.length;i++){
            builer.append(pi[i]+"\n");

        }
            builder.setMessage("Activities:\n"+builer);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();


        return null;
    }


}
