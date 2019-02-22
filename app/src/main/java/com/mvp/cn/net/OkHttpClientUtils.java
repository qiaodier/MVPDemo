package com.mvp.cn.net;


import com.mvp.cn.BuildConfig;
import com.mvp.cn.utils.JsonUtil;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    private Retrofit retrofit;
    private String token = "";
    private StringBuilder mMessage = new StringBuilder();

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
//        getRequestClient();
    }

    /**
     * 更新token方法
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
        okHttpClient = null;
    }

    public  OkHttpClient getOk(){
        return okHttpClient.build();
    }


    public IHttpRequestService getRequestClient() {
        IHttpRequestService iHttpRequestService = null;
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder();
            okHttpClient.connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS);
            if (SSLManager.createAllSSLSocketFactory() != null) {
                okHttpClient.sslSocketFactory(SSLManager.createAllSSLSocketFactory());
            }
            HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor(this);
//            //打印http的body体
            mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addNetworkInterceptor(mHttpLoggingInterceptor);
//            HttpLoggerInterceptor httpLoggerInterceptor = new HttpLoggerInterceptor();
//            httpLoggerInterceptor.setLevel(HttpLoggerInterceptor.Level.BODY);
//            okHttpClient.addInterceptor(httpLoggerInterceptor);
            okHttpClient.hostnameVerifier((String hostname, SSLSession session)-> {
                return true;
            });
            okHttpClient.addInterceptor((Interceptor.Chain chain) -> {
                //获取request的创建者builder
                Request request = chain.request().newBuilder().addHeader("token", token)
                        .addHeader("Content-Type", "text/html; charset=UTF-8")
                        .build();
                Response response = chain.proceed(request);
                return response;
            });
        }


        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)//baseurl
                .client(okHttpClient.build()) // 传入请求客户端
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换工厂
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
