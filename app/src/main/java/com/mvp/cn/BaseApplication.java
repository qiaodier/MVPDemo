package com.mvp.cn;

import android.app.Application;

import com.mvp.cn.utils.CrashHandler;


/**
 * app入口
 * baseappliction
 */

public class BaseApplication extends Application {

    public static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化崩溃异常日志
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

    }
    public static BaseApplication getIns() {
        return instance;
    }


}



