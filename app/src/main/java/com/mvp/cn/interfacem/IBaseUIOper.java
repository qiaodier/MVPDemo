package com.mvp.cn.interfacem;

/**
 * 作者： qiaohao
 * 时间： 2017/4/27 13:40
 * 说明：IBaseUIOper
 */
public  interface IBaseUIOper {


    void showLoadingDialog();

    void dismissLoadingDialog();

    void showAlertMessage(String content);

    void requestSuccess();

    void requestFail();



}
