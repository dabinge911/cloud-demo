package com.server.cloud.config;

import com.server.cloud.sdk.sdk.NameTheadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Description 配置定时任务并行执行线程池
 * @Auther sea-hibn
 * @Date 2019-07-16
 */
@Configuration
@EnableScheduling
public class ScheduledConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(setTaskExecutors());
    }


    @Bean
    public Executor setTaskExecutors(){
        return Executors.newScheduledThreadPool(2,new NameTheadFactory("feign-task-pool"));
    }
}
