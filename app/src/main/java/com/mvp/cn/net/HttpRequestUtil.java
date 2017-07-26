package com.mvp.cn.net;

import com.google.gson.Gson;
import com.mvp.cn.model.BaseRequestEntity;
import com.mvp.cn.model.BaseResponseEntity;
import com.mvp.cn.model.LoginEntity;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;

/**
 * 作者： qiaohao
 * 时间： 2017/7/19 14:13
 * 说明：HttpRequestUtil
 */
public class HttpRequestUtil extends OkHttpClientUtils  {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public volatile static HttpRequestUtil requestUtil;
    private  IHttpRequestService apiService;

    public HttpRequestUtil() {
        apiService = getRequestClient();
    }

    public static HttpRequestUtil getOkClient() {
        if (requestUtil == null) {
            synchronized (HttpRequestUtil.class){
                requestUtil = new HttpRequestUtil();
            }
        }
        return requestUtil;
    }


    public Call<String> login(String pwd){
//        BaseRequestEntity<LoginEntity> requestEntity = new BaseRequestEntity<LoginEntity>();
//        LoginEntity loginEntity = new LoginEntity();
//        loginEntity.setLoginPwd(pwd);
//        requestEntity.setBusiReqInfo(loginEntity);
//        String json = new Gson().toJson(requestEntity);
//        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Map<String,String> map = new HashMap<String,String>();
        map.put("platform","android");
        map.put("version","V1.1.1");
        return apiService.login(map);
    }



}
