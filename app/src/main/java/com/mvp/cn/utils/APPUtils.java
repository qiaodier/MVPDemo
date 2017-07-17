package com.mvp.cn.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;



/**
 * Created by qiaohao on 2016/9/13.
 * 说明：获取手机信息的管理类
 */
public class APPUtils {

    private Context mContext;
    private TelephonyManager tm;

    public static APPUtils getInstance(Context context) {
        return new APPUtils(context);
    }

    public APPUtils(Context context) {
        mContext = context;
        tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 获取手机IMEI
     *
     * @return
     */
    public String getIMEI() {
        return !tm.getDeviceId().equals("") ? tm.getDeviceId() : "";
    }

    /**
     * 2  * 获取版本号
     * 3  * @return 当前应用的版本号
     * 4
     */
    public String getAPPVersion() {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取手机系统版本号
     *
     * @return
     */
    public String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public String getPhoneModel() {
        return android.os.Build.MODEL;
    }


}
