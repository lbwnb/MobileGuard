package cn.edu.gdmec.android.mobileguard.m9advancedtools.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m4appmanager.entity.AppInfo;

/**
 * Created by asus on 2017/12/19.
 */

public class AppLockAdapter extends BaseAdapter {
    private List<AppInfo> appInfos;
    private Context context;

    public AppLockAdapter(List<AppInfo> appInfos, Context context) {
        super();
        this.appInfos = appInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return appInfos.size();
    }


    @Override
    public Object getItem(int position) {
        return appInfos.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null && convertView instanceof RelativeLayout) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_list_applock, null);
            viewHolder.mAppIconImgv = (ImageView) convertView.findViewById(R.id.imgv_appicon);
            viewHolder.mAppNameTV = (TextView) convertView.findViewById(R.id.tv_appname);
            viewHolder.mLockIcon = (ImageView) convertView.findViewById(R.id.imgv_lock);
            convertView.setTag(viewHolder);
        }
        final AppInfo appInfo = appInfos.get(position);
        viewHolder.mAppIconImgv.setImageDrawable(appInfo.icon);
        viewHolder.mAppNameTV.setText(appInfo.appName);
        if (appInfo.isLock) {
            viewHolder.mLockIcon.setBackgroundResource(R.drawable.applock_icon);
        } else {
            viewHolder.mLockIcon.setBackgroundResource(R.drawable.appunlock_icon);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView mAppNameTV;
        ImageView mAppIconImgv;
        /**
         * 控制图片显示加锁还是不加锁
         */
        ImageView mLockIcon;
    }
}
