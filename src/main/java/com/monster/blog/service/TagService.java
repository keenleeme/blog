package com.monster.blog.service;

import com.monster.blog.domain.TagEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 标签信息表 服务类
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
public interface TagService extends IService<TagEntity> {

    List<TagEntity> getAllTags(Long userId);

    TagEntity getTagById(Long id);

    boolean checkTagByName(String name, Long id);

    TagEntity getTagByName(String name);

    int addTag(String name, Long userId);

    int updateTag(Long id, String name, Long userId);

    int deleteTag(Long id, Long userId);

}
