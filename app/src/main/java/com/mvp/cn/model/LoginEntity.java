package com.mvp.cn.model;

/**
 * Created by qiaohao on 2016/9/20.
 * 说明：
 */
public class LoginEntity {
    private String loginPwd;


    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public LoginEntity() {
    }

    public LoginEntity(String loginPwd) {
        this.loginPwd = loginPwd;
    }
}
