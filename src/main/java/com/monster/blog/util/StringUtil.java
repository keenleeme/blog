package com.monster.blog.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author liz
 * @Description 字符串工具类
 * @date 2020/6/5-17:24
 */
public class StringUtil {

    /**
     * 去掉字符串指定的前缀
     * @param str 字符串名称
     * @param prefix 前缀数组
     * @return
     */
    public static String removePrefix(String str, String[] prefix) {
        if (StringUtils.isEmpty(str)) {
            return "";
        } else {
            if (null != prefix) {
                String[] prefixArray = prefix;

                for(int i = 0; i < prefix.length; ++i) {
                    String pf = prefixArray[i];
                    if (str.toLowerCase().matches("^" + pf.toLowerCase() + ".*")) {
                        return str.substring(pf.length());//截取前缀后面的字符串
                    }
                }
            }

            return str;
        }
    }

}
