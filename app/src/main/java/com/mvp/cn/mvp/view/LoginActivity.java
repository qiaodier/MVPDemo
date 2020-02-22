package com.mvp.cn.ui;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mvp.cn.R;
import com.mvp.cn.mvp.contract.LoginContract;
import com.mvp.cn.mvp.model.LoginModel;
import com.mvp.cn.mvp.presenter.LoginPresnter;
import com.mvp.cn.utils.Utils;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

import java.util.Map;
import java.util.Optional;

public class LoginActivity extends BaseActivity<LoginPresnter,LoginContract.View> implements LoginContract.View {

    private static final int REQUEST_CODE_WRITE_SETTINGS = 10000;
    private static final String TAG = "umeng登录测试";
    private EditText mUserName, mUserPwd;
    private Button mLoginBtn;
    private LoginPresnter mLoginPresenter;
    private int count;
    private Intent installService;
    /**
     * upadateApkPath 更新apk存放路径  /data/cache/update.apk
     */
    private String upadateApkPath = Utils.getApp().getExternalCacheDir() + "/update.apk";

    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
//        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
//        intent.setData(Uri.parse("package:" + getPackageName()));
//        startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS );


    }


    private void initData() {
        mUserName = (EditText) findViewById(R.id.et_user_name);
//        mUserName.setText(JNIUtil.getAPPInfo(this));
        mUserPwd = (EditText) findViewById(R.id.et_user_pwd);
        mLoginBtn = (Button) findViewById(R.id.btn_login_qq);
        Button weixinBtn = (Button) findViewById(R.id.btn_login_weixin);
        weixinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authorization(SHARE_MEDIA.WEIXIN);
            }
        });
        mLoginBtn.setOnClickListener((View v) -> {
            //为按钮添加了点击事件，触发点击事件时，则会执行Emitter的onNext方法
            mLoginPresenter.login(mUserName.getText().toString(), mUserPwd.getText().toString());
//                authorization(SHARE_MEDIA.QQ);
            //调用自动安装逻辑之前，需要引导用户开启智能安装服务，否则无法实现自动安装
            //Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            // startActivity(intent);
            //安装apk调用示例代码
//            InstallApkUtils.installAPK(this, new File(upadateApkPath));
            //启动模拟点击安装服务
//            installService = new Intent(this, InstallService.class);
//            startService(installService);
        });
    }

    @Override
    protected void onDestroy() {
        stopService(installService);
        Optional
                .ofNullable(mLoginPresenter)
                .ifPresent(loginPresnter -> {
                    loginPresnter.detachView();
                });
        super.onDestroy();
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
        if (requestCode == REQUEST_CODE_WRITE_SETTINGS) {
            if (Settings.System.canWrite(this)) {
                if (Build.VERSION.SDK_INT >= 23) {
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(this, mPermissionList, 123);
                    count = 0;
                } else {
                    initData();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == 123) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                } else {
                    count++;
                }
            }
            if (count == permissions.length) {
                initData();
            } else {
                showMessage("权限未获取");
                finish();
            }
        }
    }


    @Override
    protected LoginPresnter initPresenter() {
       return new LoginPresnter(new LoginModel());
    }


    /**
     * 请求成功
     */
    @Override
    public void requestSuccess() {

    }

    @Override
    public RxAppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dismissLoaddingDialog();
    }

    /**
     * 请求失败
     *
     * @param msg
     */
    @Override
    public void requestFail(String msg) {

    }

    /**
     * 请求完成
     */
    @Override
    public void requestComplete() {

    }


}
