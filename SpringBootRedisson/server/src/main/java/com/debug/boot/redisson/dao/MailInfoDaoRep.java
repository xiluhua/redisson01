package com.debug.boot.redisson.dao;

import com.debug.boot.redisson.model.entity.MailInfo;
import com.debug.boot.redisson.model.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailInfoDaoRep extends PagingAndSortingRepository<MailInfo,Integer>{


}
