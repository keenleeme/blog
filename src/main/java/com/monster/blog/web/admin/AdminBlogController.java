package com.monster.blog.web.admin;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monster.blog.constant.BlogConstant;
import com.monster.blog.domain.BlogEntity;
import com.monster.blog.domain.UserEntity;
import com.monster.blog.query.BlogQuery;
import com.monster.blog.service.BlogService;
import com.monster.blog.service.TagService;
import com.monster.blog.service.TypeService;
import com.monster.blog.util.UploadUtil;
import com.monster.blog.vo.AddBlogVo;
import com.monster.blog.vo.BlogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author liz
 * @Description 后台管理博客管理接口
 * @date 2020/6/5-13:51
 */
@Controller
@RequestMapping("/admin/blog")
@Api(value = "后台管理博客管理接口", tags = {"博客管理接口"})
public class AdminBlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    private static final String BLOG_PAGE = "/admin/blogs";
    private static final String BLOG_PAGE_LIST = "/admin/blogs :: blogList";
    private static final String ADD_BLOG_PAGE = "/admin/blogs-input";
    private static final String REDIRECT_BLOG_PAGE = "redirect:/admin/blog/blogList";
    private static final String REDIRECT_ADD_BLOG_PAGE = "redirect:/admin/blog/addBlog";
    private static final String REDIRECT_UPDATE_BLOG_PAGE = "redirect:/admin/blog/updateBlog/";

    @RequestMapping("/blogList")
    @ApiOperation(value = "博客列表")
    public String blogList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        BlogQuery blogQuery = new BlogQuery();
        if (user.getRoleId() != 1) {
            blogQuery.setUserId(user.getId());
        }
        PageHelper.startPage(pageNum, BlogConstant.PAGE_SIZE);
        List<BlogVo> list = blogService.getBlogList(blogQuery);
        PageInfo<BlogVo> pageInfo = new PageInfo<>(list);
        model.addAttribute("types", typeService.getAllTypes(null));
        model.addAttribute("tags", tagService.getAllTags(null));
        model.addAttribute("pageInfo", pageInfo);
        return BLOG_PAGE;
    }

    @RequestMapping("/search")
    @ApiOperation(value = "博客列表")
    public String search(BlogQuery blogQuery, Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        if (user.getRoleId() != 1) {
            blogQuery.setUserId(user.getId());
        }
        Integer pageNum = blogQuery.getPageNum();
        List<BlogVo> list = blogService.getBlogList(blogQuery);
        if (!CollUtil.isEmpty(list)) {
            PageHelper.startPage(pageNum, BlogConstant.PAGE_SIZE);
        }
        PageInfo<BlogVo> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("types", typeService.getAllTypes(null));
        model.addAttribute("tags", tagService.getAllTags(null));
        return BLOG_PAGE_LIST;
    }

    @RequestMapping("/addBlog")
    @ApiOperation(value = "新增博客页面")
    public String addBlog(Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        model.addAttribute("blog", new BlogEntity().setRoleId(user.getRoleId()));
        model.addAttribute("types", typeService.getAllTypes(null));
        model.addAttribute("tags", tagService.getAllTags(null));
        return ADD_BLOG_PAGE;
    }

    @RequestMapping("/save")
    @ApiOperation(value = "新增博客")
    public String save(BlogEntity blog, MultipartFile file, HttpSession session, RedirectAttributes attributes) {
        Map<String,Object> map = UploadUtil.upload(file);
        Integer code = Integer.parseInt(map.get(BlogConstant.CODE).toString());
        // 判断是否成功，200 -> 成功
        if (!code.equals(BlogConstant.SUCCESS)) {
            attributes.addFlashAttribute("message", "图片上传失败");
            return ADD_BLOG_PAGE;
        }
        String url = map.get(BlogConstant.PICTURE_SRC).toString();
        blog.setBannerImg(BlogConstant.PICTURE_URL + url);
        UserEntity user = (UserEntity) session.getAttribute("admin");
        BlogEntity blogEntity = blogService.getBlogByName(blog.getTitle(), user.getId());
        // 判断标题是否已存在
        if (blogEntity != null) {
            attributes.addFlashAttribute("message", "不能添加重复的标题");
            return REDIRECT_ADD_BLOG_PAGE;
        }
        blog.setCreatedBy(user.getId());
        blog.setUpdatedBy(user.getId());
        blogService.addBlog(blog);
        return REDIRECT_BLOG_PAGE;
    }

    @RequestMapping("/updateBlog/{id}")
    @ApiOperation(value = "编辑博客页面")
    public String updateBlog(@PathVariable Long id, Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        model.addAttribute("blog", blogService.getBlogById(id).setRoleId(user.getRoleId()));
        model.addAttribute("types", typeService.getAllTypes(null));
        model.addAttribute("tags", tagService.getAllTags(null));
        return ADD_BLOG_PAGE;
    }

    @RequestMapping("/update/{id}")
    @ApiOperation(value = "编辑博客")
    public String update(@PathVariable Long id, AddBlogVo blog, MultipartFile file, HttpSession session, RedirectAttributes attributes) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        BlogEntity blogEntity = blogService.getBlogByName(blog.getTitle(), user.getId());
        // 判断标题是否已存在
        if (blogEntity != null && !id.equals(blogEntity.getId())) {
            attributes.addFlashAttribute("message", "不能添加重复的标题");
            return REDIRECT_UPDATE_BLOG_PAGE + id;
        }
        if(StringUtils.isNotBlank(file.getOriginalFilename())) {
            Map<String,Object> map = UploadUtil.upload(file);
            Integer code = Integer.parseInt(map.get(BlogConstant.CODE).toString());
            // 判断是否成功，200 -> 成功
            if (!code.equals(BlogConstant.SUCCESS)) {
                attributes.addFlashAttribute("message", "图片上传失败");
                return ADD_BLOG_PAGE;
            }
            String url = map.get(BlogConstant.PICTURE_SRC).toString();
            blog.setBannerImg(BlogConstant.PICTURE_URL + url);
        }
        blogService.updateBlog(blog, id, user.getId());
        return REDIRECT_BLOG_PAGE;
    }

    @RequestMapping("/delete/{id}")
    @ApiOperation(value = "删除博客")
    public String delete(@PathVariable Long id, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        blogService.deleteBlog(id, user.getId());
        return REDIRECT_BLOG_PAGE;
    }

}
