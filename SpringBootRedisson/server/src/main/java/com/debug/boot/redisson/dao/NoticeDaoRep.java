package com.debug.boot.redisson.dao;

import com.debug.boot.redisson.model.entity.Notice;
import com.debug.boot.redisson.model.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeDaoRep extends PagingAndSortingRepository<Notice,Integer>{


}
