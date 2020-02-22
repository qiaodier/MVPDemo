package com.mvp.cn.bean;

/**
 * Created by qiaohao on 2016/9/20.
 * 说明：
 */
public class LoginEntity {
   private String name;
   private String pwd;

    public LoginEntity(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
