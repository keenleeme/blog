package com.monster.blog.service;

import com.monster.blog.domain.CommentEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 评论id 服务类
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
public interface CommentService extends IService<CommentEntity> {

    List<CommentEntity> getCommentListByBlogId(Long blogId);

    int insertComment(Long userId, Long blogId, String content);

    int replyComment(Long userId, Long parentId, String content);

    int deleteComment(Long userId, Long commentId,Long blogId);

}
