package com.debug.boot.redisson.service;

import com.debug.boot.redisson.common.MailService;
import com.debug.boot.redisson.dao.MailInfoDaoRep;
import com.debug.boot.redisson.enums.Constant;
import com.debug.boot.redisson.model.entity.MailInfo;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import org.springframework.transaction.annotation.Transactional;

/**
 *
 **/
@Service
public class MapCacheService {

    private static final Logger log= LoggerFactory.getLogger(MapCacheService.class);

    @Autowired
    private MailInfoDaoRep mailInfoDaoRep;

    @Autowired
    private MailService mailService;

    @Autowired
    private RedissonClient redissonClient;

    //创建一封邮件(当is_delay=1时，意味着发送的时间将大于当前时间 - 这个可以由前端控制)
    public void createMail(MailInfo info) throws Exception{
        //TODO:计算出当前这封邮件的待发送的时间 与 当前这一时刻的时间 的距离 = ttl : 限当天，定时发送的时间 12:00，当前的时刻：11:00 意味着
        //TODO:邮件即将在1个小时后发送，ttl=1h
        long diffTime=0;
        if (info.getIsDelay().intValue()==1 && StringUtils.isNotBlank(info.getSendDateTime())){
            info.setSendTime(Constant.DATE_TIME_FORMAT.parse(info.getSendDateTime()));

            diffTime=info.getSendTime().getTime() - System.currentTimeMillis();
            if (diffTime<=0){
                throw new RuntimeException("邮件待发送时间应当大于当前时间");
            }
        }
        log.info("----准备创建一封邮件：{}",info);

        //插入数据库 - 发送邮件有两种模式（即时；定时）
        int res= mailInfoDaoRep.save(info).getId();
        if (res>0){
            if (diffTime>0){
                //TODO:元素的淘汰策略~ 带有元素淘汰（Eviction）机制的映射类允许针对一个映射中每个元素单独设定 有效时间 和 最长闲置时间
                //TODO:无形中提供了一种 “单独失效某个元素Value，而不是整个Key” 的机制 -
                RMapCache<Integer,String> rMapCache= redissonClient.getMapCache(Constant.RedisMapCacheMail);
                rMapCache.put(info.getId(),info.getTos(),diffTime, TimeUnit.MILLISECONDS);

                log.info("---定时发送邮件---");
            }else{
                mailService.sendSimpleEmail(info.getSubject(),info.getContent(),info.getTos());
            }
        }
    }

    /**
     * 失效待发送的邮件 ~ 可能是某种原因不想发送出去了 ~ 针对isDelay=1的，即需要延时发送的那些邮件
     * @param id
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void invalidateEmail(final Integer id) throws Exception{
        MailInfo entity= mailInfoDaoRep.findById(id).get();
        if (entity==null || entity.getIsActive().intValue()==0 || entity.getIsDelay().intValue()==0){
            throw new RuntimeException("待失效的邮件记录不合法或者不存在！");
        }

        //TODO:更新DB
        entity.setIsActive(0);
        int res= mailInfoDaoRep.save(entity).getId();
        if (res>0){
            //TODO:同时去缓存里头移除掉mapCache对应的key
            RMapCache<Integer,String> rMapCache= redissonClient.getMapCache(Constant.RedisMapCacheMail);
            rMapCache.fastRemove(id);
            System.out.println();
        }
    }

}
































