package com.mvp.cn.mvp.view;

import android.Manifest;
import android.os.Environment;

import com.mvp.cn.BuildConfig;
import com.mvp.cn.R;
import com.mvp.cn.mvp.base.BaseActivity;
import com.mvp.cn.mvp.base.BasePresenter;
import com.mvp.cn.router.RouterManager;
import com.mvp.compile.Route;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVHandler;
import com.tencent.mmkv.MMKVLogLevel;
import com.tencent.mmkv.MMKVRecoverStrategic;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by iqiao on 2020-02-24 14:17
 * Desc: loading页面
 *
 * @author iqiao
 */
@Route("/main")
public class LoadingActivity extends BaseActivity {

    private RxPermissions rxPermissions;

    static {
        System.loadLibrary("c++_shared");
        System.loadLibrary("marsxlog");
    }

    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .flatMap((Boolean aBoolean) -> {
                    if (aBoolean) {
                        //初始化xlog
                        initXLog();
                        //初始化mmkv
                        initMMKV();
                        return Observable
                                .interval(1, TimeUnit.SECONDS)
                                .take(3)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                    return null;
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        RouterManager.getInstance().navigation("/login");
                        finish();
                    }
                });


    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    private void initMMKV() {
        //注册mmkv
        MMKV.initialize(this);
        MMKV.setLogLevel(MMKVLogLevel.LevelDebug);
        MMKV.registerHandler(new MMKVHandler() {

            @Override
            public MMKVRecoverStrategic onMMKVCRCCheckFail(String s) {
                Log.e("BaseApplication", "MMKVHandler onMMKVCRCCheckFail");
                return null;
            }

            @Override
            public MMKVRecoverStrategic onMMKVFileLengthError(String s) {
                Log.e("BaseApplication", "MMKVHandler onMMKVFileLengthError");
                return null;
            }

            @Override
            public boolean wantLogRedirecting() {
                return true;
            }

            @Override
            public void mmkvLog(MMKVLogLevel mmkvLogLevel, String file, int line, String func, String message) {
                String log = "<" + file + ":" + line + "::" + func + "> " + message;
                switch (mmkvLogLevel) {
                    case LevelDebug:
                        Log.d("mmkvLog", log);
                        break;
                    case LevelInfo:
                        Log.i("mmkvLog", log);
                        break;
                    case LevelWarning:
                        Log.w("mmkvLog", log);
                        break;
                    case LevelError:
                        Log.e("mmkvLog", log);
                        break;
                    case LevelNone:
                        Log.e("mmkvLog", log);
                        break;
                    default:
                        break;
                }
            }
        });

    }


    private void initXLog() {
        final String SDCARD = Environment.getExternalStorageDirectory().getPath();
        final String logPath = SDCARD + "/mvpDemo_xlog/log";
        // this is necessary, or may crash for SIGBUS
        final String cachePath = this.getFilesDir() + "/xlog";
        //init xlog
        if (BuildConfig.DEBUG) {
            Xlog.appenderOpen(Xlog.LEVEL_VERBOSE, Xlog.AppednerModeAsync, cachePath, logPath, "mvpDemo", 0, "");
            Xlog.setConsoleLogOpen(true);
        } else {
            Xlog.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, cachePath, logPath, "mvpDemo", 0, "");
            Xlog.setConsoleLogOpen(false);
        }
        Log.setLogImp(new Xlog());
    }
}
