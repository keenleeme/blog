package com.monster.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.monster.blog.domain.AccountEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangdj
 * @since 2021-03-03
 */
@Mapper
public interface AccountMapper extends BaseMapper<AccountEntity> {

}
