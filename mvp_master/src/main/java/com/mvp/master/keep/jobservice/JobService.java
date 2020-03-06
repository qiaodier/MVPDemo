package com.mvp.master.keep.jobservice;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


import com.tencent.mars.xlog.Log;

import androidx.annotation.RequiresApi;

import static android.os.Build.VERSION_CODES.N;


/**
 * Created by iqiao on 2020-02-25 10:43
 * Desc:
 *
 * @author iqiao
 */
@SuppressLint("NewApi")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobService extends android.app.job.JobService {

    public static void startJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        //setPersisted 在设备重启后依然执行
        JobInfo.Builder builder = new JobInfo.Builder(10, new ComponentName(context.getPackageName(), JobService.class.getName())).setPersisted(true);
        if (Build.VERSION.SDK_INT < N) {
            //执行时间间隔5s   23版本最小周期为5s
            builder.setPeriodic(5000);
        } else {
            //延迟1s执行
            builder.setMinimumLatency(5000);
        }
        jobScheduler.schedule(builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        if (Build.VERSION.SDK_INT >= N) {
            //开始执行之后继续调用starJob
            startJob(this);
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e("JobService","onStopJob");
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("JobService","onDestroy");
    }
}

