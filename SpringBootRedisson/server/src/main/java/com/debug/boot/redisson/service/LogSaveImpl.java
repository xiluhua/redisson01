package com.debug.boot.redisson.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;

@Service
@Transactional(propagation= Propagation.REQUIRED)
public class LogSaveImpl {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @author xilh
	 * @since 20191115
	 */
    @Transactional(propagation= Propagation.REQUIRED)
	public <T> void saveBatch(LinkedList<T> list) throws Exception {
		try {
			int i = 0;
			while (list.size() > 0) {
				entityManager.persist(list.poll());
				i++;
				if (i % 20 == 0){
					entityManager.flush();
					entityManager.clear();
				}
				//再批量保存一次
				entityManager.flush();
				entityManager.clear();
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}
}
