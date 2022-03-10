package com.monster.blog.web.admin;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monster.blog.constant.BlogConstant;
import com.monster.blog.domain.TypeEntity;
import com.monster.blog.domain.UserEntity;
import com.monster.blog.service.TypeService;
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
 * @Description 后台管理分类管理接口
 * @date 2020/6/6-13:56
 */
@Controller
@RequestMapping("/admin/type")
@Api(value = "分类管理接口")
public class AdminTypeController {

    @Autowired
    private TypeService typeService;

    private static final String TYPE_PAGE = "/admin/types";
    private static final String ADD_TYPE_PAGE = "/admin/types-input";
    private static final String REDIRECT_TYPE_PAGE = "redirect:/admin/type/typeList";
    private static final String REDIRECT_ADD_TYPE_PAGE = "redirect:/admin/type/addType";

    @RequestMapping("/typeList")
    @ApiOperation(value = "查询分类管理页面接口")
    public String typeList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        PageHelper.startPage(pageNum, BlogConstant.PAGE_SIZE);
        List<TypeEntity> list = typeService.getAllTypes(user.getRoleId() == 1 ? null : user.getId());
        PageInfo<TypeEntity> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        return TYPE_PAGE;
    }

    @RequestMapping("/addType")
    @ApiOperation(value = "新增分类页面")
    public String addType(Model model) {
        model.addAttribute("type", new TypeEntity());
        return ADD_TYPE_PAGE;
    }

    @RequestMapping("/save")
    @ApiOperation(value = "新增分类接口")
    public String save(@RequestParam(value = "name") String name, HttpSession session, RedirectAttributes attributes) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        // 判断当前分类名称是否存在
        if (typeService.getTypeByName(name) != null) {
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return REDIRECT_ADD_TYPE_PAGE;
        }
        typeService.addType(name, user.getId());
        return REDIRECT_TYPE_PAGE;
    }

    @RequestMapping("/updateType/{id}")
    @ApiOperation(value = "编辑分类页面")
    public String updateType(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getTypeById(id));
        return ADD_TYPE_PAGE;
    }

    @RequestMapping("/update/{id}")
    @ApiOperation(value = "更新分类接口")
    public String update(@PathVariable Long id, @RequestParam(value = "name") String name, HttpSession session, RedirectAttributes attributes) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        // 判断当前分类名称是否存在
        if (typeService.getTypeByName(name) != null) {
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return REDIRECT_ADD_TYPE_PAGE;
        }
        typeService.updateType(id, name, user.getId());
        return REDIRECT_TYPE_PAGE;
    }

    @RequestMapping("/delete/{id}")
    @ApiOperation(value = "删除分类")
    public String delete(@PathVariable Long id, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        typeService.deleteType(id, user.getId());
        return REDIRECT_TYPE_PAGE;
    }
}
