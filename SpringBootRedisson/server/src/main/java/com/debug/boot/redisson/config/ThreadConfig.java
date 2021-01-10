package com.debug.boot.redisson.config;/**
 * Created by Administrator on 2020/2/14.
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池的配置
 * @Author:debug (SteadyJack)
 * @Link: weixin-> debug0868 qq-> 1948831260
 * @Date: 2020/2/14 15:50
 **/
@Configuration
public class ThreadConfig {


    @Bean("threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setKeepAliveSeconds(10);
        executor.setQueueCapacity(8);

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }


}
















