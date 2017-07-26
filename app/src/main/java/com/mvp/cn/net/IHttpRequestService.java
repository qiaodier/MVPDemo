package com.mvp.cn.net;

import com.mvp.cn.model.BaseResponseEntity;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 作者： qiaohao
 * 时间： 2017/7/19 14:08
 * 说明：IHttpRequestService
 */
public interface IHttpRequestService {

    @GET("getpushmsg")
    Call<String> login(@QueryMap Map<String,String> login);

}
