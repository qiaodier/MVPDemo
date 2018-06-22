package com.mvp.cn;

import android.app.Application;

import com.mvp.cn.utils.CrashHandler;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


/**
 * app入口
 * baseappliction
 */

public class BaseApplication extends Application {
    public final static String APPID_WX= "wxdda18e27ff003d9b";
    public final static String AppSecret_WX= "dbffdd9bb7940fec77168a74c892d500";
    public final static String APPID_QQ= "1104951123";
    public final static String AppKEY_QQ= "FwOJSUJTYSOzE8Uc";
    public final static String TOKEN_VALUES = "";
    public final static String PRO_BASE_URL = "";
    public final static String DEV_BASE_URL = "";
    public static String BASE_URL=DEV_BASE_URL;
    public static String BASE_TEST_URL="";

    {
        PlatformConfig.setWeixin(APPID_WX, AppSecret_WX);
        PlatformConfig.setQQZone(APPID_QQ, AppKEY_QQ);
    }
    public static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化崩溃异常日志
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        UMShareAPI.get(this);//初始化sdk
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;
    }
    public static BaseApplication getIns() {
        return instance;
    }


}



