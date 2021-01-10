package com.debug.boot.redisson.controller;

import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.dao.UserDaoRep;
import com.debug.boot.redisson.enums.Constant;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.dao.UserDaoRep;
import com.debug.boot.redisson.model.entity.User;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 映射Map - RMap
 **/
@RestController
@RequestMapping("map")
public class MapController {

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private UserDaoRep userDaoRep;

    //添加
    @RequestMapping(value = "user/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse addUser(@RequestBody User user){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            //TODO:先插数据库~再入缓存
            int res=userDaoRep.save(user).getId();
            if (res>0){
                //TODO:相对于通用的对象桶实例 ~ 最大的优点，减少了大量的Key
                RMap<Integer,User> rMap=redisson.getMap(Constant.RedisMapUser);
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
            RMap<Integer,User> rMap=redisson.getMap(Constant.RedisMapUser);
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
            int res=userDaoRep.save(user).getId();
            if (res>0){
                RMap<Integer,User> rMap=redisson.getMap(Constant.RedisMapUser);
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
            RMap<Integer,User> rMap=redisson.getMap(Constant.RedisMapUser);
            rMap.remove(id);
                //rMap.fastRemove(id);

        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }



}




































