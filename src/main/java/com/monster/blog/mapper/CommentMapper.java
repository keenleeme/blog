package com.monster.blog.mapper;

import com.monster.blog.domain.CommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 评论id Mapper 接口
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Mapper
public interface CommentMapper extends BaseMapper<CommentEntity> {

}
