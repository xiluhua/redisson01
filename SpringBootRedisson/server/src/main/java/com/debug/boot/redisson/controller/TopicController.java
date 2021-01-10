package com.debug.boot.redisson.controller;

import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 话题Topic-订阅分发 - 异步实时发送邮件
 **/
@RestController
@RequestMapping("topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    // http://localhost:9101/topic/email
    // 给指定的用户发送邮件
    @RequestMapping(value = "email",method = RequestMethod.POST)
    public BaseResponse sendEmail(@RequestParam Integer userId){
        if (userId==null || userId<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            topicService.sendTopicEmail(userId);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}





































