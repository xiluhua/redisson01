package com.debug.boot.redisson.service;/**
 * Created by Administrator on 2020/2/7.
 */

import com.debug.boot.MainApplication;
import com.debug.boot.redisson.dao.ItemDaoRep;
import com.debug.boot.redisson.enums.Constant;
import com.debug.boot.redisson.model.entity.Item;
import com.debug.boot.redisson.thread.ThreadDto;
import com.google.common.collect.Lists;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 布隆过滤器-service
 * @Author:debug (SteadyJack)
 * @Link: weixin-> debug0868 qq-> 1948831260
 * @Date: 2020/2/7 21:49
 **/
@Service
public class BloomService {

    private static final Logger log= LoggerFactory.getLogger(BloomService.class);

    @Autowired
    private RedissonClient redisson;
    @Autowired
    private ItemDaoRep itemDaoRep;
    @Autowired
    private DataService dataService;

    //添加商品
    @Transactional(rollbackFor = Exception.class)
    public Integer addItem(Item item) throws Exception{
        Integer id=-1;

        //TODO:商品编码是否已存在
        RBloomFilter<String> rBloomFilter=redisson.getBloomFilter(Constant.RedisBloomItem);
        rBloomFilter.tryInit(100000000L, 0.001);
        if (rBloomFilter.contains(item.getCode())){
            throw new RuntimeException("该商品编码已存在！");
        }

        //TODO:插入数据库
        item.setCreateTime(new Date());
        int res=itemDaoRep.save(item).getId();
        if (res>0){
            id=item.getId();
            //TODO:入布隆过滤器
            rBloomFilter.add(item.getCode());
        }
        return id;
    }

    // jpa 得不到 transaction
    //多线程~生成大批量的数据并插入数据库-入布隆过滤器
    @Deprecated
    public void genDataByThreads() throws Exception{
        //juc的executors
        ExecutorService executorService=Executors.newFixedThreadPool(10);
        List<ThreadDto> list= Lists.newLinkedList();
        for (int i=0;i<10;i++){
            ThreadDto threadDto = MainApplication.ac.getBean(ThreadDto.class);
            list.add(threadDto);
        }

        executorService.invokeAll(list);
    }

    //检查数据是否在大数集合中存在
    public Boolean existData(final String dataCode) throws Exception{
        RBloomFilter<String> rBloomFilter=redisson.getBloomFilter(Constant.RedisBloomItem);
        rBloomFilter.tryInit(100000000L,0.001);
        return rBloomFilter.contains(dataCode);
    }

}























