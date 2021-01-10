package com.debug.boot.redisson.service.remote;/**
 * Created by Administrator on 2020/2/14.
 */

import com.debug.boot.redisson.api.service.IRemoteProductService;
import org.redisson.api.RRemoteService;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动成功之后 ~ 初始化做一些事情 ~ 将开发好的生产者接口服务注册到redisson，充当服务端远程服务
 **/
@Component
public class RemoteServiceInit implements CommandLineRunner {

    private static final Logger log= LoggerFactory.getLogger(RemoteServiceInit.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private IRemoteProductService remoteProductService;


    @Override
    public void run(String... strings) throws Exception {
        log.info("----项目启动成功之后 ~ 注册服务端远程服务----");
        RRemoteService remoteService=redissonClient.getRemoteService();
        remoteService.register(IRemoteProductService.class,remoteProductService,100);


        /*RRemoteService remoteService = redisson.getRemoteService();
        SomeServiceImpl someServiceImpl = new SomeServiceImpl();

        //在调用远程方法以前，应该首先注册远程服务
        //只注册了一个服务端工作者实例，只能同时执行一个并发调用
        remoteService.register(SomeServiceInterface.class, someServiceImpl);

        //注册了12个服务端工作者实例，可以同时执行12个并发调用
        remoteService.register(SomeServiceInterface.class, someServiceImpl, 12);*/

    }
}




















































