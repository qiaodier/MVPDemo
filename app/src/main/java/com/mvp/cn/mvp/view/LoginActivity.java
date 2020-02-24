package com.mvp.cn.mvp.view;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mvp.cn.R;
import com.mvp.cn.mvp.base.BaseActivity;
import com.mvp.cn.mvp.contract.LoginContract;
import com.mvp.cn.mvp.model.LoginModel;
import com.mvp.cn.mvp.presenter.LoginPresnter;
import com.mvp.cn.router.RouterManager;
import com.mvp.cn.utils.Utils;
import com.mvp.compile.Route;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.util.Optional;

@Route("/login")
public class LoginActivity extends BaseActivity<LoginPresnter, LoginContract.View> implements LoginContract.View {

    private static final int REQUEST_CODE_WRITE_SETTINGS = 10000;
    private static final String TAG = "umeng登录测试";
    private EditText mUserName, mUserPwd;
    private Button mLoginBtn;
    private int count;
    private Intent installService;
    /**
     * upadateApkPath 更新apk存放路径  /data/cache/update.apk
     */
    private String upadateApkPath = Utils.getApp().getExternalCacheDir() + "/update.apk";

    @Override
    protected int layoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
//        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
//        intent.setData(Uri.parse("package:" + getPackageName()));
//        startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS );
        initData();

    }


    private void initData() {
        mUserName = findViewById(R.id.et_user_name);
//        mUserName.setText(JNIUtil.getAPPInfo(this));
        mUserPwd = findViewById(R.id.et_user_pwd);
        mLoginBtn = findViewById(R.id.btn_login_qq);
        mLoginBtn.setOnClickListener((View v) -> {
            //测试路由页面跳转
            RouterManager.getInstance().navigation("/main");
            //为按钮添加了点击事件，触发点击事件时，则会执行Emitter的onNext方法
//            mPrensenter.login(mUserName.getText().toString(), mUserPwd.getText().toString());
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
//        stopService(installService);
        Optional
                .ofNullable(mPrensenter)
                .ifPresent(loginPresnter -> {
                    loginPresnter.detachView();
                });
        super.onDestroy();
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
        showMessage(msg);
    }

    /**
     * 请求完成
     */
    @Override
    public void requestComplete() {

    }


}
