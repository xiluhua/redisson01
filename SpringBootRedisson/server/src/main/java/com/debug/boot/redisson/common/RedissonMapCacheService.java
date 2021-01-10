package com.debug.boot.redisson.common;

import com.debug.boot.redisson.dao.MailInfoDaoRep;
import com.debug.boot.redisson.enums.Constant;
import com.debug.boot.redisson.model.entity.MailInfo;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryEvent;
import org.redisson.api.map.event.EntryExpiredListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;
import com.debug.boot.redisson.model.entity.MailInfo;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryEvent;
import org.redisson.api.map.event.EntryExpiredListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

/**
 * redisson中间件通用的监听mapCache里头时时刻刻可能会失效的元素
 * @Author:debug (SteadyJack)
 * @Link: weixin-> debug0868 qq-> 1948831260
 * @Date: 2020/2/13 11:36
 **/
@Service
public class RedissonMapCacheService implements ApplicationRunner,Ordered {

    private static final Logger log= LoggerFactory.getLogger(RedissonMapCacheService.class);

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private MailService mailService;

    @Autowired
    private MailInfoDaoRep mailInfoDaoRep;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("--在项目跑的过程中，不间断地执行一些我们自定义的服务逻辑 order-2 --");

//        this.sendDelayEmail();
    }

    @Override
    public int getOrder() {
        return 2;
    }

    //定时/延时发送邮件
    private void sendDelayEmail(){
        try {
            RMapCache<Integer,String> rMapCache=redisson.getMapCache(Constant.RedisMapCacheMail);

            rMapCache.addListener(new EntryExpiredListener<Integer, String>() {
                @Override
                public void onExpired(EntryEvent<Integer, String> event) {
                    log.info("---定时/延时发送邮件,监听到消息：key={},value={}",event.getKey(),event.getValue());

                    //TODO:真正的发送邮件
                    MailInfo info=mailInfoDaoRep.findById(event.getKey()).get();
                    if (info!=null && StringUtils.isNotBlank(info.getTos())){
                        mailService.sendSimpleEmail(info.getSubject(),info.getContent(), StringUtils.split(info.getTos(),","));
                    }
                }
            });
        }catch (Exception e){
            log.error("redisson中间件通用的监听mapCache里头时时刻刻可能会失效的元素-发生异常：",e.fillInStackTrace());
        }
    }
}


































