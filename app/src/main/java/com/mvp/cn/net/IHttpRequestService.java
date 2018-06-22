package com.mvp.cn.net;

import java.util.Map;

import com.mvp.cn.model.BaseRequestEntity;
import com.mvp.cn.model.LoginEntity;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * 作者： qiaohao
 * 时间： 2017/7/19 14:08
 * 说明：IHttpRequestService 接口
 */
public interface IHttpRequestService {
    @Headers({"url_name:test"})
    @GET("getpushmsg")
    Call<String> login(@QueryMap Map<String,String> login);

    @POST("login")
    Call<String> login(@Body BaseRequestEntity<LoginEntity> baseRequestEntity);

}
