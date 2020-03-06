package com.mvp.master.router;

import java.util.Map;

/**
 * Created by iqiao on 2020-02-23 23:00
 * Desc: 注册路由监听类
 *
 * @author iqiao
 */
public interface IRouterListener {
    /**
     * 所有使用@Route注解的activity都需要通过该方法进行注册
     * 目前已经自动生成注册类，调用者无需手动调用
     * @param routerMap
     */
    void register(Map<String, Class<?>> routerMap);
}
