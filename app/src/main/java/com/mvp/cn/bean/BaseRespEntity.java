package com.mvp.cn.bean;

import java.io.Serializable;

/**
 * 响应实体基类
 * @param <T>
 */
public class BaseRespEntity<T> implements Serializable {

    //响应状态码
    private int status;
    //响应状态描述
    private String message;
    //响应提体
    private T data;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
