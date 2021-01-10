package com.debug.boot.redisson.dao;

import com.debug.boot.redisson.model.entity.SysConfig;
import com.debug.boot.redisson.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysConfigDaoRep extends PagingAndSortingRepository<SysConfig,Integer>{

    @Query(value="SELECT c FROM SysConfig c WHERE c.type = ?1")
    List<SysConfig> findByType(String type);

}
