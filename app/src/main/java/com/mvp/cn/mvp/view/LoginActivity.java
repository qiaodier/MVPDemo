package com.mvp.cn.mvp.view;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mvp.cn.BaseApplication;
import com.mvp.cn.R;
import com.mvp.cn.mvp.contract.LoginContract;
import com.mvp.cn.mvp.model.LoginModel;
import com.mvp.cn.mvp.presenter.LoginPresenter;
import com.mvp.cn.utils.Utils;
import com.mvp.master.image.ImageLoaderProxy;
import com.mvp.master.mvp.base.BaseActivity;
import com.mvp.router.api.Route;
import com.tencent.mars.xlog.Log;
import com.tencent.mmkv.MMKV;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.util.Optional;

/**
 * @author iqiao
 */
@Route("/login")
public class LoginActivity extends BaseActivity<LoginPresenter, LoginContract.View> implements LoginContract.View {

    private final String TAG = LoginActivity.this.getClass().getName();
    private EditText mUserName, mUserPwd;
    private Button mLoginBtn;
    private ImageView imageView;
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
        setTranslucentStatus();
        initData();
        testMMKV();
        testSP();
        Log.e("===========", getClipContent());
    }

    /**
     * 获取系统剪贴板内容
     */
    public static String getClipContent() {
        ClipboardManager manager = (ClipboardManager) BaseApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {
                CharSequence addedText = manager.getPrimaryClip().getItemAt(0).getText();
                String addedTextString = String.valueOf(addedText);
                if (!TextUtils.isEmpty(addedTextString)) {
                    return addedTextString;
                }
            }
        }
        return "";
    }

    private void testMMKV() {
        MMKV mmkv = MMKV.defaultMMKV();
        Log.e(TAG, "testMMKV");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            mmkv.encode("" + i, "" + i);
        }
        //耗时11ms
        Log.e(TAG, (System.currentTimeMillis() - start) + "ms");
    }

    private void testSP() {
        SharedPreferences sp = getSharedPreferences("test", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Log.e(TAG, "testSP");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            editor.putString("" + i, "" + i);
            //使用commit 耗时77ms
            editor.commit();
//            使用apply 耗时 187ms
//            editor.apply();
        }
        Log.e(TAG, (System.currentTimeMillis() - start) + "ms");
    }


    private void initData() {
        mUserName = findViewById(R.id.et_user_name);
        mUserPwd = findViewById(R.id.et_user_pwd);
        mLoginBtn = findViewById(R.id.btn_login_qq);
        imageView = findViewById(R.id.image);
        ImageLoaderProxy.getInstance().showImage(this, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584506380577&di=88f7e3ac60f687b33e1a1bcf81e96f8d&imgtype=0&src=http%3A%2F%2Ft8.baidu.com%2Fit%2Fu%3D2857883419%2C1187496708%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1280%26h%3D763", imageView);
        mLoginBtn.setOnClickListener((View v) -> {

            //跳转MainActivity
//            RouterManager.getInstance().navigation("/main");
            //测试路由页面跳转
//            RouterManager.getInstance().navigation("/main");
            //为按钮添加了点击事件，触发点击事件时，则会执行Emitter的onNext方法
            mPrensenter.login(mUserName.getText().toString(), mUserPwd.getText().toString());
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Optional
                    .ofNullable(mPrensenter)
                    .ifPresent(loginPresnter -> {
                        loginPresnter.detachView();
                    });
        } else {
            if (mPrensenter != null) {
                mPrensenter.detachView();
            }
        }
        super.onDestroy();
    }


    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter(new LoginModel());
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
        dismissLoadingDialog();
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
