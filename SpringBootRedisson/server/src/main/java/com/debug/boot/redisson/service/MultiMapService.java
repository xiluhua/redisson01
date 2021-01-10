package com.debug.boot.redisson.service;

import com.debug.boot.redisson.dao.SysConfigDaoRep;
import com.debug.boot.redisson.enums.Constant;
import com.debug.boot.redisson.model.entity.SysConfig;
import org.redisson.api.RMultimap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;

import com.debug.boot.redisson.dao.SysConfigDaoRep;
import com.debug.boot.redisson.model.entity.SysConfig;
import org.redisson.api.RMultimap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 多值映射service
 **/
@Service
public class MultiMapService {

    private static final Logger log= LoggerFactory.getLogger(MultiMapService.class);

    @Autowired
    private SysConfigDaoRep sysConfigDaoRep;

    @Autowired
    private RedissonClient redissonClient;


    //录入数据字典
    @Transactional(rollbackFor = Exception.class)
    public int insertConfig(SysConfig config) throws Exception{
        //TODO:录入数据库
        int res= sysConfigDaoRep.save(config).getId();
        if (res>0){
            //TODO:录入缓存-多值映射
            RMultimap<String,SysConfig> rMultimap= redissonClient.getSetMultimap(Constant.RedisMultiMapConfig);
            Collection<SysConfig> collection = sysConfigDaoRep.findByType(config.getType());
            rMultimap.putAll(config.getType(), collection);

            return config.getId();
        }
        return -1;
    }

    //获取数据字典选项列表
    public Collection<SysConfig> getConfigByType(final String typeCode) throws Exception{
        RMultimap<String,SysConfig> rMultimap= redissonClient.getSetMultimap(Constant.RedisMultiMapConfig);
        Collection<SysConfig> collection = rMultimap.get(typeCode);
        if (collection != null && collection.size() > 0)
            return collection;

        return sysConfigDaoRep.findByType(typeCode);
    }


    //删除数据字典
    @Transactional(rollbackFor = Exception.class)
    public int deleteConfig(final Integer id) throws Exception{
        SysConfig entity= sysConfigDaoRep.findById(id).get();
        if (entity!=null){
            sysConfigDaoRep.deleteById(id);
            //TODO:先查出数据库里头当前这个大类对应的小类(数据选项列表)，然后替换掉缓存里头多值映射对应的大类的取值列表
            RMultimap<String,SysConfig> rMultimap= redissonClient.getSetMultimap(Constant.RedisMultiMapConfig);
            List<SysConfig> list= sysConfigDaoRep.findByType(entity.getType());
            rMultimap.fastRemove(entity.getType());

            if (list!=null && !list.isEmpty()){
                rMultimap.putAll(entity.getType(),list);
            }
            return 1;
        }
        return -1;
    }

}




























