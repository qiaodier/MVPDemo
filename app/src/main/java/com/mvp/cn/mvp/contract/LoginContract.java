package com.mvp.cn.mvp.contract;


import com.mvp.cn.mvp.model.bean.LoginEntity;
import com.mvp.master.http.bean.BaseRespEntity;
import com.mvp.master.mvp.base.IBaseModel;
import com.mvp.master.mvp.base.IBaseView;

import io.reactivex.Observable;

/**
 * Created by iqiao on 2020-02-22 11:56
 * Desc:
 *
 * @author iqiao
 */
public interface LoginContract {

    interface View extends IBaseView {
        /**
         * 无参数返回
         */
        void requestSuccess();


    }

    interface Model extends IBaseModel {
        /**
         * 定义网络请求接口
         * 由于写的是简单的登录示例，只用基础响应实体来接收
         *
         * @param entity
         * @return
         */
        Observable<BaseRespEntity> login(LoginEntity entity);
    }
}
