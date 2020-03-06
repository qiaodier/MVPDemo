package com.mvp.cn.mvp.model.api;

import com.mvp.cn.mvp.model.bean.LoginEntity;
import com.mvp.master.http.bean.BaseRespEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author iqiao
 * @date 2020-03-06 14:03
 * @desc
 */
public interface HttpApi {

    /**
     * 登录接口
     *
     * @param loginEntity 登录参数
     * @return 登录响应
     */
    @POST("user/login")
    Observable<BaseRespEntity> login(@Body LoginEntity loginEntity);
}
