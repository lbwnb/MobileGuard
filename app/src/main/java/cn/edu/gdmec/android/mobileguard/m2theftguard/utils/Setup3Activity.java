package cn.edu.gdmec.android.mobileguard.m2theftguard.utils;

import android.os.Bundle;
import android.widget.RadioButton;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by Administrator on 2017/10/11.
 */



public class Setup3Activity extends BaseSetUpActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_setup_3);
        ((RadioButton)findViewById(R.id.rb_third)).setChecked(true);

    }
    @Override
    public void showNext() {
startActivityAndFinishSelf(Setup4Activity.class);
    }

    @Override
    public void showPre() {
startActivityAndFinishSelf(Setup2Activity.class);
    }
}
