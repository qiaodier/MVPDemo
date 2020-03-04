package com.mvp.cn.http.framework;

import io.reactivex.Observable;

/**
 * Created by iqiao on 2020-03-04 16:39
 * Desc:
 * @author iqiao
 */
public interface IHttpRequest<T> {

    Observable<T> setRequest(Observable<T> observable);

    void setRequestLisener(IHttpListener iHttpListener);

    void execute();
}
