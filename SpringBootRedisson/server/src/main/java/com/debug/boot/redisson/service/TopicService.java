package com.debug.boot.redisson.service;

import com.debug.boot.redisson.dao.UserDaoRep;
import com.debug.boot.redisson.enums.Constant;
import com.debug.boot.redisson.model.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.debug.boot.redisson.dao.UserDaoRep;
import com.debug.boot.redisson.enums.Constant;
import com.debug.boot.redisson.model.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 话题Topic-订阅分发
 * 注意：不会保存或持久化在 redis
 **/
@Service
public class TopicService {

    private static final Logger log= LoggerFactory.getLogger(TopicService.class);

    @Autowired
    private UserDaoRep userDaoRep;

    @Autowired
    private RedissonClient redissonClient;


    //将邮件消息塞入话题Topic对象实例
    public void sendTopicEmail(final Integer userId) throws Exception{
        User user=userDaoRep.findById(userId).get();
        if (user!=null && StringUtils.isNotBlank(user.getEmail())){
            //TODO:消息的生产端 ~ 将接受邮件信息的用户邮箱塞入 Topic对象实例去

            RTopic<String> rTopic = redissonClient.getTopic(Constant.RedissonTopicEmail);
            rTopic.publish("123");
            rTopic.publishAsync("123");

        }
    }

}






























