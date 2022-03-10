package com.monster.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liz
 * @Description 图片Vo类
 * @date 2020/6/27-19:57
 */
@Data
@ApiModel(value = "图片Vo类")
public class PictureVo {

    @ApiModelProperty(value = "图片id")
    private String id;

    @ApiModelProperty(value = "图片名称")
    private String pictureName;

    @ApiModelProperty(value = "图片日期")
    private String pictureTime;

    @ApiModelProperty(value = "图片地址")
    private String url;

    @ApiModelProperty(value = "图片描述")
    private String desc;

    @ApiModelProperty(value = "用户id")
    private Long userId;

}
