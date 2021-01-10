package com.debug.boot.redisson.controller;

import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.dao.UserDaoRep;
import com.debug.boot.redisson.enums.Constant;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.dao.UserDaoRep;
import com.debug.boot.redisson.enums.Constant;
import com.debug.boot.redisson.model.entity.User;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存映射Map - RLocalCachedMap
 **/
@RestController
@RequestMapping("map/cache/local")
public class MapLocalCacheController {

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private UserDaoRep userDaoRep;


    //本地缓存映射的选项配置
    private LocalCachedMapOptions options;

    @PostConstruct
    public void init(){
        options = LocalCachedMapOptions.defaults()
                // 用于淘汰清除本地缓存内的元素
                // 共有以下几种选择:
                // LFU - 统计元素的使用频率，淘汰用得最少（最不常用）的。
                // LRU - 按元素使用时间排序比较，淘汰最早（最久远）的。
                // SOFT - 元素用Java的WeakReference来保存，缓存元素通过GC过程清除。
                // WEAK - 元素用Java的SoftReference来保存, 缓存元素通过GC过程清除。
                // NONE - 永不淘汰清除缓存元素。
                .evictionPolicy(LocalCachedMapOptions.EvictionPolicy.NONE)
                // 如果缓存容量值为0表示不限制本地缓存容量大小
                .cacheSize(1000)
                // 以下选项适用于断线原因造成了未收到本地缓存更新消息的情况。
                // 断线重连的策略有以下几种：
                // CLEAR - 如果断线一段时间以后则在重新建立连接以后清空本地缓存
                // LOAD - 在服务端保存一份10分钟的作废日志
                //        如果10分钟内重新建立连接，则按照作废日志内的记录清空本地缓存的元素
                //        如果断线时间超过了这个时间，则将清空本地缓存中所有的内容
                // NONE - 默认值。断线重连时不做处理。
                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.NONE)
                // 以下选项适用于不同本地缓存之间相互保持同步的情况
                // 缓存同步策略有以下几种：
                // INVALIDATE - 默认值。当本地缓存映射的某条元素发生变动时，同时驱逐所有相同本地缓存映射内的该元素
                // UPDATE - 当本地缓存映射的某条元素发生变动时，同时更新所有相同本地缓存映射内的该元素
                // NONE - 不做任何同步处理
                .syncStrategy(LocalCachedMapOptions.SyncStrategy.INVALIDATE)
                // 每个Map本地缓存里元素的有效时间，默认毫秒为单位
                .timeToLive(10000)
                // 或者
                .timeToLive(10, TimeUnit.SECONDS)
                // 每个Map本地缓存里元素的最长闲置时间，默认毫秒为单位
                .maxIdle(10000)
                // 或者
                .maxIdle(10, TimeUnit.SECONDS);
    }


    //添加
    @RequestMapping(value = "user/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse addUser(@RequestBody User user){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            //TODO:先插数据库~再入缓存
            int res= userDaoRep.save(user).getId();
            if (res>0){
                RLocalCachedMap<Integer,User> rMap =redisson.getLocalCachedMap(Constant.RedisMapLocalCacheUser,options);
                rMap.put(user.getId(),user);
            }

            response.setData(user.getId());
        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    //获取详情
    @GetMapping("user/info")
    public BaseResponse getUser(@RequestParam Integer id){
        if (id==null || id<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            RLocalCachedMap<Integer,User> rMap =redisson.getLocalCachedMap(Constant.RedisMapLocalCacheUser,options);
            response.setData(rMap.get(id));

        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    //更新
    @RequestMapping(value = "user/update",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse updateUser(@RequestBody User user){
        if (user.getId()==null || user.getId()<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            //TODO:先更新数据库 ~ 再更新入缓存
            int res= userDaoRep.save(user).getId();
            if (res>0){
                RLocalCachedMap<Integer,User> rMap =redisson.getLocalCachedMap(Constant.RedisMapLocalCacheUser,options);
                rMap.put(user.getId(),user);

                //rMap.fastPut(user.getId(),user);
            }
        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    //删除
    @RequestMapping(value = "user/delete",method = RequestMethod.POST)
    public BaseResponse deleteUser(@RequestParam Integer id){
        if (id==null || id<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            //TODO:先删除数据库 ~ 再删缓存
            userDaoRep.deleteById(id);
            RLocalCachedMap<Integer,User> rMap =redisson.getLocalCachedMap(Constant.RedisMapLocalCacheUser,options);
            rMap.remove(id);

            //rMap.fastRemove(id);

        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }



}




































