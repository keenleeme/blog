package com.monster.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monster.blog.constant.BlogConstant;
import com.monster.blog.domain.BlogEntity;
import com.monster.blog.mapper.BlogMapper;
import com.monster.blog.query.BlogQuery;
import com.monster.blog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monster.blog.service.MessageService;
import com.monster.blog.service.TagService;
import com.monster.blog.service.TypeService;
import com.monster.blog.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 博客信息表 服务实现类
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Slf4j
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, BlogEntity> implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TypeService typeService;

    @Override
    public BlogStatisticVo getBlogStatistic(Long userId) {
        BlogStatisticVo blogStatistic = blogMapper.selectBlogStatistic(userId);
        Integer messageNums = messageService.countAllMessage(userId);
        blogStatistic.setTotalMessageNums(messageNums);
        return blogStatistic;
    }

    @Override
    public List<BlogVo> getBlogList(BlogQuery blogQuery) {
        if (StringUtils.isNotBlank(blogQuery.getTagIds())) {
            blogQuery.setTagIdList(Arrays.asList(blogQuery.getTagIds().split(",")));
        }
        List<BlogVo> blogList = blogMapper.selectBlogList(blogQuery);
        // 遍历集合
        for (BlogVo blog : blogList) {
            List<String> blogIds = Arrays.asList(blog.getTagIds().split(","));
            List<String> tagNames = new ArrayList<>();
            // 遍历标签id集合
            for (String blogId : blogIds) {
                tagNames.add(tagService.getTagById(Long.parseLong(blogId)).getName());
            }
            blog.setTagName(tagNames.stream().collect(Collectors.joining(",")));
        }
        log.info(blogList.toString());
        return blogList;
    }

    @Override
    public int addBlog(BlogEntity blogEntity) {
        blogEntity.setCreatedTime(new Date())
                .setUpdatedTime(new Date());
        return blogMapper.insertBlog(blogEntity);
    }

    @Override
    public int updateBlog(AddBlogVo blog, Long id, Long userId) {
        BlogEntity blogEntity = blogMapper.selectById(id);
        blogEntity.setUpdatedBy(userId);
        blogEntity.setTagIds(blog.getTagIds())
                .setBannerImg(blog.getBannerImg())
                .setContent(blog.getContent())
                .setDescs(blog.getDescs())
                .setTypeId(blog.getTypeId())
                .setTitle(blog.getTitle())
                .setIsCommentable(blog.getIsCommentable())
                .setIsLike(blog.getIsLike())
                .setIsOriginal(blog.getIsOriginal())
                .setIsPrivacy(blog.getIsPrivacy())
                .setIsRecommend(blog.getIsRecommend())
                .setShareStatement(blog.getShareStatement())
                .setStatus(blog.getStatus())
                .setUpdatedTime(new Date());
        return blogMapper.updateById(blogEntity);
    }

    @Override
    public BlogEntity getBlogByName(String title, Long userId) {
        QueryWrapper<BlogEntity> query = new QueryWrapper<>();
        query.eq(BlogConstant.TITLE, title);
        query.eq(BlogConstant.CREATED_BY, userId);
        query.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
        return blogMapper.selectOne(query);
    }

    @Override
    public BlogEntity getBlogById(Long id) {
        return blogMapper.selectById(id);
    }

    @Override
    public int deleteBlog(Long id, Long userId) {
        BlogEntity blog = blogMapper.selectById(id);
        blog.setUpdatedTime(new Date());
        blog.setIsRemoved(BlogConstant.IS_REMOVED);
        blog.setUpdatedBy(userId);
        return blogMapper.updateById(blog);
    }

    @Override
    public List<RecommendBlogVo> getRecommendBlogList() {

        return blogMapper.selectRecommendList();
    }

    @Override
    public List<IndexBlogVo> getIndexBlogList() {
        return blogMapper.selectIndexBlogList();
    }

    @Override
    public List<IndexBlogVo> getBlogByType(Long type) {
        return blogMapper.selectBlogByType(type);
    }

    @Override
    public BlogVo getBlogDetail(Long id) {
        BlogVo blog = blogMapper.selectBlogDetail(id);
        List<String> tagNames = new ArrayList<>();
        List<String> blogIds = Arrays.asList(blog.getTagIds().split(","));
        // 遍历标签id集合
        for (String blogId : blogIds) {
            tagNames.add(tagService.getTagById(Long.parseLong(blogId)).getName());
        }
        blog.setTagName(tagNames.stream().collect(Collectors.joining(",")));
        blog.setTagNames(tagNames);
        return blog;
    }

    @Override
    public int updateNumsAboutBlog(UpdateNumsVo numsVo) {

        return blogMapper.updateNumsAboutBlog(numsVo);
    }

    @Override
    public List<BlogEntity> getBlogOrderByTime() {
        QueryWrapper<BlogEntity> query = new QueryWrapper<>();
        query.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
        query.orderByDesc(BlogConstant.CREATED_TIME);
        return blogMapper.selectList(query);
    }
}
