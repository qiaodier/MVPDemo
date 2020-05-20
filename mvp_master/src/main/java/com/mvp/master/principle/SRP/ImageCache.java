package com.mvp.master.principle.SRP;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * @author iqiao
 */
public class ImageCache {
    /**
     * 图片lru缓存
     */
    LruCache<String, Bitmap> mImageCache;

    public ImageCache() {
        initImageCache();
    }

    /**
     * 初始化
     */
    private void initImageCache() {
        //计算可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //取四分之一的可用内存作为缓存
        final int cacheSize = maxMemory / 4;
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    /**
     * 加入缓存
     *
     * @param url
     * @param bitmap
     */
    public void put(String url, Bitmap bitmap) {
        mImageCache.put(url, bitmap);
    }

    /**
     * 从缓存中获取图片 通过图片地址
     *
     * @param url
     * @return
     */
    public Bitmap get(String url) {
        return mImageCache.get(url);
    }
}
