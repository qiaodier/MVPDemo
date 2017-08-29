package com.mvp.cn.view;


import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mvp.cn.R;
import com.mvp.cn.interfacem.ILoginInterface;
import com.mvp.cn.presenter.LoginPresnter;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

import java.util.Map;

public class MainActivity extends BaseActivity implements ILoginInterface {

    private static final String TAG = "umeng登录测试";
    private EditText mUserName, mUserPwd;
    private Button mLoginBtn;
    private LoginPresnter mLoginPresenter;

    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
        mUserName = (EditText) findViewById(R.id.et_user_name);
        mUserPwd = (EditText) findViewById(R.id.et_user_pwd);
        mLoginBtn = (Button) findViewById(R.id.btn_login_qq);
        Button weixinBtn = (Button) findViewById(R.id.btn_login_weixin);
        weixinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authorization(SHARE_MEDIA.WEIXIN);
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //为按钮添加了点击事件，触发点击事件时，则会执行Emitter的onNext方法
//                mLoginPresenter.login();
                authorization(SHARE_MEDIA.QQ);
            }
        });
    }

    //授权
    private void authorization(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d(TAG, "onComplete " + "授权完成");

                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");
                Toast.makeText(getApplicationContext(), "name=" + name + ",gender=" + gender, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "name=" + name + ",gender=" + gender);
                //拿到信息去请求登录接口。。。
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {


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
