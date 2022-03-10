package com.monster.blog.web.mongodb;

import com.monster.blog.domain.Test;
import com.monster.blog.service.MongoDbService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liz
 * @Description TODO
 * @date 2020/8/6-14:38
 */
@RestController
@RequestMapping("/mongodb")
public class MongoDbController {

    @Resource
    private MongoDbService mongoDbService;

    @PostMapping("/mongo/save")
    public String saveObj(@RequestBody Test book) {
        return mongoDbService.saveObj(book);
    }

    @GetMapping("/mongo/findAll")
    public List<Test> findAll() {
        return mongoDbService.findAll();
    }

    @GetMapping("/mongo/findOne")
    public Test findOne(@RequestParam String id) {
        return mongoDbService.getTestById(id);
    }

    @GetMapping("/mongo/findOneByName")
    public Test findOneByName(@RequestParam String name) {
        return mongoDbService.getTestByName(name);
    }

    @PostMapping("/mongo/update")
    public String update(@RequestBody Test book) {
        return mongoDbService.updateTest(book);
    }

    @PostMapping("/mongo/delOne")
    public String delOne(@RequestBody Test book) {
        return mongoDbService.deleteTest(book);
    }

    @GetMapping("/mongo/delById")
    public String delById(@RequestParam String id) {
        return mongoDbService.deleteTestById(id);
    }

    @GetMapping("/mongo/findLikes")
    public List<Test> findByLikes(@RequestParam String search) {
        return mongoDbService.findByLikes(search);
    }

}
