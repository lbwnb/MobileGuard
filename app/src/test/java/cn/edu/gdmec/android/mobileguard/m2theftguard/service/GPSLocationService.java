package cn.edu.gdmec.android.mobileguard.m2theftguard.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;

/**
 * Created by acer on 2017/10/20.
 */

public class GPSLocationService extends Service {
   private LocationManager lm;
    private MyListener listener;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        listener = new MyListener();
        //criteria查询条件
        //true 只返回可用的位置提供者
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(true);
        String name = lm.getBestProvider(criteria,true);
        //权限检查
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            return;
        }
        lm.requestLocationUpdates(name,0,0,listener);
    }
    private class MyListener implements LocationListener{


        @Override
        public void onLocationChanged(Location location) {
            StringBuilder sb = new StringBuilder();
            sb.append("accuracy:"+location.getAccuracy()+"\n");
            sb.append("speed:"+location.getSpeed()+"\n");
            sb.append("Logitude:"+location.getLongitude()+"\n");
            sb.append("Latitude:"+location.getLatitude()+"\n");
            String result = sb.toString();
            SharedPreferences sp = getSharedPreferences("config",MODE_PRIVATE);
            String safenumber = sp.getString("safephone","");
            //发送GPS坐标
            SmsManager.getDefault().sendTextMessage(safenumber,null,result,null,null);
            stopSelf();

        }
        //当提供者 状态发生变化的时候调用的方法

        //当某个位置提供者 可用的时候调用
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        //当某个位置提供者  不可用的时候调用
        @Override
        public void onProviderEnabled(String provider) {

        }


        @Override
        public void onProviderDisabled(String provider) {

        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        lm.removeUpdates(listener);
        listener = null;
    }
}
