package com.mvp.master.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * @author iqiao
 * @date 2020-03-18 09:14
 * @desc 图片加载代理类
 */
public class ImageLoaderProxy implements IImageLoader {

    private IImageLoader imageLoader;

    private static class SingleClass {
        private static final ImageLoaderProxy INSTANCES = new ImageLoaderProxy();
    }

    public static ImageLoaderProxy getInstance() {
        return SingleClass.INSTANCES;
    }

    public void init(IImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }


    @Override
    public void showImage(Context context, String url, ImageView imageView, int placeholder, int errorImage) {
        imageLoader.showImage(context, url, imageView, placeholder, errorImage);
    }

    @Override
    public void showImage(Context context, String url, ImageView imageView) {
        imageLoader.showImage(context, url, imageView);
    }

}
