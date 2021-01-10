package com.debug.boot.redisson.service;

import com.debug.boot.redisson.api.dto.ProductInfo;
import com.debug.boot.redisson.api.service.IRemoteProductService;
import com.debug.boot.redisson.api.service.RemoteResponse;
import com.debug.boot.redisson.api.service.RemoteStatus;
import com.debug.boot.redisson.dao.ProductKillDao;
import com.debug.boot.redisson.model.entity.ProductKill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 远程服务调用的实现类
 **/
@Service
public class RemoteProductService implements IRemoteProductService {

    private static final Logger log= LoggerFactory.getLogger(RemoteProductService.class);


    @Autowired
    private ProductKillDao productKillDao;


    //实际的核心业务逻辑
    @Override
    public RemoteResponse getInfo(Integer id) {
        RemoteResponse response=new RemoteResponse(RemoteStatus.Success);
        try {
            ProductInfo info= productKillDao.query12(id);
            response.setData(info);

        }catch (Exception e){
            log.error("---远程服务调用的实现类,实际的核心业务逻辑 发生异常：",e);
            response=new RemoteResponse(RemoteStatus.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}


































