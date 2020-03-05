package com.mvp.cn.http.api;


import com.mvp.cn.mvp.model.bean.BaseRespEntity;
import com.mvp.cn.mvp.model.bean.LoginEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者： qiaohao
 * 时间： 2017/7/19 14:08
 * 说明：IHttpRequestService 接口
 *
 * @author iqiao
 */
public interface IHttpRequestService {

    /**
     * 登录接口
     *
     * @param loginEntity
     * @return
     */
    @POST("user/login")
    Observable<BaseRespEntity> login(@Body LoginEntity loginEntity);
}
