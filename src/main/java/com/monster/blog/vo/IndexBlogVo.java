package com.monster.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liz
 * @Description 首页博客展示vo类
 * @date 2020/6/9-17:03
 */
@Data
@ApiModel(value = "首页博客展示vo类")
public class IndexBlogVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "banner图")
    private String bannerImg;

    @ApiModelProperty(value = "描述")
    private String descs;

    @ApiModelProperty(value = "访问次数")
    private Integer readNums;

    @ApiModelProperty(value = "评论次数")
    private Integer commentNums;

    @ApiModelProperty(value = "点赞次数")
    private Integer likeNums;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "分类名称")
    private String typeName;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

}
