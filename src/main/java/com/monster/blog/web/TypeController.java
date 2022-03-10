package com.monster.blog.web;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monster.blog.constant.BlogConstant;
import com.monster.blog.domain.TypeEntity;
import com.monster.blog.service.BlogService;
import com.monster.blog.service.TypeService;
import com.monster.blog.vo.IndexBlogVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分类信息表 前端控制器
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Controller
@RequestMapping("/user/type")
@Api(value = "分类模块接口")
public class TypeController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    private static final String TYPE_PAGE = "/types";

    @RequestMapping("/{type}")
    public String getBlogByType(@PathVariable Long type, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, Model model) {
        List<Map<String,Object>> list = typeService.getTypeAndBlogNum();
        PageHelper.startPage(pageNum, BlogConstant.INDEX_PAGE_SIZE);
        if (type.equals(-1L)) {
            List<TypeEntity> types = typeService.getAllTypes(null);
            type = (long) list.get(0).get("id");
        }
        List<IndexBlogVo> blogList = blogService.getBlogByType(type);
        PageInfo<IndexBlogVo> pageInfo = new PageInfo<>(blogList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("typeId", type);
        model.addAttribute("types",list);
        return TYPE_PAGE;
    }
}

