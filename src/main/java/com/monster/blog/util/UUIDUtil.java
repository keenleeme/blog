package com.monster.blog.util;

import java.util.UUID;

/**
 * @author liz
 * @Description UUID工具类
 * @date 2020/8/5-17:07
 */
public class UUIDUtil {

    /**
     * 生成32的随机盐值
     */
    public static String createSalt() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
