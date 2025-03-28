package com.mvp.master.keep.foreground;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


import androidx.annotation.Nullable;

/**
 * Created by iqiao on 2020-02-25 08:57
 * Desc: 使用前台进程保活  提高应用优先级 只适用于7.0以及以下
 *
 * @author iqiao
 */
public class ForegroundService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //单独使用此通知，手机通知栏会有一个
        startForeground(1, new Notification());
        Log.e("ForegroundService", "发送id:" + 1 + "的通知");
        //通过系统bug，发送两个相同id的推送，随机停掉一个
        startService(new Intent(this, InnerService.class));
    }


    public static class InnerService extends Service {

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            startForeground(1, new Notification());
            stopSelf();
            Log.e("ForegroundService.InnerService", "发送id:" + 1 + "的通知");
        }

    }


}
