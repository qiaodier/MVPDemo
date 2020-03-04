package com.mvp.cn.http.framework;

import io.reactivex.Observable;

/**
 * Created by iqiao on 2020-03-04 16:43
 * Desc:
 */
public interface IHttpListener {

    void onSuccess(Observable observable);

}
