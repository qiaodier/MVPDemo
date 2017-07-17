package com.mvp.cn.net;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


import com.mvp.cn.comm.CommManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 公用的okhttp对象
 * 后期需要加网络请求缓存
 */

public class OkHttpClientUtils {
    private static OkHttpClient mOkHttpClient;
    public static OkHttpClientUtils mOkClient;
    private final int SUCCESS_CODE = 001;
    private final int FAILURE_CODE = 002;
    public int mRequestCode;
    private IRequestCallback mHttpCallback;

    public static OkHttpClientUtils getOkClient() {
        if (mOkClient == null) {
            mOkClient = new OkHttpClientUtils();
            getOkHttpClient();
        }
        return mOkClient;
    }

    public static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
//            int cacheSize = 10 * 1024 * 1024; // 10 MiB
//            File cacheDirectory = new File(SDCardUtils.getSDPath() + "okhttpCache/");
//            Cache cache = new Cache(cacheDirectory, cacheSize);
            mOkHttpClient = mOkHttpClient.newBuilder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
//                    .cache(cache)
                    //添加拦截器
                    .addInterceptor(new CustomInterceptor())
                    .build();
        }
        return mOkHttpClient;
    }


    /**
     * 发送到主线程
     */
    private Handler mResponseHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS_CODE:
                    //成功
                    mHttpCallback.onSuccess(msg.arg1, (String) msg.obj);
                    break;
                case FAILURE_CODE:
                    //失败
                    mHttpCallback.onFailure(msg.arg1, (String) msg.obj);
                    break;
            }
        }
    };

    /**
     * post请求
     *
     * @param request
     * @param callback
     * @return
     */
    public synchronized void httpPostString(int requestCode, String request, IRequestCallback callback) {
        mHttpCallback = callback;
        RequestBody requestBody = RequestBody.create(null, request);
        Request reuqestEntity = new Request.Builder()
                .url(CommManager.URL)
                .post(requestBody)
                //此tag 属性是用来区分不同接口的请求
                .tag(requestCode)
                .build();
        Log.e("okhttpUtils=======》", request);

            mOkHttpClient.newCall(reuqestEntity).enqueue(new Callback() {
                                                             @Override
                                                             public void onFailure(Call call, IOException e) {
                                                                 if (e != null && e.getMessage() != null) {
                                                                     Log.e("okhttpUtils=======》", "RequestFailure:" + e.getMessage() + "");
                                                                     Message msg = new Message();
                                                                     msg.what = FAILURE_CODE;
                                                                     msg.obj = e.getMessage();
                                                                     msg.arg1 = (int) call.request().tag();
                                                                     mResponseHandler.sendMessage(msg);
                                                                 } else {
                                                                     Message msg = new Message();
                                                                     msg.what = FAILURE_CODE;
                                                                     msg.obj = "服务器响应错误";
                                                                     msg.arg1 = (int) call.request().tag();
                                                                     mResponseHandler.sendMessage(msg);
                                                                 }
                                                             }

                                                             @Override
                                                             public void onResponse(Call call, Response response) throws IOException {
                                                                 if (response.isSuccessful()) {
                                                                     try {
                                                                         String resp = response.body().string();
                                                                         Log.e("okhttpUtils=======》", "ResponseSuccess:" + resp);
                                                                         Message msg = new Message();
                                                                         msg.what = SUCCESS_CODE;
                                                                         msg.arg1 = (int) call.request().tag();
                                                                         msg.obj = resp;
                                                                         mResponseHandler.sendMessage(msg);
                                                                     } catch (IllegalStateException e) {
                                                                         e.printStackTrace();
                                                                         Log.e("okhttpUtils",e.getLocalizedMessage());
                                                                     }
                                                                 } else {
                                                                     //响应失败
                                                                     Log.e("okhttpUtils=======》", "ResponseSuccess:" + response.message() + "");
                                                                     Message msg = new Message();
                                                                     msg.what = FAILURE_CODE;
                                                                     msg.arg1 = (int) call.request().tag();
                                                                     msg.obj = response.message();
                                                                     mResponseHandler.sendMessage(msg);
                                                                 }

                                                             }

                                                         }

            );
    }


    /**
     * post请求
     *
     * @param request
     * @param callback
     * @param url
     * @return
     */
    public synchronized void httpPostString(int requestCode, String url,String request, IRequestCallback callback) {
        mHttpCallback = callback;
        RequestBody requestBody = RequestBody.create(null, request);
        Request reuqestEntity = new Request.Builder()
                .url(url)
                .post(requestBody)
                //此tag 属性是用来区分不同接口的请求
                .tag(requestCode)
                .build();
        Log.e("okhttpUtils=======》", request);

        mOkHttpClient.newCall(reuqestEntity).enqueue(new Callback() {
                                                         @Override
                                                         public void onFailure(Call call, IOException e) {
                                                             if (e != null && e.getMessage() != null) {
                                                                 Log.e("okhttpUtils=======》", "RequestFailure:" + e.getMessage() + "");
                                                                 Message msg = new Message();
                                                                 msg.what = FAILURE_CODE;
                                                                 msg.obj = e.getMessage();
                                                                 msg.arg1 = (int) call.request().tag();
                                                                 mResponseHandler.sendMessage(msg);
                                                             } else {
                                                                 Message msg = new Message();
                                                                 msg.what = FAILURE_CODE;
                                                                 msg.obj = "服务器响应错误";
                                                                 msg.arg1 = (int) call.request().tag();
                                                                 mResponseHandler.sendMessage(msg);
                                                             }
                                                         }

                                                         @Override
                                                         public void onResponse(Call call, Response response) throws IOException {
                                                             if (response.isSuccessful()) {
                                                                 try {
                                                                     String resp = response.body().string();
                                                                     Log.e("okhttpUtils=======》", "ResponseSuccess:" + resp);
                                                                     Message msg = new Message();
                                                                     msg.what = SUCCESS_CODE;
                                                                     msg.arg1 = (int) call.request().tag();
                                                                     msg.obj = resp;
                                                                     mResponseHandler.sendMessage(msg);
                                                                 } catch (IllegalStateException e) {
                                                                     e.printStackTrace();
                                                                     Log.e("okhttpUtils",e.getLocalizedMessage());
                                                                 }
                                                             } else {
                                                                 //响应失败
                                                                 Log.e("okhttpUtils=======》", "ResponseSuccess:" + response.message() + "");
                                                                 Message msg = new Message();
                                                                 msg.what = FAILURE_CODE;
                                                                 msg.arg1 = (int) call.request().tag();
                                                                 msg.obj = response.message();
                                                                 mResponseHandler.sendMessage(msg);
                                                             }

                                                         }

                                                     }

        );
    }

    /**
     * get 请求
     *
     * @param requestCode
     * @param callback
     */
    public void httpGetString(int requestCode, IRequestCallback callback) {
        mRequestCode = requestCode;
        mHttpCallback = callback;
        Request reuqestEntity = new Request.Builder()
                .url(CommManager.URL)
                .get()
                .build();
        mOkHttpClient.newCall(reuqestEntity).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("RequestFailure", e.getLocalizedMessage());
                Message msg = new Message();
                msg.what = FAILURE_CODE;
                msg.obj = e.getLocalizedMessage();
                mResponseHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resp = response.body().string();
                    Log.e("ResponseSuccess", resp);
                    Message msg = new Message();
                    msg.what = SUCCESS_CODE;
                    msg.obj = resp;
                    mResponseHandler.sendMessage(msg);
                } else {
                    //响应失败
                    Log.e("判断响应码失败", response.message());
                }
            }
        });
    }


}
