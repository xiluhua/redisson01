package com.debug.boot.redisson.controller;/**
 * Created by Administrator on 2020/2/6.
 */

import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.model.entity.User;
import com.debug.boot.redisson.dao.UserDaoRep;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * 通用对象桶bucket
 **/
@RestController
@RequestMapping("bucket")
public class BucketController {

    private static final String BUCKET_KEY="Redisson:Bucket:User:";

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private UserDaoRep userDaoRep;

    // http://localhost:9101/bucket/user/add
    //添加
    @RequestMapping(value = "user/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse addUser(@RequestBody User user){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            //TODO:先插数据库~再入缓存
            int res=userDaoRep.save(user).getId();
            if (res>0){
                RBucket<User> rBucket= redissonClient.getBucket(BUCKET_KEY+user.getId());
                rBucket.set(user);
            }

            response.setData(user.getId());
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    // http://localhost:9101/bucket/user/info
    //获取详情
    @GetMapping("user/info")
    public BaseResponse getUser(@RequestParam Integer id){
        if (id==null || id<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {

            Optional<User> byId = userDaoRep.findById(id);
            System.out.println(byId.get());
            RBucket<User> rBucket= redissonClient.getBucket(BUCKET_KEY+id);
            response.setData(rBucket.get());
        }catch (Exception e){
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
            int res=userDaoRep.save(user).getId();
            RBucket<User> rBucket= redissonClient.getBucket(BUCKET_KEY+user.getId());
            rBucket.set(user);

        }catch (Exception e){
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
            RBucket<User> rBucket= redissonClient.getBucket(BUCKET_KEY+id);
            rBucket.delete();
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}



































