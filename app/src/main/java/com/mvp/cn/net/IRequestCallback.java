package com.mvp.cn.net;



import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 网络请求的回调接口
 * 根据响应判断是否登录
 */

public abstract class IRequestCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.raw().code() == 200) {
            onSuccess(response.body());
        }else{
           onFailure("请求失败");
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t.getCause() instanceof ConnectException) {
            onNetError("服务器连接失败");
        }else if(t.getMessage()!=null&&t.getMessage().equals("timeout")){
            onNetError("服务器连接超时");
        }else{
            onFailure(t.getMessage());
        }
    }


    public abstract void onSuccess(T response);

    public abstract void onNetError(String response);

    public abstract void onFailure(String message);
}
