package com.mvp.cn.http.glide;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.mvp.cn.http.OkHttpClientUtils;

import java.io.InputStream;

import androidx.annotation.NonNull;

/**
 *
 * @author iqiao
 * @desc glide 使用okhttp请求配置类
 */
@GlideModule
public final class OkhttpGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.replace(GlideUrl.class,InputStream.class,new OkHttpUrlLoader.Factory(OkHttpClientUtils.getInstance().getOk()));
    }

}
