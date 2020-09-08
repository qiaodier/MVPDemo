package com.mvp.cn.http.framework;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by iqiao on 2020-03-04 16:17
 * Desc:
 *
 * @author iqiao
 */
public class RequestManager {

    /**
     * 任务队列
     */
    private LinkedBlockingDeque<Runnable> tasks = new LinkedBlockingDeque<>();
    /**
     * 线程池任务
     */
    private ThreadPoolExecutor threadPoolExecutor;
    /**
     * 添加任务
     *
     * @param runnable
     */
    public void addTask(Runnable runnable) {
        tasks.add(runnable);
    }

    /**
     * 构造器
     */
    public RequestManager() {
        threadPoolExecutor = new ThreadPoolExecutor(3, 10, 10, TimeUnit.SECONDS, tasks, (Runnable runnable, ThreadPoolExecutor executor) -> {
            tasks.add(runnable);
        });
        threadPoolExecutor.execute(runnable);
    }
    /**
     * 具体执行任务
     */
    private Runnable runnable = () -> {
        while (true) {
            try {
                Runnable runnable1 = tasks.take();
                threadPoolExecutor.execute(runnable1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


    /**
     * 单例
     *
     * @return
     */

    public static RequestManager getInstance() {
        return InnerClass.INSTANCES;
    }

    private static class InnerClass {
        private static final RequestManager INSTANCES = new RequestManager();
    }
}
