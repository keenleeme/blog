package com.monster.blog.mapper;

import com.monster.blog.domain.BlogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.monster.blog.query.BlogQuery;
import com.monster.blog.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 博客信息表 Mapper 接口
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Mapper
public interface BlogMapper extends BaseMapper<BlogEntity> {

    /**
     * 查询后台博客统计
     * @param userId 用户id
     * @return
     */
    BlogStatisticVo selectBlogStatistic(@Param("userId") Long userId);

    /**
     * 查询后台管理列表接口
     * @param blogQuery
     * @return
     */
    List<BlogVo> selectBlogList(BlogQuery blogQuery);

    int insertBlog(BlogEntity blogEntity);

    List<RecommendBlogVo> selectRecommendList();

    List<IndexBlogVo> selectIndexBlogList();

    BlogVo selectBlogDetail(@Param("id") Long id);

    int updateNumsAboutBlog(UpdateNumsVo numsVo);

    int minusNumsAboutBlog(UpdateNumsVo numsVo);

    List<IndexBlogVo> selectBlogByType(@Param("typeId") Long typeId);
}
