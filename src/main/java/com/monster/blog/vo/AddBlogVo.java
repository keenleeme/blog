package com.monster.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liz
 * @Description 新增博客vo类
 * @date 2020/6/8-0:51
 */
@Data
public class AddBlogVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "分类")
    private Long typeId;

    @ApiModelProperty(value = "标签id,用','拼接")
    private String tagIds;

    @ApiModelProperty(value = "博客内容")
    private String content;

    @ApiModelProperty(value = "首页图")
    private String bannerImg;

    @ApiModelProperty(value = "博客描述")
    private String descs;

    @ApiModelProperty(value = "是否原创")
    private Integer isOriginal;

    @ApiModelProperty(value = "是否推荐")
    private Integer isRecommend;

    @ApiModelProperty(value = "是否公开")
    private Integer isPrivacy;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "转载声明")
    private Integer shareStatement;

    @ApiModelProperty(value = "点赞")
    private Integer isLike;

    @ApiModelProperty(value = "评论")
    private Integer isCommentable;

}
