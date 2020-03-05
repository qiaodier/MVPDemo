package com.mvp.cn.utils;

import com.orhanobut.logger.LogStrategy;

import androidx.annotation.Nullable;
import io.reactivex.annotations.NonNull;

/**
 * @author iqiao
 * desc: 处理android studio 3.1 Logcat 日志合并TAG问题
 */
public class CustomLogCatStrategy implements LogStrategy {

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {
//        Log.println(priority, randomKey() + tag, message);
        com.tencent.mars.xlog.Log.e(randomKey() + tag, message);
    }

    private int last;

    private String randomKey() {
        int random = (int) (10 * Math.random());
        if (random == last) {
            random = (random + 1) % 10;
        }
        last = random;
        return String.valueOf(random);
    }

}
