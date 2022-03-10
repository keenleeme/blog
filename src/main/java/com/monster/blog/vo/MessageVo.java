package com.monster.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liz
 * @Description 留言板Vo类
 * @date 2020/6/24-0:32
 */
@Data
@ApiModel(value = "留言板Vo")
public class MessageVo {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "父私信id")
    private Long parentMessageId;

    @ApiModelProperty(value = "私信内容")
    private String content;

    @ApiModelProperty(value = "是否删除")
    private Integer removed = 0;
}
