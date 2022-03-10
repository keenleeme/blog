package com.monster.blog.service;

import com.monster.blog.domain.BlogEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.monster.blog.query.BlogQuery;
import com.monster.blog.vo.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 博客信息表 服务类
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
public interface BlogService extends IService<BlogEntity> {

    BlogStatisticVo getBlogStatistic(Long userId);

    List<BlogVo> getBlogList(BlogQuery blogQuery);

    int addBlog(BlogEntity blogEntity);

    BlogEntity getBlogByName(String title, Long userId);

    BlogEntity getBlogById(Long id);

    int updateBlog(AddBlogVo blog,Long id, Long userId);

    int deleteBlog(Long id, Long userId);

    List<RecommendBlogVo> getRecommendBlogList();

    List<IndexBlogVo> getIndexBlogList();

    List<IndexBlogVo> getBlogByType(Long type);

    BlogVo getBlogDetail(Long id);

    int updateNumsAboutBlog(UpdateNumsVo numsVo);

    List<BlogEntity> getBlogOrderByTime();

}
