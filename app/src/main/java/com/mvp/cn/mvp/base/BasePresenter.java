package com.mvp.cn.mvp.base;


import android.os.Build;

import com.tencent.mars.xlog.Log;

import java.lang.ref.WeakReference;
import java.util.Optional;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 作者： qiaohao
 * 时间： 2017/4/27 13:22
 * 说明：BasePresenter
 * 实现LifecycleObserver接口的类，既有能力监听activity和fragment的生命周期
 */
public abstract class BasePresenter<M extends IBaseModel, V extends IBaseView> implements LifecycleObserver {


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

    }


    public V getView() {
        return mView.get();
    }

    public void releasePrensenter() {
        mModel = null;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(LifecycleOwner owner) {
        Log.i(mView.get().getActivity().getLocalClassName(), "onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart(LifecycleOwner owner) {
        Log.i(mView.get().getActivity().getLocalClassName(), "onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume(LifecycleOwner owner) {
        Log.i(mView.get().getActivity().getLocalClassName(), "onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause(LifecycleOwner owner) {
        Log.i(mView.get().getActivity().getLocalClassName(), "onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop(LifecycleOwner owner) {
        Log.i(mView.get().getActivity().getLocalClassName(), "onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        Log.i(mView.get().getActivity().getLocalClassName(), "onDestroy");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny(LifecycleOwner owner) {
        Log.i(mView.get().getActivity().getLocalClassName(), "onAny");
    }


}
