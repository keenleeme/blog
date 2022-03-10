package com.monster.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monster.blog.constant.BlogConstant;
import com.monster.blog.domain.TagEntity;
import com.monster.blog.mapper.TagMapper;
import com.monster.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 标签信息表 服务实现类
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagEntity> implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public List<TagEntity> getAllTags(Long userId) {
        QueryWrapper<TagEntity> query = new QueryWrapper<>();
        if (userId != null) {
            query.eq(BlogConstant.CREATED_BY, userId);
        }
        query.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
        query.orderByDesc(BlogConstant.UPDATED_TIME);
        return tagMapper.selectList(query);
    }

    @Override
    public TagEntity getTagById(Long id) {
        return tagMapper.selectById(id);
    }

    @Override
    public boolean checkTagByName(String name, Long id) {
        QueryWrapper<TagEntity> query = new QueryWrapper<>();
        query.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
        query.eq(BlogConstant.NAME, name);
        TagEntity tag = tagMapper.selectOne(query);
        if (tag == null) {
            return false;
        }
        if (tag.getId().equals(id)) {
            return false;
        }
        return true;
    }

    @Override
    public TagEntity getTagByName(String name) {
        QueryWrapper<TagEntity> query = new QueryWrapper<>();
        query.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
        query.eq(BlogConstant.NAME, name);
        return tagMapper.selectOne(query);
    }

    @Override
    public int addTag(String name, Long userId) {
        TagEntity tag = new TagEntity()
                .setCreatedBy(userId)
                .setCreatedTime(new Date())
                .setIsRemoved(BlogConstant.NOT_REMOVED)
                .setName(name)
                .setUpdatedTime(new Date());
        return tagMapper.insert(tag);
    }

    @Override
    public int updateTag(Long id, String name, Long userId) {
        TagEntity tag = tagMapper.selectById(id)
                .setUpdatedTime(new Date())
                .setName(name)
                .setUpdatedBy(userId);
        return tagMapper.updateById(tag);
    }

    @Override
    public int deleteTag(Long id, Long userId) {
        TagEntity tag = tagMapper.selectById(id)
                .setIsRemoved(BlogConstant.IS_REMOVED)
                .setUpdatedTime(new Date())
                .setUpdatedBy(userId);
        return tagMapper.updateById(tag);
    }
}
