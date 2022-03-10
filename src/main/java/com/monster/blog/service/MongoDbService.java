package com.monster.blog.service;

import com.monster.blog.domain.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author liz
 * @Description TODO
 * @date 2020/8/6-14:32
 */
@Service
public class MongoDbService {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 保存对象
     *
     * @param test
     * @return
     */
    public String saveObj(Test test) {
        test.setCreateTime(new Date());
        test.setUpdateTime(new Date());
        mongoTemplate.save(test);
        return "添加成功";
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<Test> findAll() {
        return mongoTemplate.findAll(Test.class);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Test getTestById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Test.class);
    }

    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    public Test getTestByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Test.class);
    }

    /**
     * 更新对象
     *
     * @param test
     * @return
     */
    public String updateTest(Test test) {
        Query query = new Query(Criteria.where("_id").is(test.getId()));
        Update update = new Update().set("publish", test.getPublish()).set("info", test.getInfo()).set("updateTime",
                new Date());
        // updateFirst 更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, Test.class);
        // updateMulti 更新查询返回结果集的全部
        // mongoTemplate.updateMulti(query,update,test.class);
        // upsert 更新对象不存在则去添加
        // mongoTemplate.upsert(query,update,test.class);
        return "success";
    }

    /***
     * 删除对象
     * @param test
     * @return
     */
    public String deleteTest(Test test) {
        mongoTemplate.remove(test);
        return "success";
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    public String deleteTestById(String id) {
        // findOne
        Test test = getTestById(id);
        // delete
        deleteTest(test);
        return "success";
    }

    /**
     * 模糊查询
     *
     * @param search
     * @return
     */
    public List<Test> findByLikes(String search) {
        Pattern pattern = Pattern.compile("^.*" + search + ".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<Test> lists = mongoTemplate.findAllAndRemove(query, Test.class);
        return lists;
    }

}
