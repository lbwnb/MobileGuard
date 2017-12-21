package cn.edu.gdmec.android.mobileguard.m3communicationguard.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ObbInfo;
import android.os.Handler;
import android.telephony.SmsMessage;

import cn.edu.gdmec.android.mobileguard.m3communicationguard.db.dao.BlackNumberDao;

/**
 * Created by asus on 2017/10/29.
 */

public class InterceptSmsReciever extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences mSP = context.getSharedPreferences("config",Context.MODE_APPEND);
        boolean BlackNumStatus = mSP.getBoolean("BlackNumStatus",true);
        if(!BlackNumStatus){
            //黑名单拦截关闭
            return;
        }
        //如果是黑名单，则终止广播
        BlackNumberDao dao = new BlackNumberDao(context);
        Object[] objs = (Object[]) intent.getExtras().get("pdus");
        for(Object obj:objs){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
            String sender = smsMessage.getOriginatingAddress();
            String body = smsMessage.getMessageBody();
            if (sender.startsWith("+86")){
                sender = sender.substring(3,sender.length());

            }
            int mode = dao.getBlackContactMode(sender);
            if (mode==2||mode==3){
                //需要拦截的短信，拦截广播
                abortBroadcast();
            }
        }
    }
}
