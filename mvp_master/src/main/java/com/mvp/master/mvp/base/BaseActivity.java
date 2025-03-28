package com.mvp.master.mvp.base;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.mvp.master.utils.CustomDialogUtils;

import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.util.Optional;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;



/**
 * 作者： qiaohao
 * 时间： 2017/4/27 13:19
 * 说明：BaseActivity
 *
 * @author iqiao
 */
public abstract class BaseActivity<T extends BasePresenter, V extends IBaseView> extends RxAppCompatActivity implements LifecycleObserver {


    public Dialog mLoading;

    protected T mPrensenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID());
        mPrensenter = initPresenter();
        getLifecycle().addObserver(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Optional.ofNullable(mPrensenter).ifPresent(prensenter -> {
                prensenter.attachView((V) this);
            });
        } else {
            if (mPrensenter != null) {
                mPrensenter.attachView((V) this);
            }
        }
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Optional
                    .ofNullable(mPrensenter)
                    .ifPresent(t -> {
                        t.detachView();
                    });
        } else {
            if (mPrensenter != null) {
                mPrensenter.detachView();
            }
        }

    }

    /**
     * 该方法必须重写
     *
     * @return 返回activity中对应的xml的id
     */
    protected abstract int layoutResID();

    /**
     * 初始化组件的方法
     */
    protected abstract void initViews();

    /**
     * 该方法必须重写
     *
     * @return 初始化presenter操作类
     */
    protected abstract T initPresenter();


    /**
     * toast参数是字符串
     * 显示默认位置
     *
     * @param text
     */
    public void showMessage(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showLoadingDialog() {
        if (mLoading == null) {
            mLoading = CustomDialogUtils.getInstance(this).showLoadingDialog();
            mLoading.show();
        } else if (mLoading != null && !mLoading.isShowing()) {
            mLoading.show();
        }
    }

    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 取消loadding 对话框的显示
     */
    public void dismissLoadingDialog() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.cancel();
            mLoading = null;
        }

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(LifecycleOwner owner) {
        Log.i(this.getClass().getName(), "onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart(LifecycleOwner owner) {
        Log.i(this.getClass().getName(), "onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume(LifecycleOwner owner) {
        Log.i(this.getClass().getName(), "onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause(LifecycleOwner owner) {
        Log.i(this.getClass().getName(), "onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop(LifecycleOwner owner) {
        Log.i(this.getClass().getName(), "onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        Log.i(this.getClass().getName(), "onDestroy");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny(LifecycleOwner owner) {
        Log.i(this.getClass().getName(), "onAny");
    }

}
