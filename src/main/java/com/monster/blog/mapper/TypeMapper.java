package com.monster.blog.mapper;

import com.monster.blog.domain.TypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分类信息表 Mapper 接口
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Mapper
public interface TypeMapper extends BaseMapper<TypeEntity> {

    List<Map<String,Object>> getTypeAndBlogNum();
}
