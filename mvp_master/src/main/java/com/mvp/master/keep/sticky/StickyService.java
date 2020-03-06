package com.mvp.master.keep.sticky;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.tencent.mars.xlog.Log;

import androidx.annotation.Nullable;

/**
 * Created by iqiao on 2020-02-25 10:25
 * Desc:
 *
 * @author iqiao
 */
public class StickyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * START_STICKY：如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象，随后系统会尝试重新创建service。
     * <p>
     * START_NOT_STICKY：使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统将会把它置为started状态，系统不会自动重启该服务。
     * <p>
     * START_REDELIVER_INTENT：使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，它将会在隔一段时间后自动重启该服务，并将Intent的值传入。
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("StickyService", "已启动");
        return super.onStartCommand(intent, flags, startId);
    }

}
