package com.mvp.cn.mvp.model.bean;

import java.io.Serializable;

/**
 * 请求实体类基类
 *
 * @param <T>
 * @author iqiao
 */

public class BaseReqEntity<T> implements Serializable {

    /**
     * app版本号
     */
    private String clientVersion;
    /**
     * 客户端类型
     * 0：android
     * 1:ios
     */

    private String clientType;
    /**
     * 手机号
     */
    private String msisdn;
    /**
     * 请求数据
     */
    private T data;

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
