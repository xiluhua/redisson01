package com.debug.boot.remote.redisson.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * spring boot应用的启动入口类
 **/
@SpringBootApplication
@ImportResource(value = {"classpath:spring/spring-jdbc.xml"})
@EnableScheduling
@EnableAsync
public class MainConsumerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MainConsumerApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MainConsumerApplication.class,args);
    }
}