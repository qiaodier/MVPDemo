package com.mvp.master.mvp.base;


import android.os.Build;

import java.lang.ref.WeakReference;
import java.util.Optional;

/**
 * 作者： qiaohao
 * 时间： 2017/4/27 13:22
 * 说明：BasePresenter
 * 实现LifecycleObserver接口的类，既有能力监听activity和fragment的生命周期
 * @author iqiao
 */
public abstract class BasePresenter<M extends IBaseModel, V extends IBaseView> {


    protected M mModel;

    protected WeakReference<V> mView;

    /**
     * 初始化
     *
     * @param model
     */
    public void init(M model) {
        this.mModel = model;
    }

    /**
     * 绑定view
     */
    public void attachView(V view) {
        this.mView = new WeakReference<>(view);
    }

    /**
     * 解绑
     */
    public void detachView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Optional
                    .ofNullable(mView)
                    .ifPresent((WeakReference<V> vWeakReference) -> {
                        vWeakReference.clear();
                    });
        }else{
            if (mView!=null){
                mView.clear();
            }
        }
        releasePrensenter();
    }


    public V getView() {
        return mView.get();
    }

    public void releasePrensenter() {
        mModel = null;
    }





}
