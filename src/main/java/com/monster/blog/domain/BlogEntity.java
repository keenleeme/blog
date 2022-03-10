package com.monster.blog.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 博客信息表
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("blog")
@ApiModel(value="BlogEntity对象", description="博客信息表")
public class BlogEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "类型id")
    private Long typeId;

    @ApiModelProperty(value = "标签id")
    private String tagIds;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "banner图地址")
    private String bannerImg;

    @ApiModelProperty(value = "描述")
    private String descs;

    @ApiModelProperty(value = "转载声明")
    private Integer shareStatement;

    @ApiModelProperty(value = "是否原创（0 原创 1 转载）")
    private Integer isOriginal;

    @ApiModelProperty(value = "状态（0 草稿 1 审核中 2 发布 4 下架 ）")
    private Integer status;

    @ApiModelProperty(value = "是否推荐（0 否 1 是）")
    private Integer isRecommend;

    @ApiModelProperty(value = "是否私人（0 私人 1 公开）")
    private Integer isPrivacy;

    @ApiModelProperty(value = "是否可评论（0 否 1 是）")
    private Integer isCommentable;

    @ApiModelProperty(value = "是否可点赞（0 否 1 是）")
    private Integer isLike;

    @ApiModelProperty(value = "阅读次数")
    private Integer readNums;

    @ApiModelProperty(value = "评论次数")
    private Integer commentNums;

    @ApiModelProperty(value = "点赞次数")
    private Integer likeNums;

    @ApiModelProperty(value = "是否删除（0 未删除 1 已删除）")
    private Integer isRemoved;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    @ApiModelProperty(value = "更新人")
    @TableField(fill = FieldFill.UPDATE)
    private Long updatedBy;

    /**
     * 用户角色id
     */
    @TableField(exist = false)
    private Long roleId;


}
