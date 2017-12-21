package cn.edu.gdmec.android.mobileguard.m8trafficmonitor.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import cn.edu.gdmec.android.mobileguard.m8trafficmonitor.service.TrafficMonitoringService;
import cn.edu.gdmec.android.mobileguard.m8trafficmonitor.utils.SystemInfoUtils;

/**
 * Created by asus on 2017/11/26.
 */

public class BootCompleteReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //开机广播
        //判断流量监控服务是否开启。如果没有开启则开启
        if (!SystemInfoUtils.isServiceRunning(context,
                "cn.edu.android.mobileguard.m8trafficmonitor.service.TrafficMonitoringService")){
            //开启服务
            Log.d("traffic service","turn on");
            context.startService(new Intent(context, TrafficMonitoringService.class));
        }

    }
}
