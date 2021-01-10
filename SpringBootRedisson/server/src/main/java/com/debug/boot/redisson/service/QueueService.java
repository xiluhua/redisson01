package com.debug.boot.redisson.service;

import com.debug.boot.redisson.dao.NoticeDaoRep;
import com.debug.boot.redisson.enums.Constant;
import com.debug.boot.redisson.model.entity.Notice;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.debug.boot.redisson.dao.NoticeDaoRep;
import com.debug.boot.redisson.model.entity.Notice;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 队列queue
 **/
@Service
public class QueueService {

    @Autowired
    private NoticeDaoRep noticeDaoRep;

    @Autowired
    private RedissonClient redisson;


    //创建公告
    public int createNotice(Notice notice) throws Exception{
        int res= noticeDaoRep.save(notice).getId();
        if (res>0){
//            RQueue<Notice> rQueue=redisson.getQueue(Constant.RedisQueueNotice);
//            //TODO:生产并塞入队列queue - 生产者的角度
//            rQueue.offer(notice);

            //TODO:堵塞队列 - 使用普通的任务队列时要手动防止并发，代码比较繁琐 -> 提供了线程安全的阻塞队列BlockingQueue
            RBlockingQueue<Notice> blockingQueue=redisson.getBlockingQueue(Constant.RedisQueueBlockingNotice);
            blockingQueue.offer(notice);

            return 1;
        }
        return -1;
    }


}





























