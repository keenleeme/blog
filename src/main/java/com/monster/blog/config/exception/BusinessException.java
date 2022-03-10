package com.monster.blog.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author liz
 * @Description 业务逻辑异常类
 * @date 2020/5/29-16:24
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BusinessException extends Exception {

    public BusinessException() {

    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message,Throwable cause) {
        super(message,cause);
    }
}
