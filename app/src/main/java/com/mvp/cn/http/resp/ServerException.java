package com.mvp.cn.http.resp;

/**
 * @author iqiao
 * @date 2018/2/27
 * 描述：服务器下发的错误
 */
public class ServerException extends RuntimeException {

    public int code;

    public ServerException(int code, String message) {
        super(message);
        this.code = code;
    }
}
