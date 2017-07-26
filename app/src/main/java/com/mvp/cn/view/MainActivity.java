package com.mvp.cn.view;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mvp.cn.R;
import com.mvp.cn.interfacem.ILoginInterface;
import com.mvp.cn.presenter.LoginPresnter;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity implements ILoginInterface {

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
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //为按钮添加了点击事件，触发点击事件时，则会执行Emitter的onNext方法
                mLoginPresenter.login();
            }
        });
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

    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void showAlertMessage(String content) {

    }

    @Override
    public void requestSuccess() {
            //登录成功

    }

    @Override
    public void requestFail() {
            //登录失败

    }


}
