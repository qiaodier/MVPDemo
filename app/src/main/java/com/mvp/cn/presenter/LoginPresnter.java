package com.mvp.cn.presenter;

import com.google.gson.Gson;
import com.mvp.cn.interfacem.ILoginInterface;
import com.mvp.cn.model.BaseRequestEntity;
import com.mvp.cn.model.LoginEntity;
import com.mvp.cn.net.IRequestCallback;
import com.mvp.cn.net.OkHttpClientUtils;

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

    public void login(){
        String userName = mLoginInterface.getUserName();
        String userPwd = mLoginInterface.getUserPwd();
        if (userName!=null&&!userName.equals("")&&userPwd!=null&&!userPwd.equals("")) {
        mLoginInterface.showLoadingDialog();
        //网络请求
        BaseRequestEntity<LoginEntity> requestEntity = new BaseRequestEntity<LoginEntity>();
        LoginEntity entity = new LoginEntity();
        entity.setLoginPwd(mLoginInterface.getUserPwd());
        requestEntity.setBusiReqInfo(entity);
        //请求类型
        requestEntity.setBusinessType(0);
        OkHttpClientUtils.getOkClient().httpPostString(LOGIN_REQUEST_CODE, new Gson().toJson(requestEntity).toString(), new IRequestCallback() {
            @Override
            public void onSuccess(int reqesutCode, String response) {
                mLoginInterface.dismissLoadingDialog();
                mLoginInterface.requestSuccess();
            }

            @Override
            public void onFailure(int requestCode, String responseBody) {
                mLoginInterface.dismissLoadingDialog();
                mLoginInterface.requestFail();
            }
        });
        }else{
            mLoginInterface.showAlertMessage("请输入正确内容");
        }
    }
}
