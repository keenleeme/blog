package com.monster.blog.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author liz
 * @Description TODO
 * @date 2020/8/6-14:31
 */
@Data
@Document(collection = "test")
public class Test {

    @Id
    private String id;

    private Integer price;

    private String name;

    private String info;

    private String publish;

    private Date createTime;

    private Date updateTime;

}
