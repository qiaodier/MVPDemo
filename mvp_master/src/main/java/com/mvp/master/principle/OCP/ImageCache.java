package com.mvp.master.principle.OCP;

import android.graphics.Bitmap;

/**
 *
 * @author iqiao
 *
 * 遵循开闭原则的重要手段是抽象
 */
public interface ImageCache {
    /**
     * 加入缓存
     * @param url
     * @param bitmap
     */
    void put(String url, Bitmap bitmap);

    /**
     * 从缓存中获取
     * @param url
     * @return
     */
    Bitmap get(String url);
}
