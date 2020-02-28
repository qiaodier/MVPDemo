package com.mvp.cn.mvp.presenter;

import android.os.Build;

import com.mvp.cn.mvp.model.bean.BaseRespEntity;
import com.mvp.cn.mvp.model.bean.LoginEntity;
import com.mvp.cn.mvp.base.BasePresenter;
import com.mvp.cn.mvp.contract.LoginContract;
import com.mvp.cn.utils.LogUtil;

import java.util.Optional;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者： qiaohao
 * 时间： 2017/4/27 14:02
 * 说明：LoginPresnter
 */
public class LoginPresnter extends BasePresenter<LoginContract.Model, LoginContract.View> {


    public LoginPresnter(LoginContract.Model model) {
        init(model);
    }

    public void login(String name, String pwd) {
        mModel.login(new LoginEntity(name, pwd))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.get().getActivity().bindToLifecycle())
                .subscribe(new Observer<BaseRespEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.get().showLoading();
                        LogUtil.e("LoginPresenter", "showLoading");
                    }

                    @Override
                    public void onNext(BaseRespEntity baseRespEntity) {
                        LogUtil.e("LoginPresenter", "onNext");
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
                    public void onError(Throwable e) {
                        mView.get().hideLoading();
                        LogUtil.e("LoginPresenter", "onError");
                        String error = "";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            error = Optional
                                    .ofNullable(e)
                                    .map(Throwable::getLocalizedMessage)
                                    .orElse("请求错误");
                        } else {
                            if (e != null) {
                                error = e.getMessage();
                            } else {
                                error = "请求错误";
                            }
                        }
                        mView.get().requestFail(error);
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.e("LoginPresenter", "onComplete");
                        mView.get().hideLoading();
                        mView.get().requestComplete();
                    }
                });


    }
}
