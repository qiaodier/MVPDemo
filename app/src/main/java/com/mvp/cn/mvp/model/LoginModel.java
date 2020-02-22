package com.mvp.cn.mvp.model.imp;

import com.mvp.cn.bean.BaseRespEntity;
import com.mvp.cn.bean.LoginEntity;
import com.mvp.cn.mvp.contract.LoginContract;
import com.mvp.cn.net.OkHttpClientUtils;

import io.reactivex.Observable;

/**
 * Created by iqiao on 2020-02-22 12:20
 * Desc:
 */
public class LoginModelImp implements LoginContract.Model {

    @Override
    public Observable<BaseRespEntity> login(LoginEntity entity) {
        return OkHttpClientUtils.getInstance().getRequestClient().login(entity);
    }
}
