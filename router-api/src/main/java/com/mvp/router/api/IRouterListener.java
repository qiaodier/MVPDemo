package com.mvp.router.api;

import java.util.Map;

/**
 * Created by iqiao on 2020-02-23 23:00
 * Desc: register router interface
 *
 * @author iqiao
 */
public interface IRouterListener {
    /**
     * @param routerMap save activity
     */
    void register(Map<String, Class<?>> routerMap);
}
