package com.mvp.cn.net;

/**
 * 网络请求的回调接口
 */

public interface IRequestCallback {

    public void onSuccess(int reqesutCode, String response);

    public void onFailure(int requestCode, String responseBody);
}
