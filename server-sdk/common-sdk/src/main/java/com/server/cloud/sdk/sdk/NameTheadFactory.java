package com.server.cloud.sdk.sdk;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 自定义线程池命名(自定义线程命名可以快速找出异常处理代码)
 * @Auther sea-hibn
 * @Date 2019-07-16
 */
public class NameTheadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public NameTheadFactory(){
        this("default-name-pool");
    }

    public NameTheadFactory(String name){
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        this.namePrefix = name +
                poolNumber.getAndIncrement();
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + "-thread-"+threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
