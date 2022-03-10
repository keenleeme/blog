package com.monster.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liz
 * @Description 博客数据修改Vo(访问次数，评论次数)
 * @date 2020/6/11-21:08
 */
@Data
@ApiModel(value = "博客数据修改Vo")
public class UpdateNumsVo {

    @ApiModelProperty(value = "博客id")
    private Long id;

    @ApiModelProperty(value = "是否阅读")
    private Boolean isRead = false;

    @ApiModelProperty(value = "是否评论")
    private Boolean isComment = false;

}
