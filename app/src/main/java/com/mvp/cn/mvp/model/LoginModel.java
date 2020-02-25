package com.mvp.cn.mvp.model;

import com.mvp.cn.mvp.model.bean.BaseRespEntity;
import com.mvp.cn.mvp.model.bean.LoginEntity;
import com.mvp.cn.mvp.contract.LoginContract;
import com.mvp.cn.net.OkHttpClientUtils;

import io.reactivex.Observable;

/**
 * Created by iqiao on 2020-02-22 12:20
 * Desc: 实现登录网络请求
 */
public class LoginModel implements LoginContract.Model {

    @Override
    public Observable<BaseRespEntity> login(LoginEntity entity) {
        return OkHttpClientUtils.getInstance().getRequest().login(entity);
    }
}
