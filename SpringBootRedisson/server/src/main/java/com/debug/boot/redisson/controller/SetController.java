package com.debug.boot.redisson.controller;

import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.service.SetService;
import com.debug.boot.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.debug.boot.redisson.model.entity.Problem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 集合Set - 题库的管理和随机获取
 **/
@RestController
@RequestMapping("set/problem")
public class SetController {

    private static final Logger log= LoggerFactory.getLogger(SetController.class);

    @Autowired
    private SetService setService;

    // http://localhost:9101/set/problem/add
    //TODO:新增试题
    @RequestMapping(value = "add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse addProblem(@RequestBody @Validated Problem problem, BindingResult result){
        String checkRes= ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(checkRes)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),checkRes);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(setService.addProblem(problem));

        }catch (Exception e){
            log.error("新增试题-发生异常：",e);
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    // http://localhost:9101/set/problem/get
    //TODO:获取试题(所有)
    @RequestMapping(value = "get",method = RequestMethod.GET)
    public BaseResponse getProblems(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(setService.getProblemsV1());
           // response.setData(setService.getProblemsV2());

        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    // http://localhost:9101/set/problem/random
    //TODO:获取一道题目
    @RequestMapping(value = "random",method = RequestMethod.GET)
    public BaseResponse random(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(setService.randomProblem());
        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    // http://localhost:9101/set/problem/delete
    //TODO:删除试题
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public BaseResponse deleteProblem(@RequestParam Integer id){
        if (id==null || id<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(setService.deleteProblem(id));
        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
