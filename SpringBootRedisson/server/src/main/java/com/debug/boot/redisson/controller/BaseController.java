package com.debug.boot.redisson.controller;

import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.Set;
import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 基本controller
 **/
@RestController
@RequestMapping("base")
public class BaseController {

    @Autowired
    private RedissonClient redisson;

    // http://localhost:9101/redisson/base/keys
    @RequestMapping(value = "keys",method = RequestMethod.GET)
    public BaseResponse getKeys(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap= Maps.newHashMap();
        try {
            RKeys keys = redisson.getKeys();
            Iterable<String> iterable=keys.getKeys();

            Set<String> set= Sets.newHashSet();
            iterable.forEach(s -> set.add(s));

            resMap.put("randomKey",keys.randomKey());
            resMap.put("keys",set);

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        response.setData(resMap);
        return response;
    }


}




































