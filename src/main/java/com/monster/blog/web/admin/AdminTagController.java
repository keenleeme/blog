package com.monster.blog.web.admin;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monster.blog.constant.BlogConstant;
import com.monster.blog.domain.TagEntity;
import com.monster.blog.domain.UserEntity;
import com.monster.blog.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author liz
 * @Description 后台管理标签管理接口
 * @date 2020/6/6-21:24
 */
@Controller
@RequestMapping("/admin/tag")
@Api(value = "标签管理接口", tags = {"标签管理接口"})
public class AdminTagController {

    @Autowired
    private TagService tagService;

    private static final String TAG_PAGE = "/admin/tags";
    private static final String ADD_TAG_PAGE = "/admin/tags-input";
    private static final String REDIRECT_ADD_TAG_PAGE = "redirect:/admin/tag/addTag";
    private static final String REDIRECT_TAG_PAGE = "redirect:/admin/tag/tagList";

    @RequestMapping("/tagList")
    @ApiOperation(value = "查询标签列表页面")
    public String tagPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        PageHelper.startPage(pageNum, BlogConstant.PAGE_SIZE);
        List<TagEntity> list = tagService.getAllTags(user.getRoleId() == 1 ? null : user.getId());
        PageInfo<TagEntity> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        return TAG_PAGE;
    }

    @RequestMapping("/addTag")
    @ApiOperation(value = "新增标签页面")
    public String addType(Model model) {
        model.addAttribute("tag", new TagEntity());
        return ADD_TAG_PAGE;
    }

    @RequestMapping("/save")
    @ApiOperation(value = "新增标签接口")
    public String save(@RequestParam(value = "name") String name, HttpSession session, RedirectAttributes attributes) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        // 判断标签名是否重复
        if (tagService.getTagByName(name) != null) {
            attributes.addFlashAttribute("message", "不能添加重复的标签");
            return REDIRECT_ADD_TAG_PAGE;
        }
        tagService.addTag(name, user.getId());
        return REDIRECT_TAG_PAGE;
    }

    @RequestMapping("/updateTag/{id}")
    @ApiOperation(value = "新增标签页面")
    public String addType(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTagById(id));
        return ADD_TAG_PAGE;
    }

    @RequestMapping("/update/{id}")
    @ApiOperation(value = "更新标签接口")
    public String update(@PathVariable Long id, @RequestParam(value = "name") String name, HttpSession session, RedirectAttributes attributes) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        // 判断标签名是否重复
        if (tagService.checkTagByName(name, id)) {
            attributes.addFlashAttribute("message", "不能添加重复的标签");
            return REDIRECT_ADD_TAG_PAGE;
        }
        tagService.updateTag(id, name, user.getId());
        return REDIRECT_TAG_PAGE;
    }


    @RequestMapping("/delete/{id}")
    @ApiOperation(value = "删除标签")
    public String delete(@PathVariable Long id, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        tagService.deleteTag(id, user.getId());
        return REDIRECT_TAG_PAGE;
    }

}
