package com.debug.boot.redisson.controller;

import com.debug.boot.MainApplication;
import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.service.BloomService;
import com.debug.boot.redisson.service.DataService;
import com.debug.boot.redisson.thread.ThreadDto;
import com.debug.boot.utils.ValidatorUtil;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.model.entity.Item;
import com.debug.boot.redisson.service.BloomService;
import com.debug.boot.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 布隆过滤器 ~ 判断一个数是否存在于一个大的集合中（判重；过滤重复提交）
 * 容量不够时：用一个新的布隆过滤器和多个老的布隆过滤器共同组成一个新的过滤器，提供相同的接口（循环布隆过滤器）
 **/
@RestController
@RequestMapping("bloom")
public class BloomController {

    public static void main(String[] args) {
        System.out.println((long)((double)(-555555555L) * Math.log(0.03d) / (Math.log(2.0D) * Math.log(2.0D))) - 4294967294L);
    }

    @Autowired
    private BloomService bloomService;

    // http://localhost:9101/bloom/item/add
    // 添加商品
    @RequestMapping(value = "item/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse addItem(@RequestBody @Validated Item item, BindingResult result){
        /*if (result.hasErrors()){
            return new BaseResponse(StatusCode.InvalidParams);
        }*/
        String res= ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(bloomService.addItem(item));

        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    // http://localhost:9101/bloom/data/threads
    //多线程生成大批量的数据，并批量插入数据库-再入布隆过滤器 ~ 构造布隆过滤器实例
    @RequestMapping(value = "data/threads",method = RequestMethod.GET)
    public BaseResponse threadData(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            bloomService.genDataByThreads();

            //TODO:每个线程生成1w个随机数
            int i=0;
            Set<String> set= Sets.newHashSet();
            for (;i<=10;i++){
                set.add(ThreadDto.SNOWFLAKE.nextIdStr());
            }
            DataService dataService = MainApplication.ac.getBean(DataService.class);

            dataService.insertBatchData(set);
        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    // http://localhost:9101/bloom/data/exist
    //检测给定的数据是否在一个大数集合中存在
    @RequestMapping(value = "data/exist",method = RequestMethod.GET)
    public BaseResponse existData(@RequestParam String dataCode){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(bloomService.existData(dataCode));

        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    // http://localhost:9101/bloom/data/exist
    //检测给定的数据是否在一个大数集合中存在
    @RequestMapping(value = "data/exist2",method = RequestMethod.GET)
    public BaseResponse existData2(@RequestParam String name){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(bloomService.existData(name));

        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}



































