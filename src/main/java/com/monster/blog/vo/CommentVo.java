package com.monster.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liz
 * @Description 用户评论/回复vo类
 * @date 2020/6/11-21:59
 */
@Data
@ApiModel(value = "用户评论/回复vo类")
public class CommentVo {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "博客id")
    private Long blogId;

    @ApiModelProperty(value = "父评论id")
    private Long parentCommentId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "是否删除")
    private Integer removed = 0;

}
