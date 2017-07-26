package com.mvp.cn.net;

import android.util.Log;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by qiaohao on 2016/7/11.
 * 说明：okhttp的拦截器  设置了请求头内容
 */
public class CustomInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request newrequest = chain.request().newBuilder()
                .addHeader("Content-Type", "text/html; charset=UTF-8")
                .addHeader("accept", "*/*")
                .addHeader("Connection", "Keep-Alive")
                .build();
//        Response response = chain.proceed(newrequest);
//        ResponseBody responseBody = response.peekBody(1024*1024);
//        Log.e("Interceptor",String.format("接收响应: [%s] %n返回json:%s %n头部%s",
//                response.request().url(),
//                responseBody.string(),
//                response.headers()));
        return chain.proceed(newrequest);
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
