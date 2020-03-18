package com.mvp.master.image.glide;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.mvp.master.R;
import com.mvp.master.image.IImageLoader;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author iqiao
 * @date 2020-03-18 09:12
 * @desc 代理模式glide实现类
 */
public class GlideImageLoaderImpl implements IImageLoader {

    @Override
    public void showImage(Context context, String url, ImageView imageView, int placeholder, int errorImage) {
        if (context == null) {
            throw new NullPointerException("context is null");
        }
        if (imageView == null) {
            throw new NullPointerException("imageView is null");
        }
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("url is null");
        }
        DrawableCrossFadeFactory factory = new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        RequestOptions requestOptions = new RequestOptions().error(errorImage).placeholder(placeholder).priority(Priority.HIGH);
        Glide.with(context).applyDefaultRequestOptions(requestOptions).load(url).transition(withCrossFade(factory)).into(imageView);

    }

    @Override
    public void showImage(Context context, String url, ImageView imageView) {
        showImage(context, url, imageView, R.drawable.loading_img, R.drawable.error_img);
    }
}
