package com.mvp.cn.mvp.base;

import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

/**
 * 作者： qiaohao
 * 时间： 2017/4/27 13:40
 * 说明：IBaseView
 */
public interface IBaseView {
    /**
     * 获取当前activity
     *
     * @return
     */
    RxAppCompatActivity getActivity();

    /**
     * 显示loading
     */
    void showLoading();
    /**
     * 隐藏loading
     */
    void hideLoading();

    /**
     * 请求失败
     *
     * @param msg
     */
    void requestFail(String msg);

    /**
     * 请求完成
     */
    void requestComplete();


}
