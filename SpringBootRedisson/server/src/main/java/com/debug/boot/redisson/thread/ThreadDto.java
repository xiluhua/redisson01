package com.debug.boot.redisson.thread;

import cn.hutool.core.lang.Snowflake;
import com.debug.boot.MainApplication;
import com.debug.boot.redisson.service.DataService;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.concurrent.Callable;

import cn.hutool.core.lang.Snowflake;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * 每个线程要执行的逻辑所在之处
 **/
@Scope(value="prototype")
@Service
public class ThreadDto implements Callable<Boolean> {

    //雪花算法-分布式全局唯一id生成工具
    public static final Snowflake SNOWFLAKE=new Snowflake(3,2);


    //线程执行逻辑
    @Override
    public Boolean call() throws Exception {
        //TODO:每个线程生成1w个随机数
        int i=0;
        Set<String> set= Sets.newHashSet();
        for (;i<=10;i++){
            set.add(SNOWFLAKE.nextIdStr());
        }
        DataService dataService = MainApplication.ac.getBean(DataService.class);

        dataService.insertBatchData(set);
        return true;
    }
}