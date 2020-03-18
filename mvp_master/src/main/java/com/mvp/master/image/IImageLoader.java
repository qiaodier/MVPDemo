package com.mvp.master.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * @author iqiao
 * @date 2020-03-18 09:06
 * @desc
 */
public interface IImageLoader {
    /**
     * 显示图片
     *
     * @param context     上下文
     * @param url         图片url
     * @param imageView   图片控件
     * @param placeholder 占位图
     * @param errorImage  错误图片
     */
    void showImage(Context context, String url, ImageView imageView, int placeholder, int errorImage);

    /**
     * 显示图片
     *
     * @param context   上下文
     * @param url       图片url
     * @param imageView 图片控件
     */
    void showImage(Context context, String url, ImageView imageView);


}
