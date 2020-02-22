package com.mvp.cn.net;



import com.mvp.cn.bean.BaseRespEntity;

import java.io.EOFException;
import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 网络请求的回调接口
 *
 */

public abstract class IRequestCallback<T> implements Callback<BaseRespEntity<T>> {

    @Override
    public void onResponse(Call call, Response response) {
        if (response.raw().code() == 200) {
//            if (((BaseRespEntity)response.body()).getStatus()==4){
//                Intent intent = new Intent(BaseApplication.getInstance(), LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                BaseApplication.getInstance().startActivity(intent);
//                return;
//            }
            onSuccess(response.body());
        }else if(response.raw().code() == 404){
            onFailure("请求失败,404.");
        }else{
           onFailure("请求失败");
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        if (t.getCause() instanceof ConnectException) {
            onNetError("服务器连接失败");
        }else if(t.getMessage()!=null&&t.getMessage().equals("timeout")){
            onNetError("服务器连接超时");
        }else if(t.getCause() instanceof EOFException){
            onFailure("响应报文无法解析");
        }else{
            onFailure(t.getMessage()!=null&&t.getMessage().length()>0?t.getMessage():"请求失败");
        }
    }


    public abstract void onSuccess(Object response);

    public abstract void onNetError(String response);

    public abstract void onFailure(String message);
}
