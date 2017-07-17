package com.mvp.cn;

import android.app.Application;




/**
 * 镭卡app入口
 * baseappliction
 */

public class BaseApplication extends Application {

    public static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }
    public static BaseApplication getIns() {
        return instance;
    }


}



