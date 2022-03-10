package com.monster.blog.mapper;

import com.monster.blog.domain.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
