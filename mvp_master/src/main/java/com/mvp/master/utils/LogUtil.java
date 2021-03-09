package com.mvp.master.utils;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hank
 */
public final class LogUtil {
    public static final int LEVEL_LOG_I = 0x01;
    public static final int LEVEL_LOG_D = 0x02;
    public static final int LEVEL_LOG_E = 0x03;
    public static final int LEVEL_LOG_V = 0x04;
    public static final int LEVEL_LOG_W = 0x05;

    private static final String DEFAULT_TAG = "LogUtil";
    private static boolean isLog = true;
    private static boolean isSaveToFile = true;
    private static ExecutorService sExecutor;
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final Format FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS ");
    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final Config CONFIG = new Config();

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
        if (!isLog || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.d(tag, msg);

        if (isSaveToFile) {
            file(msg);
        }
    }

    public static void d(String msg) {
        d(DEFAULT_TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.v(tag, msg);
        if (isSaveToFile) {
            file(msg);
        }

    }

    public static void v(String msg) {
        v(DEFAULT_TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.w(tag, msg);

        if (isSaveToFile) {
            file(msg);
        }
    }

    public static void w(String msg) {
        w(DEFAULT_TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.i(tag, msg);

        if (isSaveToFile) {
            file(msg);
        }
    }

    public static void i(String msg) {
        i(DEFAULT_TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.e(tag, msg);

        if (isSaveToFile) {
            file(msg);
        }
    }

    public static void e(String msg) {
        e(DEFAULT_TAG, msg);
    }

    /**
     * @param tag 标签
     * @param msg msg
     * @author hank
     * @date 2018/9/5 11:58
     * @descrption 这里使用自己分节的方式来输出足够长度的 message
     */
    public static void dTAGLongInfo(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) {
            return;
        }
        msg = msg.trim();
        int index = 0;
        int maxLength = 3500;
        String sub;
        while (index < msg.length()) {
            if (msg.length() <= index + maxLength) {
                sub = msg.substring(index);
            } else {
                sub = msg.substring(index, index + maxLength);
            }

            index += maxLength;
            d(tag, sub.trim());
        }
    }

    public static void dLongInfo(String msg) {
        dTAGLongInfo(DEFAULT_TAG, msg);
    }

    /**
     * @param msg 日志
     * @author hank
     * @date 2018/9/5 14:13
     * @descrption 日志写入文件
     */
    public static void file(String msg) {
        print2File(DEFAULT_TAG, msg);
    }

    private static void print2File(final String tag, final String msg) {
        Date now = new Date(System.currentTimeMillis());
        String format = FORMAT.format(now);
        String date = format.substring(0, 5);
        String time = format.substring(6);
        final String fullPath =
                (CONFIG.mDir == null ? CONFIG.mDefaultDir : CONFIG.mDir)
                        + CONFIG.mFilePrefix + "-" + date + ".txt";
        if (!createOrExistsFile(fullPath)) {
            e("create " + fullPath + " failed!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(time)
                .append("\t")
                .append(tag + "\t")
                .append(msg)
                .append(LINE_SEP);
        final String content = sb.toString();
        input2File(content, fullPath);
    }

    private static boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            boolean isCreate = file.createNewFile();
            if (isCreate) {
                printDeviceInfo(filePath);
            }
            return isCreate;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static void printDeviceInfo(final String filePath) {
//        String versionName = BuildConfig.VERSION_NAME;
//        long versionCode = BuildConfig.VERSION_CODE;
        String time = filePath.substring(filePath.length() - 9, filePath.length() - 4);
        final String head = "************* Log Head ****************" +
                "\nDate of Log        : " + time +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +
                "\nDevice Model       : " + Build.MODEL +
                "\nAndroid Version    : " + Build.VERSION.RELEASE +
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +
//                "\nApp VersionName    : " + versionName +
//                "\nApp VersionCode    : " + versionCode +
                "\n************* Log Head ****************\n\n";
        input2File(head, filePath);
    }

    private static void input2File(final String input, final String filePath) {
        if (sExecutor == null) {
            sExecutor = new ThreadPoolExecutor(1, 1,
                            0L, TimeUnit.MILLISECONDS,
                            new LinkedBlockingQueue<Runnable>());
        }
        Future<Boolean> submit = sExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                BufferedWriter bw = null;
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath),true);
                    bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream,"GBK"));
                    bw.write(input);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        try {
            if (submit.get()) {
                return;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        e("log to " + filePath + " failed!");
    }

    public static class Config {
        // The default storage directory of log.
        private String mDefaultDir;
        // The storage directory of log.
        private String mDir;
        // The file prefix of log.
        private String mFilePrefix = "log";

        private Config() {
            if (mDefaultDir != null) {
                return;
            }
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && Utils.getApp().getExternalCacheDir() != null) {
                mDefaultDir = Utils.getApp().getExternalCacheDir() + FILE_SEP + "log" + FILE_SEP;
            } else {
                mDefaultDir = Utils.getApp().getCacheDir() + FILE_SEP + "log" + FILE_SEP;
            }
        }
    }
}
