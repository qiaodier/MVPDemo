package com.mvp.master.router;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.mvp.router.api.IRouterListener;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dalvik.system.DexFile;

/**
 * Created by iqiao on 2020-02-23 22:32
 * Desc: 路由管理类：当前类负责存储所有Activity以及对应的key
 * 提供启动activity的功能
 * @author iqiao
 */
public class RouterManager {

    private Map<String, Class<?>> routerMap;
    private Application application;


    public void init(Application application) {
        routerMap = new HashMap<>();
        this.application = application;
        Log.e("RouterManager","init");
        /**
         * 这里的弊端是在application启动的时候注册所有的路由注解类，会影响启动速度
         */
        try {
            loadInfo();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
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
        private final static RouterManager instance = new RouterManager();
    }

    public static RouterManager getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 注册进路由
     *
     * @param routeName
     * @param routeClass
     */
    public void registerRouter(String routeName, Class<?> routeClass) {
        this.routerMap.put(routeName, routeClass);
    }

    /**
     * 跳转调用 无参数
     *
     * @param routeName
     */
    public void navigation(String routeName) {
        if (routerMap != null && application != null) {
            Class<?> routeClass = this.routerMap.get(routeName);
            if (routeClass!=null){
                Intent routeIntent = new Intent(application, routeClass);
                routeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                application.startActivity(routeIntent);
            }else{
                Log.e("RouterManager","navigation error");
            }
        }else{
            Log.e("RouterManager","RouterManager Uninitialized");
        }
    }

    /**
     * 扫描到自动生成的注册代码类之后，自动执行registe方法
     *
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void loadInfo() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        //获得所有 apt生成的路由注册类的(路由表)
        Set<String> routerSetMap = getClassName("com.mvp.cn.mvp.view.router");
        Log.e("RouterManager","com.mvp.cn.mvp.view.router"+"      " + routerSetMap.size()+"");
        for (String className : routerSetMap) {
            Log.e("RouterManager","" + className);
            //root中注册的是分组信息 将分组信息加入仓库中
            try {
                IRouterListener iRouterListener = ((IRouterListener) Class.forName(className).getConstructor().newInstance());
                iRouterListener.register(routerMap);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    private Set<String> getClassName(String packageName) {
        Set<String> classNameList = new HashSet<>();
        try {
            //通过DexFile查找当前的APK中可执行文件
            DexFile df = new DexFile(application.getPackageCodePath());
            //获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
            Enumeration<String> enumeration = df.entries();
            //遍历
            while (enumeration.hasMoreElements()) {
                String className = enumeration.nextElement();
                //在当前所有可执行的类里面查找包含有该包名的所有类
                if (className.startsWith(packageName)) {
                    classNameList.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classNameList;
    }


}
