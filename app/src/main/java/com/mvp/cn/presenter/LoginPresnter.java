package com.mvp.cn.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.mvp.cn.interfacem.ILoginInterface;
import com.mvp.cn.model.BaseResponseEntity;
import com.mvp.cn.net.HttpRequestUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者： qiaohao
 * 时间： 2017/4/27 14:02
 * 说明：LoginPresnter
 */
public class LoginPresnter extends BasePresenter<ILoginInterface> {

    private static final int LOGIN_REQUEST_CODE = 0x01;
    public ILoginInterface mLoginInterface;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLoginInterface.dismissLoadingDialog();
            switch (msg.what){
                case 1:
                    mLoginInterface.requestSuccess();
                    break;
                case 2:
                    mLoginInterface.requestFail();
                    break;
            }
        }
    };



    public LoginPresnter(ILoginInterface loginInterface) {
        this.mLoginInterface = loginInterface;
    }

    public void login() {
        String userName = mLoginInterface.getUserName();
        String userPwd = mLoginInterface.getUserPwd();
        if (userName != null && !userName.equals("") && userPwd != null && !userPwd.equals("")) {
            mLoginInterface.showLoadingDialog();
            HttpRequestUtil.getOkClient().login(userPwd).enqueue(new Callback<BaseResponseEntity<String>>() {
                @Override
                public void onResponse(Call<BaseResponseEntity<String>> call, Response<BaseResponseEntity<String>> response) {
                    mHandler.sendEmptyMessage(1);
                }

                @Override
                public void onFailure(Call<BaseResponseEntity<String>> call, Throwable t) {
                    mHandler.sendEmptyMessage(2);
                }
            });


        }
    }
}
