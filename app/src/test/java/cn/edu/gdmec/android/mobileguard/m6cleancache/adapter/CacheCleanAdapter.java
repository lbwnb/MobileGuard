package cn.edu.gdmec.android.mobileguard.m6cleancache.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m6cleancache.entity.CacheInfo;

/**
 * Created by asus on 2017/11/20.
 */

public class CacheCleanAdapter extends BaseAdapter {
  private Context context;
    private List<CacheInfo> cacheInfos;


    public CacheCleanAdapter(Context context,List<CacheInfo> cacheInfos) {
        this.context = context;
        this.cacheInfos = cacheInfos;

    }

    @Override
    public int getCount() {
        return cacheInfos.size();
    }


    @Override
    public Object getItem(int position) {
        return cacheInfos.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView =convertView.inflate(context, R.layout.item_cacheclean_list,null);
            viewHolder.mAppIconImgv = (ImageView)convertView.findViewById(R.id.imgv_appicon_cacheclean);
            viewHolder.mAppNameTV = (TextView)convertView.findViewById(R.id.tv_appname_cacheclean);
            viewHolder.mCacheSizeTV = (TextView)convertView.findViewById(R.id.tv_appsize_cacheclean);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CacheInfo cacheInfo = cacheInfos.get(position);
        viewHolder.mAppIconImgv.setImageDrawable(cacheInfo.appIcon);
        viewHolder.mAppNameTV.setText(cacheInfo.appName);
        viewHolder.mCacheSizeTV.setText(Formatter.formatFileSize(context,cacheInfo.cacheSize));
        return convertView;
    }
    class ViewHolder {
        ImageView mAppIconImgv;
        TextView mAppNameTV;
        TextView mCacheSizeTV;
    }
}
