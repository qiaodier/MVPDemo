package com.mvp.cn.http.glide;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author iqiao
 * @date 2020-03-06 09:08
 * @desc GlideBuilder 封装类
 * 加载动画和缩略图未实现
 */
public class GlideBuilder<T> {
    /**
     * 上下文
     */
    private Context context;
    /**
     * 图片url
     */
    private String url;
    /**
     * 图片控件
     */
    private ImageView imageView;
    /**
     * 占位图 图片加载出来前，显示的图片
     */
    private int placeholder;

    /**
     * 占位图  图片加载失败后，显示的图片
     */
    private int errorImage;


    /**
     * 显示图片的方法
     * 内部调用了glide
     */
    public void show() {
        if (this.context == null) {
            throw new NullPointerException("context is null");
        }
        if (this.imageView == null) {
            throw new NullPointerException("imageView is null");
        }
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("url is null");
        }
        DrawableCrossFadeFactory factory = new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        RequestOptions requestOptions = new RequestOptions().error(errorImage).placeholder(placeholder).priority(Priority.HIGH);
        Glide.with(this.context).applyDefaultRequestOptions(requestOptions).load(url).transition(withCrossFade(factory)).into(imageView);
    }


    public GlideBuilder(Builder builder) {
        this.context = builder.context;
        this.errorImage = builder.errorImage;
        this.imageView = builder.imageView;
        this.placeholder = builder.placeholder;
        this.url = builder.url;
    }

    /**
     * Builder类实现
     */
    public static final class Builder {
        Context context;
        String url;
        ImageView imageView;
        private int placeholder;
        private int errorImage;

        public Builder with(Context context1) {
            this.context = context1;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder into(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder placeholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder errorImage(int errorImage) {
            this.errorImage = errorImage;
            return this;
        }


        public GlideBuilder build() {
            return new GlideBuilder(this);
        }
    }
}
