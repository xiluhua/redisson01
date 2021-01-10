package com.debug.boot.redisson.service;

import com.debug.boot.redisson.common.OkHttpService;
import com.debug.boot.redisson.enums.Constant;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

/**
 * 限流器service - 短信发送
 **/
@Service
public class RateLimiterService {

    private static final Logger log= LoggerFactory.getLogger(RateLimiterService.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private OkHttpService okHttpService;

    @Autowired
    private Environment env;

    /**
     * 短信发送~限流
     * @param phone 对同一个手机号限制 xx 时间内的发送次数 = 10s 内
     */
    public void sendMsg(final String phone) throws Exception{
        if (StringUtils.isNotBlank(phone)){
            RRateLimiter rateLimiter= redissonClient.getRateLimiter(Constant.RedisRateLimiter+phone);
            //初始化  最大流速 = 每 xx 秒钟产生1个令牌(同个手机号 xx 秒内只能发一次) ~ 每10秒钟产生1个令牌 -
            //真实应用中还得借助缓存/数据库记录发送的次数、ip限制、白名单、黑名单限制等
            rateLimiter.trySetRate(RateType.OVERALL,1,10, RateIntervalUnit.SECONDS);
            if (rateLimiter.tryAcquire(1)) {
                log.info("----发送短信，手机号：{}",phone);

                //TODO:真正的调用网信通的发送短信接口
                String url=env.getProperty("sms.url.send");
                url=String.format(url,phone);
                String result=okHttpService.get(url,null);

                log.info("----发送短信后的结果：{}",result);
            }else {
                log.info(phone+"： can not send msg");
            }
        }
    }

}






































