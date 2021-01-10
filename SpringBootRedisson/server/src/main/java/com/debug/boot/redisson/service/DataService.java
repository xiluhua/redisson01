package com.debug.boot.redisson.service;

import com.debug.boot.redisson.enums.Constant;
import com.google.common.collect.Lists;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.debug.boot.redisson.model.entity.Item;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 将线程中要执行的“批量插入数据”交给dataService
 **/
@Service
@Transactional
public class DataService {

    private static final Logger log= LoggerFactory.getLogger(DataService.class);

    @Resource
    private LogSaveImpl logSaveImpl;

    @Resource
    private RedissonClient redissonClient;

    public static void main(String[] args) {
     System.out.println(Math.random());
    }

    //批量插入数据
    public void insertBatchData(Set<String> set) throws Exception {
        LinkedList<Item> list= Lists.newLinkedList();

        if (set!=null && !set.isEmpty()){
            set.forEach(value -> {
                Item data=new Item();
//                data.setId(new Double(10000*Math.random()).intValue());
                data.setCode(value);
                data.setName(value.substring(0,10)+new Double(10000*Math.random()).intValue());
                data.setCreateTime(new Date());
                list.add(data);

            });
            //TODO：批量插入数据库
            logSaveImpl.saveBatch(list);

            //TODO:再入布隆过滤器-构造布隆过滤器实例
            RBloomFilter<String> rBloomFilter= redissonClient.getBloomFilter(Constant.RedisBloomItem);
            rBloomFilter.tryInit(100000000L,0.001);

            set.forEach(s -> rBloomFilter.add(s));
        }
    }

}






















