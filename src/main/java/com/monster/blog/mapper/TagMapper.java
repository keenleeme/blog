package com.monster.blog.mapper;

import com.monster.blog.domain.TagEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 标签信息表 Mapper 接口
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Mapper
public interface TagMapper extends BaseMapper<TagEntity> {

}
