package com.debug.boot.redisson.controller;

import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.service.RateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 限流器controller ~ 短信发送 ~ 限流
 **/
@RestController
@RequestMapping("rate/limiter")
public class RateLimiterController {

    @Autowired
    private RateLimiterService rateLimiterService;

    //
    //发送短信的接口
    @GetMapping("msg/send")
    public BaseResponse sendMsg(@RequestParam String phone){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            rateLimiterService.sendMsg(phone);
        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }


}









































