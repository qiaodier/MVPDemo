package com.mvp.cn.http.framework;

import io.reactivex.Observable;

/**
 * Created by iqiao on 2020-03-04 16:45
 * Desc:
 * @author iqiao
 */
public class HttpTask<T> implements IHttpRequest<T> {

    private Observable<T> observable;

    @Override
    public Observable<T> setRequest(Observable<T> observable) {
        this.observable = observable;
        return this.observable;
    }

    @Override
    public void setRequestLisener(IHttpListener iHttpListener) {

    }

    @Override
    public void execute() {

    }
}
