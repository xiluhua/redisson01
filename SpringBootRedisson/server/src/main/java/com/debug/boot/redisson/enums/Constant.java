package com.debug.boot.redisson.enums;

import java.text.SimpleDateFormat;

/**
 * 常量配置
 */
public class Constant {

    public static final String RedissonTopicEmail="redisson:topic:email";

    public static final String RedisBloomItem="Redisson:Bloom:Item";

    public static final String RedisRateLimiter="Redisson:Rate:Limiter:V2";

    public static final String RedisMapUser="Redisson:Map:User:V2";

    public static final String RedisMapLocalCacheUser="Redisson:Map:Local:Cache:User:V2";

    public static final SimpleDateFormat DATE_TIME_FORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String RedisMapCacheMail="Redisson:Map:Cache:Mail:V2";

    public static final String RedisMultiMapConfig="Redisson:Map:Multi:Config:V2";

    public static final String RedisSetProblem="Redisson:Set:Problem:V2";

    public static final String RedisSetCachePhoneCode="Redisson:Set:Cache:Phone:Code:V2";

    public static final String RedisScoredSortedSetPhoneFare="Redisson:Scored:Sorted:Set:Phone:Fare:V2";

    public static final String RedisQueueNotice="Redisson:Queue:Notice:V2";

    public static final String RedisQueueBlockingNotice="Redisson:Queue:Blocking:Notice:V2";

    public static final String RedisQueueDeadUserOrder="Redisson:Queue:Dead:User:Order:V2";

    public static final String RedisProductKillRecord="Redisson:Product:Kill:Record:V2";

    public static final String RedisLockKillProduct="Redisson:Lock:Kill:Product:V2:";

    public static final String RedisKillSuccessEmail="Redisson:Kill:Success:Email:V2";

    public static final String RedisQueueDeadProductKill="Redisson:Queue:Dead:Product:Kill:V2";


}
