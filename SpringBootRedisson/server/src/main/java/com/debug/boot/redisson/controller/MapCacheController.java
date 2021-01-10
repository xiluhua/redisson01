package com.debug.boot.redisson.controller;

import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.model.entity.MailInfo;
import com.debug.boot.redisson.service.MapCacheService;
import com.debug.boot.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.model.entity.MailInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 映射缓存mapCache ~ controller
 **/
@RestController
@RequestMapping("map/cache")
public class MapCacheController {

    @Autowired
    private MapCacheService mapCacheService;

    //创建一封邮件并发送 ~ 适用于即时与定时邮件的发送
    @RequestMapping(value = "email/create",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse createEmail(@RequestBody @Validated MailInfo info, BindingResult result){
        String checkRes= ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(checkRes)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),checkRes);
        }
        if (info.getIsDelay().intValue()==1 && StringUtils.isBlank(info.getSendDateTime())){
            return new BaseResponse(StatusCode.MailInfoSendDateTimeInvalidated);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            mapCacheService.createMail(info);
        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }


    //TODO:取消待发送邮件的定时发送
    @RequestMapping(value = "email/invalidate",method = RequestMethod.POST)
    public BaseResponse invalidateEmail(@RequestParam Integer id){
        if (id==null || id<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            mapCacheService.invalidateEmail(id);

        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
