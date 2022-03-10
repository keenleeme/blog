package com.monster.blog.domain;

import com.alibaba.fastjson.JSONObject;
import com.monster.blog.constant.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @author liz
 * @Description TODO
 * @date 2020/8/5-14:03
 */
@Slf4j
public class RestfulResult<T> implements Serializable {

    private static final long serialVersionUID = -251600843124382096L;

    /**
     * 操作是否成功
     **/
    private String returnFlag;

    /**
     * 操作后的信息
     **/
    private String returnInfo;

    /**
     * 返回的数据
     **/
    private T data;

    public static <T> RestfulResult<T> success(T data) {
        RestfulResult restfulResult = new RestfulResult();
        restfulResult.setReturnInfo("成功");
        restfulResult.setData(data);
        restfulResult.setReturnFlag(ResponseCode.SUCCESS);
        return restfulResult;
    }

    public static <T> RestfulResult<T> success(String returnInfo, T data) {
        RestfulResult restfulResult = new RestfulResult();
        restfulResult.setReturnInfo(returnInfo);
        restfulResult.setData(data);
        restfulResult.setReturnFlag(ResponseCode.SUCCESS);
        return restfulResult;
    }

    public static <T> RestfulResult<T> error(String errorResult) {
        RestfulResult restfulResult = new RestfulResult();
        restfulResult.setReturnFlag(ResponseCode.ERROR);
        restfulResult.setReturnInfo(errorResult);
        return restfulResult;
    }

    public static <T> RestfulResult<T> paramError(String errorResult) {
        RestfulResult restfulResult = new RestfulResult();
        restfulResult.setReturnFlag(ResponseCode.ARGERROR);
        restfulResult.setReturnInfo(errorResult);
        return restfulResult;
    }

    public static <T> RestfulResult<T> unauthorizedError(String errorResult) {
        RestfulResult restfulResult = new RestfulResult();
        restfulResult.setReturnFlag(ResponseCode.NOT_LOGIN);
        restfulResult.setReturnInfo(errorResult);
        return restfulResult;
    }

    public static <T> RestfulResult<T> error(String errorResult, String errorCode) {
        RestfulResult restfulResult = new RestfulResult();
        restfulResult.setReturnFlag(errorCode);
        restfulResult.setReturnInfo(errorResult);
        return restfulResult;
    }

    public static void responseResult(HttpServletResponse response, String logs) throws IOException {
        RestfulResult restfulResult = new RestfulResult();
        restfulResult.setData(new String[0]);
        restfulResult.setReturnFlag(ResponseCode.ERROR);
        restfulResult.setReturnInfo(logs);
        log.info(logs);
        response.setStatus(500);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(restfulResult));
    }

    public static void responseResult(HttpServletResponse response, String logs, int responseCode) throws IOException {
        RestfulResult restfulResult = new RestfulResult();
        restfulResult.setData(new String[0]);
        restfulResult.setReturnFlag(responseCode + "");
        restfulResult.setReturnInfo(logs);
        log.info(logs);
        response.setStatus(responseCode);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(restfulResult));
    }

    public static RestfulResult validatorParamError(BindingResult... errors) {
        StringBuilder builder = new StringBuilder();
        for (BindingResult error : errors) {
            if (error.hasErrors()) {
                error.getAllErrors()
                        .forEach(objectError -> builder.append(objectError.getDefaultMessage()).append(" "));
            }
        }
        String arg = builder.toString();
        return RestfulResult.error(arg, ResponseCode.ARGERROR);
    }

    public String getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(String returnInfo) {
        this.returnInfo = returnInfo;
    }

    public String getReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(String returnFlag) {
        this.returnFlag = returnFlag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
