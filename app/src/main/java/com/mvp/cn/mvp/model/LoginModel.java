package com.mvp.cn.mvp.model;


import com.mvp.cn.http.OkHttpClientUtils;
import com.mvp.cn.mvp.contract.LoginContract;
import com.mvp.cn.mvp.model.api.HttpApi;
import com.mvp.cn.mvp.model.bean.LoginEntity;
import com.mvp.master.http.bean.BaseRespEntity;

import io.reactivex.Observable;


/**
 * Created by iqiao on 2020-02-22 12:20
 * Desc: 实现登录网络请求
 *
 * @author iqiao
 */
public class LoginModel implements LoginContract.Model {

    @Override
    public Observable<BaseRespEntity> login(LoginEntity entity) {
        return OkHttpClientUtils.getInstance().get(HttpApi.class).login(entity);
    }


}
