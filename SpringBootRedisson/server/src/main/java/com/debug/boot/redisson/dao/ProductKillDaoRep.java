package com.debug.boot.redisson.dao;

import com.debug.boot.redisson.model.entity.ProductKill;
import com.debug.boot.redisson.model.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductKillDaoRep extends PagingAndSortingRepository<ProductKill,Integer>{


}
