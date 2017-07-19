package com.mvp.cn.net;

/**
 * 网络请求的回调接口
 */

public interface IRequestCallback {

     void onSuccess(int reqesutCode, String response);

     void onFailure(int requestCode, String responseBody);
}
