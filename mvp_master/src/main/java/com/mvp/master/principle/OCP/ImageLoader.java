package com.mvp.master.principle.OCP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author iqiao
 * 图片加载工具类遵循-->单一职责原则+开闭原则
 */
public class ImageLoader {
    /**
     * 图片缓存  默认初始化了
     */
    ImageCache mImageCache = new MemoryCache();
    /**
     * 线程池，线程数量为cpu数量
     */
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    /**
     * ui handler
     */
    Handler mUiHandler = new Handler(Looper.getMainLooper());

    /**
     * 默认 MemoryCache
     *
     * 动态设置图片的缓存实现类，也可自定义实现
     * @param mImageCache
     */
    public void setmImageCache(ImageCache mImageCache) {
        this.mImageCache = mImageCache;
    }

    private void updateImageView(final ImageView imageView, final Bitmap bmp) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bmp);
            }
        });
    }

    public void displayImage(final String url, final ImageView imageView) {
        Bitmap bitmap = mImageCache.get(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap1 = downloadImage(url);
                if (bitmap1 == null) {
                    return;
                }
                //更新ui
                if (imageView.getTag().equals(url)) {
                    updateImageView(imageView, bitmap1);
                }
                //加入缓存
                mImageCache.put(url, bitmap1);
            }
        });
    }

    /**
     * 下载图片
     *
     * @param imageUrl
     * @return
     */
    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
