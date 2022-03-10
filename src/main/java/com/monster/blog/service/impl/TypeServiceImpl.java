package com.monster.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monster.blog.constant.BlogConstant;
import com.monster.blog.domain.TypeEntity;
import com.monster.blog.mapper.TypeMapper;
import com.monster.blog.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分类信息表 服务实现类
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, TypeEntity> implements TypeService {

    @Resource
    private TypeMapper typeMapper;

    @Override
    public List<TypeEntity> getAllTypes(Long userId) {
        QueryWrapper<TypeEntity> query = new QueryWrapper<>();
        query.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
        if (userId != null) {
            query.eq(BlogConstant.CREATED_BY, userId);
        }
        query.orderByDesc(BlogConstant.UPDATED_TIME);
        return typeMapper.selectList(query);
    }

    @Override
    public int addType(String name, Long userId) {
        TypeEntity type = new TypeEntity();
        type.setCreatedBy(userId);
        type.setCreatedTime(new Date());
        type.setUpdatedTime(new Date());
        type.setIsRemoved(BlogConstant.NOT_REMOVED);
        type.setName(name);
        return typeMapper.insert(type);
    }

    @Override
    public TypeEntity getTypeById(Long id) {
        QueryWrapper<TypeEntity> query = new QueryWrapper<>();
        query.eq(BlogConstant.ID, id);
        query.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
        return typeMapper.selectOne(query);
    }

    @Override
    public TypeEntity getTypeByName(String name) {
        QueryWrapper<TypeEntity> query = new QueryWrapper<>();
        query.eq(BlogConstant.NAME, name);
        query.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
        return typeMapper.selectOne(query);
    }

    @Override
    public int updateType(Long id, String name, Long userId) {
        TypeEntity type = typeMapper.selectById(id);
        type.setName(name);
        type.setUpdatedBy(userId);
        type.setUpdatedTime(new Date());
        return typeMapper.updateById(type);
    }

    @Override
    public int deleteType(Long id, Long userId) {
        TypeEntity typeEntity = typeMapper.selectById(id);
        typeEntity.setUpdatedBy(userId);
        typeEntity.setUpdatedTime(new Date());
        typeEntity.setIsRemoved(BlogConstant.IS_REMOVED);
        return typeMapper.updateById(typeEntity);
    }

    @Override
    public List<Map<String, Object>> getTypeAndBlogNum() {
        return typeMapper.getTypeAndBlogNum();
    }
}
