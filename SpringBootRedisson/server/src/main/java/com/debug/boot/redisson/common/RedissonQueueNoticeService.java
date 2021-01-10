package com.debug.boot.redisson.common;

import com.debug.boot.redisson.dao.NoticeDaoRep;
import com.debug.boot.redisson.enums.Constant;
import com.debug.boot.redisson.model.entity.Notice;
import org.redisson.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.debug.boot.redisson.model.entity.Notice;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.beans.Transient;

/**
 * 队列queue-通知公告-消费者
 **/
@Component
public class RedissonQueueNoticeService {

    private static final Logger log= LoggerFactory.getLogger(RedissonQueueNoticeService.class);

    private static final String UserEmails="linsenzhong@126.com,1974544863@qq.com";

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private MailService  mailService;
    @Autowired
    private NoticeDaoRep noticeDaoRep;

    //http://www.fightjava.com/web/index/blog/article/39
    //处理队列中的通知公告
    @Scheduled(cron = "0/1 * * * * ?")
    //@Async("threadPoolTaskExecutor")
    //@Transactional // 不行，拿不到 transaction
    public void manageNotice(){
        try {
            //TODO:无界普通队列
//            RQueue<Notice> rQueue=redissonClient.getQueue(Constant.RedisQueueNotice);
//            if (rQueue!=null && !rQueue.isEmpty()){
//                Notice notice=rQueue.poll();
//
//                // 不行，拿不到 transaction
//                notice.setContent("123");
//                noticeDaoRep.save(notice);
//                //给商户发送消息
//                //mailService.sendSimpleEmail(notice.getTitle(),notice.getContent(),StringUtils.split(UserEmails,","));
//                log.info("----监听到消息：{}",notice);
//            }


//            //TODO:堵塞队列 - 使用普通的任务队列时要手动防止并发，代码比较繁琐 -> 提供了线程安全的阻塞队列BlockingQueue
            RBlockingQueue<Notice> rQueue=redissonClient.getBlockingQueue(Constant.RedisQueueBlockingNotice);
            if (rQueue!=null && !rQueue.isEmpty()){
                Notice notice=rQueue.poll();

                //给商户发送消息
                //mailService.sendSimpleEmail(notice.getTitle(),notice.getContent(), StringUtils.split(UserEmails,","));
                log.info("----监听到消息：{}",notice);
            }


        }catch (Exception e){
            e.printStackTrace();
            log.error("处理队列中的通知公告-发生异常: ",e.fillInStackTrace());
        }

    }


}





















