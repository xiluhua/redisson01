package com.debug.boot.redisson.controller;

import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.model.entity.Notice;
import com.debug.boot.redisson.service.QueueService;
import com.debug.boot.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.model.entity.Notice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 队列Queue
 **/
@RestController
@RequestMapping("queue/notice")
public class QueueController {

    @Autowired
    private QueueService queueService;

    // http://localhost:9101/queue/notice/create
    //添加通告
    @RequestMapping("create")
    public BaseResponse create(@RequestBody @Validated Notice notice, BindingResult result){
        String checkRes= ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(checkRes)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),checkRes);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(queueService.createNotice(notice));
        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }


}
































