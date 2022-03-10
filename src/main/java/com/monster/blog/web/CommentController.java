package com.monster.blog.web;


import com.monster.blog.domain.CommentEntity;
import com.monster.blog.domain.UserEntity;
import com.monster.blog.service.CommentService;
import com.monster.blog.vo.CommentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 评论id 前端控制器
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Controller
@RequestMapping("/user/comment")
@Api(value = "用户评论接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    private static final String BLOG_COMMENT_LIST = "/blog :: commentList";
    private static final String REDIRECT_BLOG_PAGE = "redirect:/user/blog/detail/";

    @RequestMapping("/insertComment")
    @ApiOperation(value = "添加评论")
    public String insertComment(CommentVo commentVo, Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        commentService.insertComment(user.getId(), commentVo.getBlogId(), commentVo.getContent());
        List<CommentEntity> list = commentService.getCommentListByBlogId(commentVo.getBlogId());
        model.addAttribute("comments", list);
        return BLOG_COMMENT_LIST;
    }

    @RequestMapping("/replyComment")
    @ApiOperation(value = "回复评论")
    public String replyComment(CommentVo commentVo, Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        commentService.replyComment(user.getId(), commentVo.getParentCommentId(), commentVo.getContent());
        List<CommentEntity> list = commentService.getCommentListByBlogId(commentVo.getBlogId());
        model.addAttribute("comments", list);
        return BLOG_COMMENT_LIST;
    }

    @RequestMapping("/delete/{id}/{blogId}")
    @ApiOperation(value = "删除评论")
    public String deleteComment(@PathVariable Long id, @PathVariable Long blogId, Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        commentService.deleteComment(user.getId(), id, blogId);
        List<CommentEntity> list = commentService.getCommentListByBlogId(blogId);
        model.addAttribute("comments", list);
        return REDIRECT_BLOG_PAGE;
    }

}

