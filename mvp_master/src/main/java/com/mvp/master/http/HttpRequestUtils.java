package com.mvp.master.http;

import com.mvp.master.mvp.base.IBaseView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by iqiao on 2020-03-05 09:07
 * Desc: 网络工具封装类
 *
 * @author iqiao
 */
public class HttpRequestUtils {


//    public static <T> ObservableTransformer<T, T> applyScheduler(final Observer<T> observer, final IBaseView baseView) {
//        return new ObservableTransformer<T, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<T> upstream) {
//                Observable<T> tObservable = upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                        .compose(baseView.getActivity().bindToLifecycle());
//                tObservable.subscribe(observer);
//                return tObservable;
//            }
//        };
//    }

    /**
     * 该方法封装线程执行和绑定生命周期
     *
     * @param observable
     * @param baseView
     * @param <T>
     * @return
     */
    public static <T> Observable<T> applyScheduler(final Observable<T> observable, IBaseView baseView) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .compose(baseView.getActivity().bindToLifecycle());
    }


}
