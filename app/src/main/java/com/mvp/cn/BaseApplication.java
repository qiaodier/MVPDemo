package com.mvp.cn;

import android.app.Application;

import com.mvp.cn.router.RouterManager;


/**
 * app入口
 * baseappliction
 */

public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //注册路由框架
        RouterManager.getInstance().init(this);
    }


}



