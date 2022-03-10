package com.monster.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liz
 * @Description 注册vo类
 * @date 2020/6/2-17:29
 */
@Data
@ApiModel(value = "注册vo类")
public class RegisterVo {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "手机号码")
    private String phoneNum;

}
