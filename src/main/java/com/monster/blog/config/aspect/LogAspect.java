package com.monster.blog.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author liz
 * @Description aop日志处理
 * @date 2020/5/27-21:50
 */
@Aspect
@Slf4j
@Component
public class LogAspect {

    @Pointcut("execution(* com.monster.blog.web.*.*(..))")
    public void log() {

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] params = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,params);

        log.info("Request: {}",requestLog);
    }

    @After("log()")
    public void doAfter() {
        log.info("在方法之后执行 ---- doAfter ----");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        log.info("Result: {}", result);
    }

    /**
     * 请求日志类
     */
    public class RequestLog {

        private String url;

        private String ip;

        private String classMethod;

        private Object[] params;

        public RequestLog(String url, String ip, String classMethod, Object[] params) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.params = params;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", params=" + Arrays.toString(params) +
                    '}';
        }
    }
}
