package cn.edu.gdmec.android.mobileguard;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * ，
 * Created by acer on 2017/10/14.
 */

public class App extends Application {
    public static final String APPLOCK_ACTION = "cn.edu.gdmec.android.mobileguard.m9advancedtools.applock";
    public static final String APPLOCK_CONTENT_URI = "content://cn.edu.gdmec.android.mobileguard.m9advancedtools.applock";
    @Override
    public void onCreate() {
        super.onCreate();
        correctSIM();
    }

    public void correctSIM() {
        //检查SIM卡是否发生变化
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        //获取防盗保护状态
        boolean protecting = sp.getBoolean("protecting", true);
        if (protecting) {
            //得到绑定的SIM卡串号
            String bindsim = sp.getString("sim", "");

            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            //为了
            String realsim = tm.getSimSerialNumber();
            realsim = "999";
            if (bindsim.equals(realsim)) {
                Log.i("", "sim卡未发生变化，还是您的手机");

            } else {
                Log.i("", "SIM卡变化了");
                //
                String safenumber = sp.getString("safephone", "");
                if (!TextUtils.isEmpty(safenumber)) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(safenumber, null, "你的亲友手机的sim卡已经被更换！", null, null);
                }
            }

        }
    }
}
