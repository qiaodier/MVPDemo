package com.mvp.cn.net;


import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mvp.cn.BaseApplication;
import com.mvp.cn.comm.CommManager;



import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 公用的okhttp对象
 * 该类增加了okhttp 的网络请求日志，便于调试
 * 增加https的忽略验证
 */

public class OkHttpClientUtils implements HttpLoggingInterceptor.Logger{

    private String token;
    //设置是否支持请求headertoken  默认false：不支持；否则需要给token设置值
    private boolean isSupportHeaderToken;
    //设置是否支持https 的ssl 忽略验证  默认false：不忽略；
    private boolean isSupportSSL;
    //设置是否支持日志打印 默认true:支持网络请求日志显示
    private boolean isSupportLogcat = true;
    private OkHttpClient.Builder okHttpClient;
    private Retrofit retrofit;

    public void setSupportLogcat(boolean supportLogcat) {
        isSupportLogcat = supportLogcat;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setSupportHeaderToken(boolean supportHeaderToken) {
        isSupportHeaderToken = supportHeaderToken;
    }

    public void setSupportSSL(boolean supportSSL) {
        isSupportSSL = supportSSL;
    }


    protected IHttpRequestService getRequestClient() {
        IHttpRequestService iHttpRequestService;
        if (okHttpClient==null&&retrofit==null) {
            okHttpClient = new OkHttpClient.Builder();
            okHttpClient.connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS);
            //判断是否要请求header带token
            CustomInterceptor customInterceptor;
            if (isSupportHeaderToken){
                customInterceptor = new CustomInterceptor(token);
            }else{
                customInterceptor =new CustomInterceptor();
            }
            okHttpClient.addInterceptor(customInterceptor);
            //判断是否增加https ssl 验证
            SSLSocketFactory sslSocketFactory;
            HostnameVerifier DO_NOT_VERIFY;
            if (isSupportSSL){
                X509TrustManager xtm = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        X509Certificate[] x509Certificates = new X509Certificate[0];
                        return x509Certificates;
                    }
                };

                SSLContext sslContext = null;
                try {
                    sslContext = SSLContext.getInstance("SSL");

                    sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                }
                DO_NOT_VERIFY = new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };
                sslSocketFactory = sslContext.getSocketFactory();
                //添加拦截器
                okHttpClient.sslSocketFactory(sslSocketFactory)
                        .hostnameVerifier(DO_NOT_VERIFY);
            }
            //判断是否显示日志
            HttpLoggingInterceptor mHttpLoggingInterceptor;
            if (isSupportLogcat) {
                mHttpLoggingInterceptor = new HttpLoggingInterceptor(this);
                //打印http的body体
                mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClient.addInterceptor(mHttpLoggingInterceptor);
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseApplication.BASE_URL)//baseurl
                    .client(okHttpClient.build()) // 传入请求客户端
                    .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换工厂
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加RxJava2调用适配工厂
                    .build();
        }
        iHttpRequestService = retrofit.create(IHttpRequestService.class);
        return iHttpRequestService;
    }


    @Override
    public void log(String message) {
        Log.e("OKHTTP", "OKLOG: " + message);
    }
}
