package com.mvp.cn.model;


import com.mvp.cn.BaseApplication;
import com.mvp.cn.comm.CommManager;
import com.mvp.cn.utils.APPUtils;

/**
 * Created by qiaohao on 2016/9/13.
 * 说明：网络请求报文的基类
 */
public class BaseRequestEntity<T> {
    //接口协议版本号
    private String version = "1.0";
    //app版本号
    private String clientVersion = APPUtils.getInstance(BaseApplication.getIns()).getAPPVersion();
    //手机imei
    private String imei=APPUtils.getInstance(BaseApplication.getIns()).getIMEI();
    //手机系统版本号
    private String phoneOsVersion=APPUtils.getInstance(BaseApplication.getIns()).getSystemVersion();
    //手机型号
    private String phoneModel=APPUtils.getInstance(BaseApplication.getIns()).getPhoneModel();
    //手机号码
    private String msisdn = CommManager.CURRENT_PHONE_NUM;
    //设备型号
    private String deviceModel="1";
    //设备类型  1：安卓  2 ：苹果
    private String phoneOsType="1";
    //loginToken
    private String loginToken;
    //业务类型
    private int businessType;
    //请求报文实体
    private T busiReqInfo;
    //mac 用于验证报文是否被修改
    private String mac;

    public BaseRequestEntity() {
    }

    public BaseRequestEntity(String version, String clientVersion, String imei, String phoneOsVersion, String phoneModel, String msisdn, String deviceModel, String phoneOsType, String loginToken, int businessType, T busiReqInfo, String mac) {
        this.version = version;
        this.clientVersion = clientVersion;
        this.imei = imei;
        this.phoneOsVersion = phoneOsVersion;
        this.phoneModel = phoneModel;
        this.msisdn = msisdn;
        this.deviceModel = deviceModel;
        this.phoneOsType = phoneOsType;
        this.loginToken = loginToken;
        this.businessType = businessType;
        this.busiReqInfo = busiReqInfo;
        this.mac = mac;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPhoneOsVersion() {
        return phoneOsVersion;
    }

    public void setPhoneOsVersion(String phoneOsVersion) {
        this.phoneOsVersion = phoneOsVersion;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getPhoneOsType() {
        return phoneOsType;
    }

    public void setPhoneOsType(String phoneOsType) {
        this.phoneOsType = phoneOsType;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public T getBusiReqInfo() {
        return busiReqInfo;
    }

    public void setBusiReqInfo(T busiReqInfo) {
        this.busiReqInfo = busiReqInfo;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
