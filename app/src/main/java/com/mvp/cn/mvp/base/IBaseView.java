package com.mvp.cn.mvp.view;

import android.app.Activity;

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
    Activity getActivity();

    /**
     * 显示loading
     */
    default void showLoading() {

    }

    /**
     * 隐藏loading
     */
    default void hideLoading() {

    }


}
