package com.monster.blog.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liz
 * @Description 首页推荐博客vo类
 * @date 2020/6/8-16:08
 */
@Data
@Api(value = "首页推荐博客vo类")
public class RecommendBlogVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "banner图")
    private String bannerImg;

    @ApiModelProperty(value = "标题")
    private String title;

}
