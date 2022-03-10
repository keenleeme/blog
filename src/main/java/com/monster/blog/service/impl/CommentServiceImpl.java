package com.monster.blog.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monster.blog.constant.BlogConstant;
import com.monster.blog.domain.CommentEntity;
import com.monster.blog.domain.UserEntity;
import com.monster.blog.mapper.BlogMapper;
import com.monster.blog.mapper.CommentMapper;
import com.monster.blog.mapper.UserMapper;
import com.monster.blog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monster.blog.vo.CommentVo;
import com.monster.blog.vo.UpdateNumsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 评论id 服务实现类
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentEntity> implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private BlogMapper blogMapper;

    private List<CommentEntity> replyComments = new ArrayList<>();

    @Override
    public List<CommentEntity> getCommentListByBlogId(Long blogId) {
        QueryWrapper<CommentEntity> query = new QueryWrapper<>();
        query.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
        query.eq(BlogConstant.BLOG_ID, blogId);
        query.eq(BlogConstant.PARENT_COMMENT_ID, 0);
        CommentVo commentVo = new CommentVo();
        commentVo.setBlogId(blogId);
        List<CommentEntity> commentList = commentMapper.selectList(query);
        // 为空就直接返回
        if (CollUtil.isEmpty(commentList)) {
            return commentList;
        }
        // 遍历评论父节点
        for (CommentEntity comment : commentList) {
            QueryWrapper<CommentEntity> query1 = new QueryWrapper<>();
            query1.eq(BlogConstant.BLOG_ID, blogId);
            query1.eq(BlogConstant.IS_REMOVED_STR, 0);
            query1.eq(BlogConstant.PARENT_COMMENT_ID, comment.getId());
            commentVo.setParentCommentId(comment.getId());
            List<CommentEntity> childrenComments = commentMapper.selectList(query1);
            getSecondComments(commentVo,childrenComments);
            comment.setReplyComments(replyComments);
            replyComments = new ArrayList<>();
        }
        return commentList;
    }

    private void getSecondComments(CommentVo commentVo,List<CommentEntity> commentList) {
        // 判断二级评论是否为空
        if (CollUtil.isNotEmpty(commentList)) {
            for (CommentEntity comment: commentList) {
                commentVo.setParentCommentId(comment.getId());
                replyComments.add(comment);
                getMoreLevelComments(commentVo);
            }
        }
    }

    private void getMoreLevelComments(CommentVo commentVo) {
        QueryWrapper<CommentEntity> query = new QueryWrapper<>();
        query.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
        query.eq(BlogConstant.BLOG_ID, commentVo.getBlogId());
        query.eq(BlogConstant.PARENT_COMMENT_ID, commentVo.getParentCommentId());
        List<CommentEntity> thirdComments = commentMapper.selectList(query);
        // 判断三级级评论是否为空
        if (CollUtil.isNotEmpty(thirdComments)) {
            for (CommentEntity thirdComment: thirdComments) {
                QueryWrapper<CommentEntity> query1 = new QueryWrapper<>();
                query1.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
                query1.eq(BlogConstant.BLOG_ID, commentVo.getBlogId());
                query1.eq(BlogConstant.PARENT_COMMENT_ID, thirdComment.getId());
                commentVo.setParentCommentId(thirdComment.getId());
                replyComments.add(thirdComment);
                getMoreLevelComments(commentVo);
            }
        }
    }

    @Override
    public int insertComment(Long userId, Long blogId,String content) {
        UserEntity user = userMapper.selectById(userId);
        CommentEntity comment = new CommentEntity();
        comment.setAdminComment(user.getRoleId() == 1 ? 1: 0)
                .setAvatar(user.getAvatar())
                .setBlogId(blogId)
                .setContent(content)
                .setCreatedBy(userId)
                .setIsRemoved(BlogConstant.NOT_REMOVED)
                .setCreatedTime(new Date())
                .setNickName(user.getUserName())
                .setParentCommentId(0L);
        int num = commentMapper.insert(comment);
        if (num > 0) {
            addCommentNums(blogId);
        }
        return num;
    }

    @Override
    public int replyComment(Long userId, Long parentId, String content) {
        CommentEntity comment = commentMapper.selectById(parentId);
        UserEntity user = userMapper.selectById(userId);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setNickName(user.getUserName())
                .setIsRemoved(BlogConstant.NOT_REMOVED)
                .setCreatedBy(userId)
                .setCreatedTime(new Date())
                .setContent(content)
                .setBlogId(comment.getBlogId())
                .setAvatar(user.getAvatar())
                .setAdminComment(user.getRoleId() == 1 ? 1 : 0)
                .setParentAvatar(comment.getAvatar())
                .setParentCommentId(parentId)
                .setParentNickName(comment.getNickName())
                .setParentUserId(comment.getCreatedBy());
        int num = commentMapper.insert(commentEntity);
        if (num > 0) {
            addCommentNums(comment.getBlogId());
        }
        return num;
    }

    /**
     * 新增评论/回复，博客评论数增加
     * @param blogId
     */
    public void addCommentNums(Long blogId) {
        UpdateNumsVo numsVo = new UpdateNumsVo();
        numsVo.setId(blogId);
        numsVo.setIsComment(true);
        blogMapper.updateNumsAboutBlog(numsVo);
    }

    @Override
    public int deleteComment(Long userId, Long commentId,Long blogId) {
        CommentEntity comment = commentMapper.selectById(commentId);
        comment.setIsRemoved(BlogConstant.IS_REMOVED);
        int num = commentMapper.updateById(comment);
        if (num > 0) {
            UpdateNumsVo numsVo = new UpdateNumsVo();
            numsVo.setId(blogId);
            numsVo.setIsComment(true);
            blogMapper.minusNumsAboutBlog(numsVo);
        }
        return num;
    }
}
