package com.monster.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangdj
 * @since 2021-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("operate_account")
@ApiModel(value="AccountEntity对象", description="")
public class AccountEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属合作伙伴id")
    private Long partnerId;

    @ApiModelProperty(value = "所属合作伙伴名称")
    private String partnerName;

    @ApiModelProperty(value = "合作伙伴编号")
    private String partnerNo;

    @ApiModelProperty(value = "账户余额")
    private BigDecimal accountAmount;

    @ApiModelProperty(value = "未入账金额")
    private BigDecimal unAmount;

    @ApiModelProperty(value = "本月消费金额")
    private BigDecimal monthConsume;

    @ApiModelProperty(value = "版本号")
    @Version
    private Integer version;

    @ApiModelProperty(value = "是否已删除（0：false;1:true）")
    @TableLogic
    private Integer removed;

    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @ApiModelProperty(value = "最近修改人")
    @TableField(fill = FieldFill.UPDATE)
    private String updatedBy;

    @ApiModelProperty(value = "最近修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;


}
