package com.mvp.cn.mvp.model.bean;

/**
 * Created by qiaohao on 2016/9/13.
 * 说明：
 */
public class BaseResponseEntity<T> {
    //接口协议版本号
    private String version;
    //状态
    private Integer status;
    //操作描述
    private String desc;
    //响应报文实体
    private T busiRespInfo;

    public BaseResponseEntity(String version, Integer status, String desc, T busiRespInfo) {
        this.version = version;
        this.status = status;
        this.desc = desc;
        this.busiRespInfo = busiRespInfo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getBusiRespInfo() {
        return busiRespInfo;
    }

    public void setBusiRespInfo(T busiRespInfo) {
        this.busiRespInfo = busiRespInfo;
    }
}
