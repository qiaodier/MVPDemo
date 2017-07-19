package com.mvp.cn.presenter;

import com.mvp.cn.interfacem.ILoginInterface;
import com.mvp.cn.model.BaseResponseEntity;
import com.mvp.cn.net.HttpRequestUtil;

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
                    mLoginInterface.requestSuccess();
                }

                @Override
                public void onFailure(Call<BaseResponseEntity<String>> call, Throwable t) {
                    mLoginInterface.requestFail();
                }
            });


        }
    }
}
