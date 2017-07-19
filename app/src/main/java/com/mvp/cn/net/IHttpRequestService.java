package com.mvp.cn.net;

import com.mvp.cn.model.BaseResponseEntity;


import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 作者： qiaohao
 * 时间： 2017/7/19 14:08
 * 说明：IHttpRequestService
 */
public interface IHttpRequestService {

    @POST("/login")
    Call<BaseResponseEntity<String>> login(@Body RequestBody body);

}
