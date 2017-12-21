package cn.edu.gdmec.android.mobileguard.m4appmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.mobileguard.App;
import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m4appmanager.adapter.AppManagerAdapter;
import cn.edu.gdmec.android.mobileguard.m4appmanager.entity.AppInfo;
import cn.edu.gdmec.android.mobileguard.m4appmanager.utils.AppInfoParser;
import cn.edu.gdmec.android.mobileguard.m4appmanager.utils.EngineUtils;

/**
 * Created by student on 17/11/6.
 */

public class AppManagerActivity extends AppCompatActivity implements View.OnClickListener {
    // 手机剩余内存
    private TextView mPhoneMemoryTV;

    private TextView mSDMemoryTV;
    private ListView mListView;
    private List<AppInfo> appInfos;
    private List<AppInfo> userAppInfos = new ArrayList<AppInfo>();
    private List<AppInfo> systemAppInfos = new ArrayList<AppInfo>();
    private AppManagerAdapter adapter;
    private TextView mAppNumTV;
    private TextView mAbout;

    //接收应用程序卸载成功的广播
    private UninstallReciver receciver;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 10:
                    if (adapter == null){
                        adapter = new AppManagerAdapter(userAppInfos,systemAppInfos,AppManagerActivity.this);
                    }
                    mListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                case 15:
                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_app_manager);
        //  注册广播
        receciver = new UninstallReciver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(receciver,intentFilter);
        initView();
    }
    //初始化控件
     private void initView(){
         findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_yellow));
         ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
         ((TextView)findViewById(R.id.tv_title)).setText("软件管家");
         mLeftImgv.setOnClickListener(this);
         mLeftImgv.setImageResource(R.drawable.back);
         mPhoneMemoryTV = (TextView)findViewById(R.id.tv_phonememory_appmanager);
         mSDMemoryTV = (TextView)findViewById(R.id.tv_sdmemory_appmanager);
         mAppNumTV = (TextView)findViewById(R.id.tv_appnumber);
         mListView = (ListView)findViewById(R.id.lv_appmanager);
         mAbout = (TextView)findViewById(R.id.tv_about_app);
         getMemoryfromPhone();
         initData();
         initListener();

     }
     private  void initListener(){
         mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {
                 if (adapter!=null){
                     new Thread(){
                         public void run(){
                             AppInfo mappInfo = (AppInfo) adapter.getItem(i);
                             // 记住当前条目状态
                             boolean flag = mappInfo.isSelected;
                             //  先将集合所有条目的AppInfo变为未选中状态
                              for (AppInfo appInfo:appInfos){
                                  appInfo.isSelected=false;
                              }
                              for (AppInfo appInfo:systemAppInfos){
                                  appInfo.isSelected=false;
                              }
                              if (mappInfo!=null){
                                  // 如果已经选中，则变为未选中
                                  if (flag){
                                      mappInfo.isSelected=false;
                                  }else {
                                      mappInfo.isSelected=true;
                                  }
                                  mHandler.sendEmptyMessage(15);
                              }
                         };
                     }.start();
                 }

             }
         });
         mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
             @Override
             public void onScrollStateChanged(AbsListView absListView, int i) {

             }

             @Override
             public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                 if (i>=userAppInfos.size()+1){
                     mAppNumTV.setText("系统程序："+systemAppInfos.size()+"个");
                 }else {
                     mAppNumTV.setText("用户程序："+userAppInfos.size()+"个");
                 }

             }
         });
     }
     private void initData(){
         appInfos = new ArrayList<AppInfo>();
         new Thread(){
             public void run(){
                 appInfos.clear();
                 userAppInfos.clear();
                 systemAppInfos.clear();
                 appInfos.addAll(AppInfoParser.getAppInfos(AppManagerActivity.this));
                 for (AppInfo appInfo:appInfos){
                     // 如果是用户
                      if (appInfo.isUserApp){
                          userAppInfos.add(appInfo);
                      }else {
                          systemAppInfos.add(appInfo);
                      }
                 }
                 mHandler.sendEmptyMessage(10);
             };
         }.start();

     }

     /*拿到手机和SD卡剩余的内存
     * */
     private void getMemoryfromPhone(){
         long avail_sd = Environment.getExternalStorageDirectory().getFreeSpace();
         long avail_rom = Environment.getDataDirectory().getFreeSpace();
         //格式化内存
          String str_avail_sd = Formatter.formatFileSize(this,avail_sd);
         String str_avail_rom = Formatter.formatFileSize(this,avail_rom);
         mPhoneMemoryTV.setText("剩余手机内存："+str_avail_rom);
         mSDMemoryTV.setText("剩余SD卡内存："+str_avail_sd);
     }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
        }


    }

    //接收应用程序卸载的广播
    class UninstallReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //收到广播；
            initData();

        }
    }
    @Override
    protected void onDestroy(){
        unregisterReceiver(receciver);
        receciver = null;
        super.onDestroy();
    }

}
