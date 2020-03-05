package com.mvp.cn.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by iqiao on 2020-02-17 13:16
 * Desc:
 * @author iqiao
 */
public class Utils {
    @SuppressLint({"StaticFieldLeak"})
    private static Application sApplication;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(Context context) {
        init((Application) context.getApplicationContext());
    }

    public static void init(Application app) {
        if (sApplication == null) {
            sApplication = app;
        }
    }

    public static Application getApp() {
        if (sApplication != null) {
            return sApplication;
        } else {
            try {
                Class<?> activityThread = Class.forName("android.app.ActivityThread");
                Object at = activityThread.getMethod("currentActivityThread").invoke((Object) null);
                Object app = activityThread.getMethod("getApplication").invoke(at);
                if (app == null) {
                    throw new NullPointerException("u should init first");
                }

                init((Application) app);
                return sApplication;
            } catch (NoSuchMethodException var3) {
                var3.printStackTrace();
            } catch (IllegalAccessException var4) {
                var4.printStackTrace();
            } catch (InvocationTargetException var5) {
                var5.printStackTrace();
            } catch (ClassNotFoundException var6) {
                var6.printStackTrace();
            }

            throw new NullPointerException("u should init first");
        }
    }



}
