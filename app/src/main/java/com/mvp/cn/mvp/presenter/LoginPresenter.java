package com.mvp.cn.mvp.presenter;

import android.os.Build;
import android.util.Log;

import com.mvp.cn.mvp.contract.LoginContract;
import com.mvp.cn.mvp.model.bean.LoginEntity;
import com.mvp.master.http.HttpRequestUtils;
import com.mvp.master.http.bean.BaseRespEntity;
import com.mvp.master.http.resp.BaseObserver;
import com.mvp.master.mvp.base.BasePresenter;


import java.util.Optional;

/**
 * 作者： qiaohao
 * 时间： 2017/4/27 14:02
 * 说明：LoginPresnter
 *
 * @author iqiao
 */
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {


    public LoginPresenter(LoginContract.Model model) {
        init(model);
    }

    public void login(String name, String pwd) {
        HttpRequestUtils.applyScheduler(mModel.login(new LoginEntity(name, pwd)), mView.get())
                .subscribe(new BaseObserver<BaseRespEntity>(mView.get()) {
                    @Override
                    public void onSuccess(BaseRespEntity baseRespEntity) {
                        Log.e("LoginPresenter", "onSuccess");
                        int status = 0;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            status = Optional
                                    .ofNullable(baseRespEntity)
                                    .map(BaseRespEntity::getStatus)
                                    .orElse(0);
                        } else {
                            if (baseRespEntity != null) {
                                status = baseRespEntity.getStatus();
                            } else {
                                status = 0;
                            }
                        }

                        if (status == 1) {
                            mView.get().requestSuccess();
                        } else {
                            String msg = "";
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                msg = Optional
                                        .ofNullable(baseRespEntity)
                                        .map(BaseRespEntity::getMessage)
                                        .orElse("请求失败");
                            } else {
                                if (baseRespEntity != null) {
                                    msg = baseRespEntity.getMessage();
                                } else {
                                    msg = "请求失败";
                                }
                            }
                            mView.get().requestFail(msg);
                        }
                    }

                    @Override
                    public void onFailure(String e) {
                        mView.get().requestFail(e);
                    }

                });


    }
}
