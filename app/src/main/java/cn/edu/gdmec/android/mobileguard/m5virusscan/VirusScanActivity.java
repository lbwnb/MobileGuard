package cn.edu.gdmec.android.mobileguard.m5virusscan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by asus on 2017/11/17.
 */

public class VirusScanActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mLastTimeTV;

    private SharedPreferences mSP;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_virus_scan );
        mSP = getSharedPreferences ( "config", MODE_PRIVATE );
        copyDB("antivirus.db");
        initView();

    }
    @Override
    protected void onResume() {
        String string=mSP.getString ( "lastVirusScan", "您还没有查杀病毒！" );
        mLastTimeTV.setText ( string );
        super.onResume ();
    }


    //拷贝病毒数据库
    private void copyDB(final String dbname) {
        //大文件的拷贝复制一定要用线程，否则很容易出现ANR
        new Thread (  ){
            public void run(){
                try{
                    File file = new File ( getFilesDir (),dbname );
                    if (file.exists ()&&file.length ()>0){
                        Log.i ("VirusScanActivity","数据库已存在！");
                        return;
                    }
                    InputStream is = getAssets().open(dbname);
                    FileOutputStream fos = openFileOutput ( dbname, MODE_PRIVATE );
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read (buffer))!=-1){
                        fos.write ( buffer, 0, len );
                    }
                    is.close ();
                    fos.close ();



                }catch (Exception e){
                    e.printStackTrace ();
                }
            };
        }.start ();
    }

    //初始化UI控件
    private void initView(){
        findViewById ( R.id.rl_titlebar ).setBackgroundColor (
                getResources ().getColor ( R.color.light_blue ) );
        ImageView mLeftImgv = (ImageView ) findViewById ( R.id.imgv_leftbtn );
        ((TextView) findViewById ( R.id.tv_title )).setText ( "病毒查杀" );
        mLeftImgv.setOnClickListener ( this );
        mLeftImgv.setImageResource ( R.drawable.back );
        mLastTimeTV = (TextView) findViewById ( R.id.tv_lastscantime );
        findViewById ( R.id.rl_allscanvirus ).setOnClickListener ( this );
    }
    @Override
    public void onClick(View view){
        switch (view.getId ()){
            case R.id.imgv_leftbtn:
                finish ();
                break;
            case R.id.rl_allscanvirus:
                startActivity(new Intent ( this,VirusScanSpeedActivity.class ));
                break;
        }
    }
}
