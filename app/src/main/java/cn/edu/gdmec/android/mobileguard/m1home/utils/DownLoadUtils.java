package cn.edu.gdmec.android.mobileguard.m1home.utils;

import android.app.DownloadManager;

import android.content.Context;

import android.net.Uri;

import android.webkit.MimeTypeMap;


/**

 * Created by asus on 2017/9/9.

 *

 */



public class DownLoadUtils {

    /**

     * 下载APK的方法

     * @param url

     * @param targetFile

     */

    public void downloadApk(String url, String targetFile, Context context){

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        request.setAllowedOverRoaming(false);

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        String mineString = mimeTypeMap.getMimeTypeFromExtension(mimeTypeMap.getFileExtensionFromUrl(url));

        request.setMimeType(mineString);



        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

        request.setVisibleInDownloadsUi(true);



        request.setDestinationInExternalPublicDir("/download",targetFile);

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        long mTaskid = downloadManager.enqueue(request);



    /*//创建HttpUtils对象

        HttpUtils httpUtils = new HttpUtils();

    //调用HttpUtils下载的方法下载指定文件

        httpUtils.download(url, targetFile, new RequestCallBack<File>() {

            @Override

            public void onSuccess(ResponseInfo<File> arg0) {

                myCallBack.onSuccess(arg0);

            }

            @Override

            public void onFailure(HttpException arg0, String arg1) {

                myCallBack.onFailure(arg0,arg1);

            }

            @Override

            public void onLoading(long total,long current,boolean isUploading){

                super.onLoading(total,current,isUploading);

                myCallBack.onLoadding(total,current,isUploading);

            }



        });

    }

    *//**

         *接口，用于监听下载状态的接口

         *//*

   interface MyCallBack{

        *//**

         * 下载成功时调用

         *//*

        void onSuccess(ResponseInfo<File> arg0);

        *//**

         * 下载失败时调用

         *//*

        void onFailure(HttpException arg0,String arg1);

        *//**

         * 下载中调用

         *//*

        void onLoadding(long total,long current,boolean isUpLoading);

*/

    }





}