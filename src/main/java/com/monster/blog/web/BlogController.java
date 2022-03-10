package com.monster.blog.web;


import com.monster.blog.domain.BlogEntity;
import com.monster.blog.domain.CommentEntity;
import com.monster.blog.service.BlogService;
import com.monster.blog.service.CommentService;
import com.monster.blog.util.MarkdownUtils;
import com.monster.blog.vo.BlogVo;
import com.monster.blog.vo.UpdateNumsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

/**
 * <p>
 * 博客信息表 前端控制器
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Controller
@RequestMapping("/user/blog")
@Api(value = "前台博客管理接口")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    private static final String BLOG_PAGE = "/blog";
    private static final String ARCHIVES_PAGE = "/archives";

    @RequestMapping("/detail/{id}")
    @ApiOperation(value = "查询博客详情接口")
    public String detail(@PathVariable Long id, Model model) {
        UpdateNumsVo numsVo = new UpdateNumsVo();
        numsVo.setId(id);
        numsVo.setIsRead(true);
        blogService.updateNumsAboutBlog(numsVo);
        BlogVo blog = blogService.getBlogDetail(id);
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));
        List<CommentEntity> list = commentService.getCommentListByBlogId(id);
        model.addAttribute("blog", blog);
        model.addAttribute("comments", list);
        return BLOG_PAGE;
    }

    @RequestMapping("/archives")
    @ApiOperation(value = "获取时间轴接口")
    public String getArchives(Model model) {
        List<BlogEntity> list = blogService.getBlogOrderByTime();
        model.addAttribute("blogs", list);
        return ARCHIVES_PAGE;
    }

}

