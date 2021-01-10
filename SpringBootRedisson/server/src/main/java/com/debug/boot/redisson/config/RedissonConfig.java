package com.debug.boot.redisson.config;
/**
 * Created by Administrator on 2020/2/6.
 */

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 配置用于操作Redisson 节点服务的客户端实例bean
 * @Author:debug (SteadyJack)
 * @Date: 2020/2/6 15:22
 **/
@Configuration
public class RedissonConfig {

    @Autowired
    private Environment env;

    //单节点的模式
    @Bean
    public RedissonClient redissonClient(){
        Config config=new Config();
        config.useSingleServer().setAddress(env.getProperty("redisson.url.single"));
        RedissonClient client=Redisson.create(config);

        System.out.println("单节点模式："+client.getConfig().toString());
        return client;
    }


    //集群的模式
//    @Bean
    public RedissonClient redissonClient00(){
        System.out.println(env.getProperty("redisson.url.cluster"));

        Config config=new Config();
        config.useClusterServers()
                //集群状态扫描间隔时间，单位是毫秒
                .setScanInterval(2000)
                //缺陷：硬编码
                //.addNodeAddress("redis://127.0.0.1:7100","redis://127.0.0.1:7101","redis://127.0.0.1:7200", "redis://127.0.0.1:7201",
                        //"redis://127.0.0.1:7300", "redis://127.0.0.1:7301")

                .addNodeAddress(env.getProperty("redisson.url.cluster").split(","))

                .setMasterConnectionMinimumIdleSize(10)
                .setMasterConnectionPoolSize(64)
                .setMasterConnectionMinimumIdleSize(10)
                .setMasterConnectionPoolSize(64)
                .setConnectTimeout(15000);

        RedissonClient redisson=Redisson.create(config);
        return redisson;
    }

}




























