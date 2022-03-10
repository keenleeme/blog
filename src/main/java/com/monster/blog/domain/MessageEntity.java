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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 私信表
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("message")
@ApiModel(value="MessageEntity对象", description="私信表")
public class MessageEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "私信id")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "父私信id")
    private Long parentMessageId;

    @ApiModelProperty(value = "父私信昵称")
    private String parentNickName;

    @ApiModelProperty(value = "父私信用户id")
    private Long parentUserId;

    @ApiModelProperty(value = "是否是当前管理员私信（0 否 1 是）")
    private Integer adminMessage;

    @ApiModelProperty(value = "是否删除")
    private Integer isRemoved;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;

    @TableField(exist = false)
    private List<MessageEntity> replyMessage;
}
