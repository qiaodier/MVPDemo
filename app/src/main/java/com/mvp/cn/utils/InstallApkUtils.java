package com.mvp.cn.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;

import androidx.core.content.FileProvider;


/**
 * Created by iqiao on 2020-02-18 11:22
 * Desc: 安装工具类
 */
public class InstallApkUtils {

    public static void installAPK(Context context, File file) {
        if (!file.exists()) {
            LogUtil.e("安装", "安装文件未找到");
            return;
        }
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(install);

    }
}
