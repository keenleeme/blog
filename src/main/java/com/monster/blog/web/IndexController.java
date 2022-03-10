package com.monster.blog.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liz
 * @Description 首页控制器
 * @date 2020/5/26-22:58
 */
@Controller
@Api(value = "测试接口", tags = {"测试接口"})
@RequestMapping("/")
public class IndexController {

    @GetMapping("/getIndexBlog")
    @ApiOperation(value = "首页接口")
    public String index(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, Model model) {
        System.out.println("-----index-----");
        return "index";
    }
}
