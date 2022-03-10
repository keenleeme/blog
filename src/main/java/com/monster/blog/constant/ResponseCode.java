package com.monster.blog.constant;

/**
 * @author liz
 * @Description TODO
 * @date 2020/8/5-14:10
 */
public interface ResponseCode {

    /*请求成功*/
    String SUCCESS = "200";
    String NOT_LOGIN = "210";
    /*参数错误*/
    String ARGERROR = "230";
    /*请求失败*/
    String ERROR = "500";
    // 短信认证失败
    String SMS_AUTH_ERR = "233";
    // 登录/修改密码时用户名不存在。
    String NOT_USERNAME = "233";
    // 登录时密码错误。
    String PASS_WORD_ERROR = "234";
    //用户已被禁用
    String DISABLE_USER = "236";
    //数据已存在
    String DATA_REPETITION = "238";
}
