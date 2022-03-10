package com.monster.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 账户充值vo
 *
 * @author liz
 * @date 2021/3/4-15:45
 */
@Data
@ApiModel(value = "账户充值vo")
public class AccountVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal chargeAmount;

}
