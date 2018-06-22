package com.mvp.cn.net;

import android.util.Log;


import java.io.IOException;
import java.util.List;

import com.mvp.cn.BaseApplication;
import okhttp3.*;

import static com.mvp.cn.BaseApplication.BASE_TEST_URL;

/**
 * Created by qiaohao on 2016/7/11.
 * 说明：okhttp的拦截器
 *  设置了请求头内容{token}
 */
public class CustomInterceptor implements Interceptor {


    private String mToken;
    public CustomInterceptor(String token) {
        mToken = token;
    }

    public CustomInterceptor() {
        mToken="";
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取request
        Request request = chain.request();
        //获取request的创建者builder
        Request.Builder builder = request.newBuilder();
        //从request中获取headers，通过给定的键url_name
        List<String> headerValues = request.headers("url_name");
        if (headerValues != null && headerValues.size() > 0) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder.removeHeader("url_name");
            //匹配获得新的BaseUrl
            String headerValue = headerValues.get(0);
            HttpUrl newBaseUrl = null;
            if ("test".equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(BaseApplication.BASE_TEST_URL);
            }  else{
                newBaseUrl = HttpUrl.parse(BaseApplication.BASE_URL);
            }
            //从request中获取原有的HttpUrl实例oldHttpUrl
            HttpUrl oldHttpUrl = request.url();
            //重建新的HttpUrl，修改需要修改的url部分
            HttpUrl newFullUrl = oldHttpUrl
                    .newBuilder()
                    .scheme(newBaseUrl.scheme())
                    .host(newBaseUrl.host())
                    .port(newBaseUrl.port())
                    .build();
            //重建这个request，通过builder.url(newFullUrl).build()；
            //然后返回一个response至此结束修改
            builder = builder.url(newFullUrl);
        }
        builder.addHeader("Content-Type", "text/html; charset=UTF-8")
                .addHeader("accept", "*/*")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("token",mToken)
                .build();
        return chain.proceed(builder.build());

    }


//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request request = chain.request();
//        LogUtil.e("OKHttp","==="+request.url());
//        Response response = chain.proceed(request);
//        LogUtil.e("OKHttp","==="+response.code()+":"+response.message());
//        LogUtil.e("OKHttp","==="+response.peekBody(1024).string());
//        /**获取请求返回打印到日志中去*/
//        if(true) {
//            JSONObject obj = new JSONObject();
//            if(request != null) {
//                if ("POST".equals(request.method())) {//Post请求获取参数
//                    StringBuilder sb = new StringBuilder();
//                    sb.append("{");
//                    if (request.body() instanceof FormBody) {
//                        FormBody body = (FormBody) request.body();
//                        for (int i = 0; i < body.size(); i++) {
//                            sb.append("\"" + body.encodedName(i) + "\":\"" + body.encodedValue(i) + "\",");
//                        }
//                        sb.delete(sb.length() - 1, sb.length());
//                        sb.append("}");
//                    }
//                LogUtil.e("拦截器",sb.toString()+"");
//                }
//            }
//        }
//        return response;
//    }

}
