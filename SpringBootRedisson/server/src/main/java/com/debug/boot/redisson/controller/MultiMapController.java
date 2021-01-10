package com.debug.boot.redisson.controller;

import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.model.entity.SysConfig;
import com.debug.boot.redisson.service.MultiMapService;
import com.debug.boot.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.model.entity.SysConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * 多值映射multiMap
 **/
@RestController
@RequestMapping("map/multi")
public class MultiMapController {

    @Autowired
    private MultiMapService multiMapService;

    // http://localhost:9101/map/multi/config/add
    //TODO:录入数据字典
    @RequestMapping(value = "config/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse addConfig(@RequestBody @Validated SysConfig sysConfig, BindingResult result){
        String checkRes= ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(checkRes)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),checkRes);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(multiMapService.insertConfig(sysConfig));
        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    // http://localhost:9101/map/multi/config/get
    //TODO:获取数据字典选项列表 - 根据大类type来获取
    @RequestMapping(value = "config/get",method = RequestMethod.GET)
    public BaseResponse getConfig(@RequestParam String typeCode){
        if (StringUtils.isBlank(typeCode)){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            Collection<SysConfig> collection = multiMapService.getConfigByType(typeCode);
            response.setData(collection);
        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    // http://localhost:9101/map/multi/config/delete
    //TODO:删除选项列表
    @RequestMapping(value = "config/delete",method = RequestMethod.POST)
    public BaseResponse deleteConfig(@RequestParam Integer id){
        if (id==null || id<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(multiMapService.deleteConfig(id));
        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }


}

































