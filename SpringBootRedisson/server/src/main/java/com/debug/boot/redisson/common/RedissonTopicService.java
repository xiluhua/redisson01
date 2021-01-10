package com.debug.boot.redisson.common;/**
 * Created by Administrator on 2020/2/7.
 */

import com.debug.boot.redisson.enums.Constant;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * 作用：可以在项目启动/运行的过程时时刻刻的运行着
 **/
@Service
public class RedissonTopicService implements ApplicationRunner,Ordered {

    private static final Logger log= LoggerFactory.getLogger(RedissonTopicService.class);

    @Autowired
    private Environment env;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private MailService mailService;


    //TODO:在项目跑的过程中，不间断地执行一些我们自定义的服务逻辑
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("--在项目跑的过程中，不间断地执行一些我们自定义的服务逻辑 order-1 --");

        this.listenTopic();
    }

    @Override
    public int getOrder() {
        return 1;
    }

    //监听对象实例-话题 里头的消息
    private void listenTopic(){
        try {
//            RTopic<String> rTopic=redissonClient.getTopic(Constant.RedissonTopicEmail);
//            rTopic.addListener((charSequence, s) -> {
//                log.info("----监听主题Topic，消息为：{}",s);
//
//            });

//                mailService.sendSimpleEmail(env.getProperty("mail.topic.subject"),env.getProperty("mail.topic.content"),s);
        }catch (Exception e){
            log.error("----监听对象实例-话题 里头的消息-发生异常：",e.fillInStackTrace());
        }
    }
}































