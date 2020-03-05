package com.mvp.cn.annotation;

import android.util.Log;

/**
 * Created by iqiao on 2020-01-09 11:04
 * Desc: 可以将所有的case语句定义成使用自定义注解的方法
 *
 * @author iqiao
 */
public class SwitchRunTaskManager {

    @SwitchType(1)
    public void eat(String content) {
        Log.e("test", "吃饭   " + content);
    }

    @SwitchType(2)
    public void sleep(String content) {
        Log.e("test", "睡觉   " + content);
    }
}
