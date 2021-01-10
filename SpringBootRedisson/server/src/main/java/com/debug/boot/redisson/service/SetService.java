package com.debug.boot.redisson.service;

import com.debug.boot.redisson.dao.ProblemDaoRep;
import com.debug.boot.redisson.enums.Constant;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.debug.boot.redisson.model.entity.Problem;
import org.redisson.api.RSet;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 集合set - 题库 service
 **/
@Service
public class SetService {

    private static final Logger log= LoggerFactory.getLogger(SetService.class);

    @Autowired
    private ProblemDaoRep problemDaoRep;

    @Autowired
    private RedissonClient redissonClient;

    //添加试题
    @Transactional(rollbackFor = Exception.class)
    public int addProblem(Problem problem) throws Exception{
        int res= problemDaoRep.save(problem).getId();
        if (res>0){
            //往缓存里头放一份
            this.cacheAllProblems();
            return problem.getId();
        }
        return -1;
    }

    //获取所有试题时乱序
    public Set<Problem> getProblemsV1() throws Exception{
        RSet<Problem> rSet= redissonClient.getSet(Constant.RedisSetProblem);
        return rSet.readAll();
    }

    public List<Object> getProblemsV2() throws Exception{
        RSet<Problem> rSet= redissonClient.getSet(Constant.RedisSetProblem);
        List<Object> list=Arrays.asList(rSet.readAll().toArray());
        Collections.shuffle(list);
        return list;
    }

    public Problem randomProblem() throws Exception{
        RSet<Problem> rSet= redissonClient.getSet(Constant.RedisSetProblem);
        return rSet.random();
    }

    //删除题目
    public int deleteProblem(final Integer id) throws Exception{
        Problem entity= problemDaoRep.findById(id).get();
        if (entity!=null){
            problemDaoRep.deleteById(id);
            this.cacheAllProblems();

            return 1;
        }
        return -1;
    }


    //TODO:原则 ~ 基于db里头所有有效的试题为主，将其全部捞出来塞入缓存里头
    private void cacheAllProblems() throws Exception{
        RSet<Problem> rSet= redissonClient.getSet(Constant.RedisSetProblem);
        Iterable<Problem> problems= problemDaoRep.findAll();
        Set<Problem> set = new HashSet<>();
        problems.forEach(problem -> set.add(problem));

        if (!rSet.isEmpty()){
            rSet.clear();

            //TODO:原来缓存是有的
            if (set!=null && !set.isEmpty()){
                //TODO:数据库有数据
                //rSet.clear();
                rSet.addAll(set);
            }
        }else{
            //TODO:原来缓存是没有的
            if (set!=null && !set.isEmpty()){
                rSet.addAll(set);
            }
        }
    }
}


































