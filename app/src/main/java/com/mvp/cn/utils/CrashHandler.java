package com.mvp.cn.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;


import com.mvp.cn.comm.CommManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 捕获应用程序崩溃异常类
 * 将应用程序崩溃时的异常信息保存到自定义的文件中
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler======》";
  
    // 系统默认的UncaughtException处理类  
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例  
    private static CrashHandler INSTANCE = new CrashHandler();  
    // 程序的Context对象  
    private Context mContext;
    // 用来存储设备信息和异常信息  
    private Map<String, String> infos = new HashMap<String, String>();



    // 用于格式化日期,作为日志文件名的一部分  
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String PATH_LOGCAT;
    /** 保证只有一个CrashHandler实例 */  
    private CrashHandler() {  
    }  
  
    /** 获取CrashHandler实例 ,单例模式 */  
    public static CrashHandler getInstance() {  
        return INSTANCE;  
    }  
  
    /** 
     * 初始化 
     *  
     * @param context 
     */  
    public void init(Context context) {
        mContext = context;  
        // 获取系统默认的UncaughtException处理器  
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器  
        Thread.setDefaultUncaughtExceptionHandler(this);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            PATH_LOGCAT = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + CommManager.CACHE_EXCEPTION_LOG_NAME;
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            PATH_LOGCAT = context.getFilesDir().getAbsolutePath()
                    + File.separator + CommManager.CACHE_EXCEPTION_LOG_NAME;
        }
    }  
  
    /** 
     * 当UncaughtException发生时会转入该函数来处理 
     */  
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {  
            // 如果用户没有处理则让系统默认的异常处理器来处理  
            mDefaultHandler.uncaughtException(thread, ex);  
        } else {  

            // 退出程序
            System.exit(0);

        }  
    }  
  
    /** 
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 
     *  
     * @param ex 
     * @return true:如果处理了该异常信息;否则返回false. 
     */  
    private boolean handleException(Throwable ex) {
        if (ex == null) {  
            return false;  
        }
        // 收集设备参数信息
        collectDeviceInfo(mContext);  
        // 保存日志文件  
        String fileName = saveCrashInfo2File(ex);
        return true;  
    }  
  
    /** 
     * 收集设备参数信息 
     *  
     * @param ctx 
     */  
    public void collectDeviceInfo(Context ctx) {
        try {  
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {  
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;  
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);  
                infos.put("versionCode", versionCode);  
            }  
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }  
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {  
                field.setAccessible(true);  
                infos.put(field.getName(), field.get(null).toString());  
                Log.e(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }  
        }  
    }  

    public String getCurrentTime(){
        long timestamp = System.currentTimeMillis();
        String time = formatter.format(timestamp);
        return time;
    }
    /** 
     * 保存错误信息到文件中 
     *  
     * @param ex 
     * @return 返回文件名称,便于将文件传送到服务器 
     */  
    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        sb.append(getCurrentTime()+ "  -------------------------------------------------------\n");
        sb.append("手机型号："+ Build.MODEL+"\nException（" + ex.getClass().getName() + "）: "
                + (ex.getMessage() == null ? "" : ex.getMessage()) + "\n");
        StackTraceElement[] ste = ex.getStackTrace();
        for (int j = 0; j < ste.length; j++) {
            Log.e("errorLog=========》",ste[j].toString() + "\n");
            sb.append(ste[j].toString() + "\n");
        }
        FileWriter fw = null;
        BufferedWriter bw =null;
        try {  
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = time +".log";
                File dir = new File(PATH_LOGCAT);
                if (!dir.exists()) {  
                    dir.mkdirs();  
                }  
                File file = new File(PATH_LOGCAT+"/"+CommManager.CACHE_EXCEPTION_LOG_NAME+".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file,true);
            bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.newLine();
            bw.flush();
            bw.close();
            fw.close();
            return fileName;
        } catch (IOException e) {
            Log.e(TAG, "an error occured while writing file...", e);
            try {
                bw.close();
                fw.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
            }
        }  
        return null;  
    }  
  
}  