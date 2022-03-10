package com.monster.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liz
 * @Description 博客数据统计汇总Vo类
 * @date 2020/6/5-9:55
 */
@Data
@ApiModel(value = "博客数据统计汇总Vo类")
public class BlogStatisticVo {

    @ApiModelProperty(value = "博客总数")
    private Integer totalBlogNums;

    @ApiModelProperty(value = "访问总数")
    private Integer totalReadNums;

    @ApiModelProperty(value = "评论总数")
    private Integer totalCommentNums;

    @ApiModelProperty(value = "点赞总数")
    private Integer totalLikeNums;

    @ApiModelProperty(value = "私信总数")
    private Integer totalMessageNums;
}
