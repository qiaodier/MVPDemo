package com.mvp.cn.net;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mvp.cn.comm.CommManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 公用的okhttp对象
 * 后期需要加网络请求缓存
 */

public class OkHttpClientUtils {

    protected IHttpRequestService getRequestClient() {
        OkHttpClient  okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    //添加拦截器
                    .addInterceptor(new CustomInterceptor())
                    .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CommManager.URL)//baseurl
                .client(okHttpClient) // 传入请求客户端
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换工厂
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加RxJava2调用适配工厂
                .build();
        return retrofit.create(IHttpRequestService.class);
    }










}
