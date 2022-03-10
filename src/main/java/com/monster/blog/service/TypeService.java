package com.monster.blog.service;

import com.monster.blog.domain.TypeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分类信息表 服务类
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
public interface TypeService extends IService<TypeEntity> {

    List<TypeEntity> getAllTypes(Long userId);

    int addType(String name, Long userId);

    int updateType(Long id, String name, Long userId);

    TypeEntity getTypeById(Long id);

    TypeEntity getTypeByName(String name);

    int deleteType(Long id, Long userId);

    List<Map<String,Object>> getTypeAndBlogNum();
}
