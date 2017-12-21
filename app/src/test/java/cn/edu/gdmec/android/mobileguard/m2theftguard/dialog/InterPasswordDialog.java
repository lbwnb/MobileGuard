package cn.edu.gdmec.android.mobileguard.m2theftguard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by asus on 2017/9/24.
 */

public class InterPasswordDialog extends Dialog implements  View.OnClickListener{
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    //对话框标题
    private TextView mTitleTV;
    //输入密码文本框
    private EditText mInterET;
    //确认按钮
    private Button mOKBtn;
    //取消按钮
    private Button mCancleBtn;
    private MyCallBack myCallBack;
    private Context context;

   public InterPasswordDialog(Context context){
       super(context,R.style.dialog_custom);
       this.context = context;
    }
    public void setCallBack(MyCallBack myCallBack){
        this.myCallBack = myCallBack;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        setContentView(R.layout.inter_password_dialog);
        super.onCreate(savedInstanceState);
        initView();

    }
    private  void initView(){
        mTitleTV = (TextView)findViewById(R.id.tv_interpwd_title);
        mInterET = (EditText)findViewById(R.id.et_inter_password);
        mOKBtn = (Button)findViewById(R.id.btn_confirm);
        mCancleBtn = (Button)findViewById(R.id.btn_dismiss);
        mOKBtn.setOnClickListener(this);
        mCancleBtn.setOnClickListener(this);
    }
    public void setTitle(String title){
        if(!TextUtils.isEmpty(title)){
            mTitleTV.setText(title);
        }
    }
public String getPassword(){
    return mInterET.getText().toString();
}
    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.btn_confirm:
            myCallBack.confirm();
            break;
        case R.id.btn_dismiss:
            myCallBack.cancel();
            break;

    }
    }
    public interface MyCallBack{
        void confirm();
        void cancel();
    }
}
