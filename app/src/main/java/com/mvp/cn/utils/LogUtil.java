package com.mvp.cn.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by iqiao on 2020-02-17 13:10
 * Desc:
 */
public class LogUtil {
    public static final int LEVEL_LOG_I = 1;
    public static final int LEVEL_LOG_D = 2;
    public static final int LEVEL_LOG_E = 3;
    public static final int LEVEL_LOG_V = 4;
    public static final int LEVEL_LOG_W = 5;
    private static final String DEFAULT_TAG = "DEMO";
    private static boolean isLog = true;
    private static boolean isSaveToFile = true;
    private static ExecutorService sExecutor;
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final Format FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS ");
    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final LogUtil.Config CONFIG = new LogUtil.Config();

    private LogUtil() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static boolean isLog() {
        return isLog;
    }

    public static void setLog(boolean isLog) {
        LogUtil.isLog = isLog;
    }

    public static boolean isIsSaveToFile() {
        return isSaveToFile;
    }

    public static void setIsSaveToFile(boolean isSaveToFile) {
        LogUtil.isSaveToFile = isSaveToFile;
    }

    public static void d(String tag, String msg) {
        if (isLog && !TextUtils.isEmpty(msg)) {
            Log.d(tag, msg);
            if (isSaveToFile) {
                file(msg);
            }

        }
    }

    public static void d(String msg) {
        d("DEMO", msg);
    }

    public static void v(String tag, String msg) {
        if (isLog && !TextUtils.isEmpty(msg)) {
            Log.v(tag, msg);
            if (isSaveToFile) {
                file(msg);
            }

        }
    }

    public static void v(String msg) {
        v("DEMO", msg);
    }

    public static void w(String tag, String msg) {
        if (isLog && !TextUtils.isEmpty(msg)) {
            Log.w(tag, msg);
            if (isSaveToFile) {
                file(msg);
            }

        }
    }

    public static void w(String msg) {
        w("DEMO", msg);
    }

    public static void i(String tag, String msg) {
        if (isLog && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
            if (isSaveToFile) {
                file(msg);
            }

        }
    }

    public static void i(String msg) {
        i("DEMO", msg);
    }

    public static void e(String tag, String msg) {
        if (isLog && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
            if (isSaveToFile) {
                file(msg);
            }

        }
    }

    public static void e(String msg) {
        e("DEMO", msg);
    }

    public static void dTAGLongInfo(String tag, String msg) {
        if (isLog && !TextUtils.isEmpty(msg)) {
            msg = msg.trim();
            int index = 0;
            short maxLength = 3500;

            while(index < msg.length()) {
                String sub;
                if (msg.length() <= index + maxLength) {
                    sub = msg.substring(index);
                } else {
                    sub = msg.substring(index, index + maxLength);
                }

                index += maxLength;
                d(tag, sub.trim());
            }

        }
    }

    public static void dLongInfo(String msg) {
        dTAGLongInfo("DEMO", msg);
    }

    public static void file(String msg) {
        print2File("DEMO", msg);
    }

    private static void print2File(String tag, String msg) {
        Date now = new Date(System.currentTimeMillis());
        String format = FORMAT.format(now);
        String date = format.substring(0, 5);
        String time = format.substring(6);
        String fullPath = (CONFIG.mDir == null ? CONFIG.mDefaultDir : CONFIG.mDir) + CONFIG.mFilePrefix + "-" + date + ".txt";
        Log.e("日志存放路径",fullPath+"");
        if (!createOrExistsFile(fullPath)) {
            e("create " + fullPath + " failed!");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t").append(tag + "\t").append(msg).append(LINE_SEP);
            String content = sb.toString();
            input2File(content, fullPath);
        }
    }

    private static boolean createOrExistsFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.isFile();
        } else if (!createOrExistsDir(file.getParentFile())) {
            return false;
        } else {
            try {
                boolean isCreate = file.createNewFile();
                if (isCreate) {
                    printDeviceInfo(filePath);
                }

                return isCreate;
            } catch (IOException var3) {
                var3.printStackTrace();
                return false;
            }
        }
    }

    private static boolean createOrExistsDir(File file) {
        boolean var10000;
        label25: {
            if (file != null) {
                if (file.exists()) {
                    if (file.isDirectory()) {
                        break label25;
                    }
                } else if (file.mkdirs()) {
                    break label25;
                }
            }

            var10000 = false;
            return var10000;
        }

        var10000 = true;
        return var10000;
    }


    private static void printDeviceInfo(String filePath) {
        String versionName = "";
        int versionCode = 0;

        try {
            PackageInfo pi = Utils.getApp().getPackageManager().getPackageInfo(Utils.getApp().getPackageName(), 0);
            if (pi != null) {
                versionName = pi.versionName;
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException var5) {
            var5.printStackTrace();
        }

        String time = filePath.substring(filePath.length() - 9, filePath.length() - 4);
        String head = "************* Log Head ****************\nDate of Log        : " + time + "\nDevice Manufacturer: " + Build.MANUFACTURER + "\nDevice Model       : " + Build.MODEL + "\nAndroid Version    : " + Build.VERSION.RELEASE + "\nAndroid SDK        : " + Build.VERSION.SDK_INT + "\nApp VersionName    : " + versionName + "\nApp VersionCode    : " + versionCode + "\n************* Log Head ****************\n\n";
        input2File(head, filePath);
    }

    private static void input2File(final String input, final String filePath) {
        if (sExecutor == null) {
            sExecutor = Executors.newSingleThreadExecutor();
        }

        Future submit = sExecutor.submit(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                BufferedWriter bw = null;

                Boolean var3;
                try {
                    bw = new BufferedWriter(new FileWriter(filePath, true));
                    bw.write(input);
                    Boolean var2 = true;
                    return var2;
                } catch (IOException var13) {
                    var13.printStackTrace();
                    var3 = false;
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch (IOException var12) {
                        var12.printStackTrace();
                    }

                }

                return var3;
            }
        });

        try {
            if ((Boolean)submit.get()) {
                return;
            }
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        } catch (ExecutionException var5) {
            var5.printStackTrace();
        }

        e("log to " + filePath + " failed!");
    }

    public static class Config {
        private String mDefaultDir;
        private String mDir;
        private String mFilePrefix;

        private Config() {
            this.mFilePrefix = "log";
            if (this.mDefaultDir == null) {
                if ("mounted".equals(Environment.getExternalStorageState()) && Utils.getApp().getExternalCacheDir() != null) {
                    this.mDefaultDir = Utils.getApp().getExternalCacheDir() + LogUtil.FILE_SEP + "log" + LogUtil.FILE_SEP;
                } else {
                    this.mDefaultDir = Utils.getApp().getCacheDir() + LogUtil.FILE_SEP + "log" + LogUtil.FILE_SEP;
                }

            }
        }
    }
}
