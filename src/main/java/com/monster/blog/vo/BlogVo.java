package com.monster.blog.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liz
 * @Description 后台管理博客管理展示vo类
 * @date 2020/6/5-15:02
 */
@Data
@Api(value = "博客管理展示vo类")
public class BlogVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "首页图")
    private String bannerImg;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "分类")
    private String typeName;

    @ApiModelProperty(value = "标签id,用','拼接")
    private String tagIds;

    @ApiModelProperty(value = "标签名称,用','拼接")
    private String tagName;

    @ApiModelProperty(value = "标签名称,用','拼接")
    @TableField(exist = false)
    private List tagNames;

    @ApiModelProperty(value = "是否原创")
    private Integer isOriginal;

    @ApiModelProperty(value = "是否推荐")
    private Integer isRecommend;

    @ApiModelProperty(value = "是否公开")
    private Integer isPrivacy;

    @ApiModelProperty(value = "支持评论")
    private Integer isCommentable;

    @ApiModelProperty(value = "支持点赞")
    private Integer isLike;

    @ApiModelProperty(value = "转载声明")
    private Integer shareStatement;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "访问数")
    private Integer readNums;

    @ApiModelProperty(value = "评论数")
    private Integer commentNums;

    @ApiModelProperty(value = "点赞数")
    private Integer likeNums;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private String updatedTime;

}
