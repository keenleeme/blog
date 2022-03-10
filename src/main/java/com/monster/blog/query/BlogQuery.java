package com.monster.blog.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liz
 * @Description 后台博客查询query类
 * @date 2020/6/5-14:12
 */
@Data
@ApiModel(value = "后台博客查询query类")
public class BlogQuery {

    @ApiModelProperty(value = "博客标题")
    private String title;

    @ApiModelProperty(value = "分类")
    private Integer typeId;

    @ApiModelProperty(value = "标签")
    private String tagIds;

    @ApiModelProperty(value = "标签id集合")
    private List tagIdList;

    @ApiModelProperty(value = "当前页数")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "用户id")
    private Long userId = null;

}
