package cn.edu.gdmec.android.mobileguard.m2theftguard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.edu.gdmec.android.mobileguard.R;



/**
 * Created by asus on 2017/10/13.
 */
@SuppressLint("ShowToast")
public class SetUp3Activity extends BaseSetUpActivity implements View.OnClickListener {
    private EditText mInputPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        initView();

    }
    private void initView(){
        ((RadioButton)findViewById(R.id.rb_third)).setChecked(true);
        findViewById(R.id.btn_addcontact).setOnClickListener(this);
       mInputPhone = (EditText) findViewById(R.id.et_inputphone);
        String safephone = sp.getString("safephone",null);
        if (!TextUtils.isEmpty(safephone)){
            mInputPhone.setText(safephone);
        }
    }
    /**
     * Called when a view has been clicked.
     *
     * //@param v The view that was clicked.
     */
    @Override
    public void showNext() {
       //判断文本框中是否有电话号码
        String safephone = mInputPhone.getText().toString().trim();
        if (TextUtils.isEmpty(safephone)){
            Toast.makeText(this,"请输入安全号码",Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences.Editor edit = sp.edit();
        edit.putString("safephone",safephone);
        edit.commit();
        startActivityAndFinishSelf(SetUp4Activity.class);
    }

    @Override
    public void showPre() {
        startActivityAndFinishSelf(SetUp2Activity.class);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_addcontact:
                //启动联系人选择activity并获取返回值
                startActivityForResult(new Intent(this,ContactSelectActivity.class),0);
                break;
        }
    }
    //获取被调用的activity的返回值
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (data!=null){
            String phone = data.getStringExtra("phone");
            mInputPhone.setText(phone);
        }
    }
}
