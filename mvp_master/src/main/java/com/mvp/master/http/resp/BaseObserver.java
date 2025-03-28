package com.mvp.master.http.resp;

import android.util.Log;

import com.mvp.master.http.bean.BaseRespEntity;
import com.mvp.master.mvp.base.IBaseView;


import java.lang.ref.WeakReference;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by iqiao on 2020-03-05 09:28
 * Desc: 处理了网络请求回调中的异常，调用者只关注业务异常即可
 *
 * @author iqiao
 */
public abstract class BaseObserver<T> implements Observer<T> {

    private WeakReference<IBaseView> baseView;

    public BaseObserver(IBaseView iBaseView) {
        baseView = new WeakReference<>(iBaseView);
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (baseView != null && baseView.get() != null) {
            baseView.get().showLoading();
        }
    }

    @Override
    public void onNext(T t) {
        if (t instanceof BaseRespEntity) {
            if (((BaseRespEntity) t).isOk()) {
                onSuccess(t);
            } else {
                onFailure(((BaseRespEntity) t).getMessage() + "");
            }
        }

    }

    @Override
    public void onError(Throwable e) {
        if (baseView != null && baseView.get() != null) {
            baseView.get().hideLoading();
        }
        onNetError(e);
    }

    @Override
    public void onComplete() {
        if (baseView != null && baseView.get() != null) {
            baseView.get().hideLoading();
        }
    }

    /**
     * 成功回调
     *
     * @param t
     */
    protected abstract void onSuccess(T t);

    /**
     * 失败回调
     *
     * @param errorMsg
     */
    protected abstract void onFailure(String errorMsg);

    /**
     * 网络错误
     *
     * @param e
     */
    protected void onNetError(Throwable e) {
        Log.e("NetError", e.getMessage());
        if (baseView != null && baseView.get() != null) {
            NetworkError.error(baseView.get().getActivity().getApplicationContext(), e);
        }
    }

}
