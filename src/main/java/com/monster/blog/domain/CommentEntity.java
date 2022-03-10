package com.monster.blog.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评论id
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comment")
@ApiModel(value="CommentEntity对象", description="评论id")
public class CommentEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "评论id")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "博客id")
    private Long blogId;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "父评论id")
    private Long parentCommentId;

    @ApiModelProperty(value = "父评论用户昵称")
    private String parentNickName;

    @ApiModelProperty(value = "父评论用户id")
    private Long parentUserId;

    @ApiModelProperty(value = "父评论用户头像")
    private String parentAvatar;

    @ApiModelProperty(value = "是否是当前用户评论（0 否 1 是）")
    private Integer adminComment;

    @ApiModelProperty(value = "是否删除（0 未删除 1 已删除）")
    private Integer isRemoved;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;

    @ApiModelProperty(value = "子评论集合")
    @TableField(exist = false)
    private List<CommentEntity> replyComments;

}
