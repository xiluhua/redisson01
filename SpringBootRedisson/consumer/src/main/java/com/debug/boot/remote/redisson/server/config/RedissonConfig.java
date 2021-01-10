package com.debug.boot.remote.redisson.server.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * redisson配置
 **/
@Configuration
public class RedissonConfig {

    @Autowired
    private Environment env;

//    @Bean
//    public RedissonClient redissonClient(){
//        Config config = new Config();
//        config.useClusterServers()
//                .setScanInterval(2000)
//                .addNodeAddress(env.getProperty("redisson.url.cluster").split(","))
//
//                .setMasterConnectionMinimumIdleSize(10)
//                .setMasterConnectionPoolSize(64)
//                .setSlaveConnectionMinimumIdleSize(10)
//                .setSlaveConnectionPoolSize(64)
//                .setConnectTimeout(15000);
//
//        RedissonClient redisson = Redisson.create(config);
//        System.out.println(redisson.getConfig());
//
//        /*//创建配置
//        Config config = new Config();
//        //指定使用集群部署方式
//        config.useClusterServers()
//                // 集群状态扫描间隔时间，单位是毫秒
//                .setScanInterval(2000)
//                //cluster方式至少6个节点(3主3从，3主做sharding，3从用来保证主宕机后可以高可用)
//                .addNodeAddress("redis://127.0.0.1:7100" )
//                .addNodeAddress("redis://127.0.0.1:7101")
//                .addNodeAddress("redis://127.0.0.1:7200")
//                .addNodeAddress("redis://127.0.0.1:7201")
//                .addNodeAddress("redis://127.0.0.1:7300")
//                .addNodeAddress("redis://127.0.0.1:7301")
//
//                .setMasterConnectionMinimumIdleSize(10)
//                .setMasterConnectionPoolSize(64)
//                .setSlaveConnectionMinimumIdleSize(10)
//                .setSlaveConnectionPoolSize(64)
//                .setConnectTimeout(15000);
//
//        //创建客户端(发现这一非常耗时，基本在2秒-4秒左右)
//        RedissonClient redisson = Redisson.create(config);*/
//        return redisson;
//    }

    @Bean
    public RedissonClient redissonClient(){
        Config config=new Config();
        config.useSingleServer().setAddress(env.getProperty("redisson.url.single"));
        RedissonClient client= Redisson.create(config);
        System.out.println(client.getConfig().toString());
        return client;
    }
}