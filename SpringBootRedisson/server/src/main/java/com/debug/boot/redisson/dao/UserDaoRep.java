package com.debug.boot.redisson.dao;

import com.debug.boot.redisson.model.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDaoRep extends PagingAndSortingRepository<User,Integer>{


}
