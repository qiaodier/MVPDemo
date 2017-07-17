package com.mvp.cn.view;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mvp.cn.R;
import com.mvp.cn.interfacem.ILoginInterface;
import com.mvp.cn.presenter.LoginPresnter;

public class MainActivity extends BaseActivity implements ILoginInterface,View.OnClickListener {

    private EditText mUserName,mUserPwd;
    private Button mLoginBtn;
    private LoginPresnter mLoginPresenter;
    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mUserName = (EditText) findViewById(R.id.et_user_name);
        mUserPwd = (EditText) findViewById(R.id.et_user_pwd);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mLoginBtn.setOnClickListener(this);
    }

    @Override
    protected void initPresenter() {
        mLoginPresenter = new LoginPresnter(this);
    }


    @Override
    public String getUserName() {
        return mUserName.getText().toString();
    }

    @Override
    public String getUserPwd() {
        return mUserPwd.getText().toString();
    }

    @Override
    public void showLoadingDialog() {
        showLoadingDialog();
    }

    @Override
    public void dismissLoadingDialog() {
        dismissLoaddingDialog();
    }

    @Override
    public void showAlertMessage(String content) {
        showMessage(content);
    }

    @Override
    public void requestSuccess() {
            //登录成功

    }

    @Override
    public void requestFail() {
            //登录失败

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                mLoginPresenter.login();
                break;
        }

    }
}
