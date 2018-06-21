package com.mvp.cn.net;

import com.google.gson.Gson;
import com.mvp.cn.BaseApplication;

import java.util.HashMap;
import java.util.Map;

import com.mvp.cn.model.BaseRequestEntity;
import com.mvp.cn.model.LoginEntity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * 作者： qiaohao
 * 时间： 2017/7/19 14:13
 * 说明：HttpRequestUtil
 * 注：首次调用HttpRequestUtil.getOkClient()之前必须设置BaseApplication.TOKEN_VALUES值
 */
public class HttpRequestUtil extends OkHttpClientUtils {

    private IHttpRequestService apiService;

    public HttpRequestUtil() {
        apiService = getRequestClient();
    }
    /**
     * 单例模式（推荐使用）
     * 定义一个私有的内部类，在第一次用这个嵌套类时，会创建一个实例。
     * 而类型为SingletonHolder的类，
     * 只有在HttpRequestUtil.getOkClient()中调用，
     * 由于私有的属性，他人无法使用SingleHolder，
     * 不调用HttpRequestUtil.getOkClient()就不会创建实例。
     * 优点：达到了lazy loading的效果，即按需创建实例。
     */
    private static class SingletonHolder {
        private final static HttpRequestUtil instance = new HttpRequestUtil();
    }

    public static HttpRequestUtil getOkClient() {
        return SingletonHolder.instance;
    }


    public Call<String> login(String pwd) {
        BaseRequestEntity<LoginEntity> requestEntity = new BaseRequestEntity<LoginEntity>();
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setLoginPwd(pwd);
        requestEntity.setBusiReqInfo(loginEntity);
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("platform", "android");
//        map.put("version", "V1.1.1");
        return apiService.login(requestEntity);
    }


}
