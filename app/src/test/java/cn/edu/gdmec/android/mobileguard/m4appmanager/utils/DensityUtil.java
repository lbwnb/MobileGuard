package cn.edu.gdmec.android.mobileguard.m4appmanager.utils;

import android.content.Context;

/**
 * Created by student on 17/11/6.
 */

public class DensityUtil {
    /*dip 转换像素*/

    public static int dip2px(Context context,float dpValue){
        try{
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int)(dpValue * scale + 0.5f);

        }catch (Exception e){
            e.printStackTrace();
        }
        return (int) dpValue;
    }
    /*像素转换为dip*/
    public static int px2dip(Context context,float pxValue){
        try{
            final float scale = context.getResources().getDisplayMetrics().density;
        }catch (Exception e){
            e.printStackTrace();
        }
        return (int) pxValue;
    }
}
