package com.debug.boot.redisson.dao;

import com.debug.boot.redisson.api.dto.ProductInfo;
import com.debug.boot.redisson.model.entity.ProductKill;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProductKillDao{

    // 获取和当前事务相关联的 EntityManager
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @return Customer
     */
    @SuppressWarnings("unchecked")
    public ProductInfo query12(Integer id) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT k.*,p.prod_name from product p,product_kill k ");
        jpql.append("WHERE p.prod_code = k.prod_code ");
        jpql.append("and k.is_active = 1 ");
        jpql.append("and k.id = ? ");
        Query query = entityManager.createNativeQuery(jpql.toString(),ProductInfo.class);
        query.setParameter(1, id);
        ProductInfo info = (ProductInfo)query.getSingleResult();
        return info;
    }


}
