package com.mvp.cn.net;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by qiaohao on 2016/7/11.
 * 说明：okhttp的拦截器
 * 设置了请求头内容{token}
 */
public class CustomInterceptor implements Interceptor {

    private String mToken;


    public void setmToken(String mToken) {
        this.mToken = mToken;
    }


    public CustomInterceptor(String mToken) {
        this.mToken = mToken;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取request
        Request request = chain.request();
        //获取request的创建者builder
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Content-Type", "text/html; charset=UTF-8")
//                .addHeader("accept", "*/*")
//                .addHeader("Connection", "Keep-Alive")
                .addHeader("token", mToken)
                .build();
//        KLog.e("HTTP_log","╔═══════════════════════════════════════════════════════════════════════════════════════");
//        KLog.e("HTTP_log", "request==>");
//        KLog.e("HTTP_log", "url:" + request.url());
////        KLog.e("HTTP_log", "header:" + request.headers());
//        KLog.e("HTTP_log", "body:" + chain.connection());
        Response response = chain.proceed(builder.build());
//        ResponseBody responseBody = response.peekBody(1024 * 1024);
//        KLog.e("HTTP_log", "response==>");
//        KLog.e("HTTP_log", "url:" + response.request().url());
////        KLog.e("HTTP_log", "header:" + response.headers());
//        KLog.e("HTTP_log", "body:" + responseBody.string());
//        KLog.e("HTTP_log","╚═══════════════════════════════════════════════════════════════════════════════════════");
        return response;
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
