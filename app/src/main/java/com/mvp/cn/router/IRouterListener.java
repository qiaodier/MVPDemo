package com.mvp.cn.router;

import java.util.Map;

/**
 * Created by iqiao on 2020-02-23 23:00
 * Desc: 注册路由监听类
 */
public interface IRouterListener {
    void register(Map<String, Class<?>> routerMap);
}
