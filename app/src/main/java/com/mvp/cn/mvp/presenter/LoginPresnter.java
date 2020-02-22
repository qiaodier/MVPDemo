package com.mvp.cn.mvp.presenter;

import com.mvp.cn.bean.BaseRespEntity;
import com.mvp.cn.bean.LoginEntity;
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
                        int status = Optional
                                .ofNullable(baseRespEntity)
                                .map(BaseRespEntity::getStatus)
                                .orElse(0);
                        if (status == 1) {
                            mView.get().requestSuccess();
                        } else {
                            String msg = Optional
                                    .ofNullable(baseRespEntity)
                                    .map(BaseRespEntity::getMessage)
                                    .orElse("请求失败");
                            mView.get().requestFail(msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.get().hideLoading();
                        LogUtil.e("LoginPresenter", "onError");
                        mView.get().requestFail(Optional
                                .ofNullable(e)
                                .map(Throwable::getLocalizedMessage)
                                .orElse("请求错误"));
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
