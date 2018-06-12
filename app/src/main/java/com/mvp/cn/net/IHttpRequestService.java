package com.mvp.cn.net;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 作者： qiaohao
 * 时间： 2017/7/19 14:08
 * 说明：IHttpRequestService 接口
 */
public interface IHttpRequestService {

    @GET("getpushmsg")
    Call<String> login(@QueryMap Map<String,String> login);

}
