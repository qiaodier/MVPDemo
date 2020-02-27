package com.mvp.cn.net;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mvp.cn.BuildConfig;
import com.mvp.cn.utils.JsonUtil;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 公用的okhttp对象
 * 该类增加了okhttp 的网络请求日志，便于调试
 * 增加https的忽略验证
 */

public class OkHttpClientUtils implements HttpLoggingInterceptor.Logger {

    private OkHttpClient.Builder okHttpClient;
    private StringBuilder mMessage = new StringBuilder();
    private IHttpRequestService iHttpRequestService;

    /**
     * 单例模式（推荐使用）
     * 定义一个私有的内部类，在第一次用这个嵌套类时，会创建一个实例。
     * 而类型为SingletonHolder的类，
     * 只有在HttpRequestUtil.getOkClient()中调用，
     * 由于私有的属性，他人无法使用SingleHolder，
     * 不调用HttpRequestUtil.getOkClient()就不会创建实例。
     * 优点：达到了lazy loading的效果，即按需创建实例。
     */
    private static class SingletonHolder {
        private final static OkHttpClientUtils instance = new OkHttpClientUtils();
    }

    public static OkHttpClientUtils getInstance() {
        return SingletonHolder.instance;
    }

    public OkHttpClientUtils() {
        iHttpRequestService = this.init();
    }

    public IHttpRequestService getRequest() {
        return iHttpRequestService;
    }

    public OkHttpClient getOk() {
        return okHttpClient.build();
    }

    private IHttpRequestService init() {
        IHttpRequestService iHttpRequestService;
        okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.readTimeout(10, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(10, TimeUnit.SECONDS);
        if (SSLManager.createAllSSLSocketFactory() != null) {
            okHttpClient.sslSocketFactory(SSLManager.createAllSSLSocketFactory());
        }
        //打印http的body体
        HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor(this);
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.addNetworkInterceptor(mHttpLoggingInterceptor);
        okHttpClient.hostnameVerifier((String hostname, SSLSession session) -> {
            return true;
        });
        //如果有需要
//        okHttpClient.dns(CustomeDNS.getInstance());
        Retrofit retrofit = new Retrofit.Builder()
                //baseurl
                .baseUrl(BuildConfig.BASE_URL)
                // 传入请求客户端
                .client(okHttpClient.build())
                // 添加Gson转换工厂
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        iHttpRequestService = retrofit.create(IHttpRequestService.class);
        return iHttpRequestService;
    }


    @Override
    public void log(String message) {
//        Log.e("",mMessage.toString());
        // 请求或者响应开始
        if (message.startsWith("--> POST") || message.startsWith("--> GET")) {
            mMessage.setLength(0);
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message));
        }
        mMessage.append(message.concat("\n"));
        // 请求或者响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            Logger.e(mMessage.toString());
        }

    }
}
