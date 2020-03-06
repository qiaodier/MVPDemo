package com.mvp.master.http;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mvp.master.utils.JsonUtil;
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
 *
 * @author iqiao
 */

public abstract class BaseOkHttpUtil {

    private OkHttpClient.Builder okHttpClient;
    private StringBuilder mMessage = new StringBuilder();
    private Retrofit retrofit;

    /**
     * 单例模式（推荐使用）
     * 定义一个私有的内部类，在第一次用这个嵌套类时，会创建一个实例。
     * 而类型为SingletonHolder的类，
     * 只有在HttpRequestUtil.getOkClient()中调用，
     * 由于私有的属性，他人无法使用SingleHolder，
     * 不调用HttpRequestUtil.getOkClient()就不会创建实例。
     * 优点：达到了lazy loading的效果，即按需创建实例。
     */


    public BaseOkHttpUtil() {
        retrofit = this.init();
    }

    /**
     * 获取url
     * 使用者必须继承该类
     *
     * @return
     */
    protected abstract String getUrl();

    /**
     * 是否打印日志
     *
     * @return
     */
    protected abstract boolean enableLog();


    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T get(Class<T> service) {
        return retrofit.create(service);
    }

    public OkHttpClient getOk() {
        return okHttpClient.build();
    }

    private Retrofit init() {
        okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.readTimeout(10, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(10, TimeUnit.SECONDS);
        //打印http的body体
        if (enableLog()) {
            HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor((String message) -> {
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
            });
            mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addNetworkInterceptor(mHttpLoggingInterceptor);
        }
        okHttpClient.hostnameVerifier((String hostname, SSLSession session) -> {
            return true;
        });
        //如果有需要
//        okHttpClient.dns(CustomeDNS.getInstance());
        retrofit = new Retrofit.Builder()
                //baseurl
                .baseUrl(getUrl())
                // 传入请求客户端
                .client(okHttpClient.build())
                // 添加Gson转换工厂
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }


}
