package com.debug.boot.remote.redisson.server.controller;/**
 * Created by Administrator on 2020/1/13.
 */

import com.debug.boot.redisson.api.response.BaseResponse;
import com.debug.boot.redisson.api.response.StatusCode;
import com.debug.boot.redisson.api.service.IRemoteProductService;
import com.debug.boot.redisson.api.service.RemoteResponse;
import com.debug.boot.redisson.api.service.RemoteStatus;
import org.redisson.api.RRemoteService;
import org.redisson.api.RedissonClient;
import org.redisson.api.RemoteInvocationOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * redisson分布式服务调度 ~ 客服端实例
 **/
@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private RedissonClient redissonClient;

    // http://localhost:9102/product/info
    @RequestMapping(value = "info",method = RequestMethod.GET)
    public BaseResponse info(@RequestParam Integer id){
        if (id==null || id<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            //分布式远程服务调用
            RRemoteService remoteService = redissonClient.getRemoteService();
            //IRemoteProductService remoteProductService=remoteService.get(IRemoteProductService.class);
            //应答回执超时1秒钟，远程执行超时30秒钟
            IRemoteProductService remoteProductService = remoteService.get(IRemoteProductService.class, RemoteInvocationOptions.defaults());

            RemoteResponse remoteResponse = remoteProductService.getInfo(id);
            if (remoteResponse.getCode().equals(RemoteStatus.Success.getCode())){
                response.setData(remoteResponse.getData());
            }else{
                return new BaseResponse(remoteResponse.getCode(),remoteResponse.getMsg());
            }
        }catch (Exception e){
            e.printStackTrace();
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }


}





























