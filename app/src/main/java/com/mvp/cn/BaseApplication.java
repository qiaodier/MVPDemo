package com.mvp.cn;

import android.app.Application;

import com.mvp.cn.utils.CustomLogCatStrategy;
import com.mvp.master.router.RouterManager;
import com.mvp.master.ui.UIUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.mars.xlog.Log;


/**
 * @author iqiao
 * app入口类
 */

public class BaseApplication extends Application {


    private static BaseApplication baseApplication;


    public static BaseApplication getInstance() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        //注册路由框架,所有Activity都必须使用@Route注解
        RouterManager.getInstance().init(this);
        //日志打印框架
        initLogger();
        //屏幕适配工具类
        UIUtils.getInstance().init(this, 1080, 1920);
        //当前测试代码的设备是夜神模拟器Android7.1.2、Android5.1,三星 SM-G8750 Android8.0，
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
//            //启动保活进程（前台进程保活法） 1
//            startService(new Intent(this, ForegroundService.class));
//        } else {
//            //  拉活 2
//            startService(new Intent(this, StickyService.class));
//        }
        //三星 SM-G8750 Android8.0 手动结束掉应用，jobservice也结束了
//        JobService.startJob(this);

    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                // (Optional) Whether to show thread info or not. Default true
                .showThreadInfo(true)
                // (Optional) How many method line to show. Default 2
                .methodCount(2)
                // (Optional) Hides internal method calls up to offset. Default 5
                .methodOffset(5)
                // (Optional) Changes the log strategy to print out. Default LogCat
                .logStrategy(new CustomLogCatStrategy())
                // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .tag("-request-log")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.appenderClose();
    }


}



