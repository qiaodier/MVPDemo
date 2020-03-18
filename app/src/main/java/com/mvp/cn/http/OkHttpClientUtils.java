package com.mvp.cn.http;

import com.mvp.cn.BuildConfig;
import com.mvp.master.http.BaseOkHttp;

/**
 * @author iqiao
 * @date 2020-03-06 12:36
 * @desc 网络请求实现类。必须重写getUrl方法和 enableLog
 *
 */
public class OkHttpClientUtils extends BaseOkHttp {


    private static class SingletonHolder {
        private final static OkHttpClientUtils INSTANCES = new OkHttpClientUtils();
    }

    public static OkHttpClientUtils getInstance() {
        return SingletonHolder.INSTANCES;
    }

    @Override
    protected String getUrl() {
        return BuildConfig.BASE_URL;
    }

    @Override
    protected boolean enableLog() {
        return BuildConfig.LOG_DEBUG;
    }


}
