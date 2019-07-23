package com.server.cloud.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description 定时任务
 * @Auther sea-hibn
 * @Date 2019-07-16
 */
@Slf4j
@Component
public class ScheduleTest {

    /**
     * 每隔1分钟执行一次
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void test1(){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sf.format(new Date()));
        log.info("test1 : {}",sf.format(new Date()));
    }

    /**
     * 启动后10秒执行一次，然后每隔1分钟执行一次
     */
    @Scheduled(initialDelay = 10000l,fixedRate = 1000*60l)
    public void test2(){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("test2 : {}",sf.format(new Date()));
    }
}
