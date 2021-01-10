package com.debug.boot;/**
 * Created by Administrator on 2020/2/5.
 */

import org.redisson.api.RedissonClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * spring boot应用的启动入口类
 * @Author:debug (SteadyJack)
 * @Link: weixin-> debug0868 qq-> 1948831260
 * @Date: 2020/2/5 22:48
 **/
@SpringBootApplication
@ImportResource(value = {"classpath:spring/spring-jdbc.xml"})
@EnableScheduling
@EnableAsync
public class MainApplication extends SpringBootServletInitializer{
    public static ApplicationContext ac = null;
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MainApplication.class);
    }

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(MainApplication.class,args);
        MainApplication.ac = ac;
        RedissonClient redissonClient = ac.getBean(RedissonClient.class);
        System.out.println("RedissonClient: "+redissonClient);
    }
}




























