package com.mvp.cn.annotation;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * Created by iqiao on 2020-01-09 11:04
 * Desc: 改工厂类是负责通过传入的type来执行不同的SwitchRunTaskManager中注解对应的方法的
 */

public class RunTaskFactory {

    public static void buildRunTask(int type, String content) {
        try {
            //获取到类对象
            Class<SwitchRunTaskManager> manager = (Class<SwitchRunTaskManager>) Class.forName("com.zq.project.test.SwitchRunTaskManager");
            //获取该类下的所有方法
            Method[] methods = manager.getMethods();
            //遍历所有方法
            for (Method method : methods) {
                boolean flag = method.isAnnotationPresent(SwitchType.class);
                //判断该方法上有无SwitchType注解
                if (flag) {
                    SwitchType type1 = method.getAnnotation(SwitchType.class);
                    if (type==type1.value()){
                        //有注解，并且判断通过则执行该方法
                        method.invoke(manager.newInstance(),content);
                    }else{
                        Log.e("test", "使用了注解，但条件不符合");
                    }
                } else {
                    Log.e("test", "当前没有使用该注解的方法");
                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
