package com.debug.boot.redisson.service;

import com.debug.boot.redisson.dao.ItemDaoRep;
import com.debug.boot.redisson.model.entity.Item;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class ItemService {

    @Resource
    ItemDaoRep itemDaoRep;

    @Transactional
    public void save(Item item){
        itemDaoRep.save(item);
        System.out.println(item.getId());
        System.out.println();
    }
}
