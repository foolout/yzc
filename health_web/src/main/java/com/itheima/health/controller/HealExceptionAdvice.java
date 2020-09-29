package com.itheima.health.controller;

import com.itheima.health.entity.Result;
import com.itheima.health.exception.HealthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常处理
@RestControllerAdvice
public class HealExceptionAdvice {
    private static final Logger log = LoggerFactory.getLogger(HealExceptionAdvice.class);

    //自定义抛出的异常处理

    @ExceptionHandler(HealthException.class)
    public Result handleHealthException(HealthException e){
        //我们自己抛出的异常信息，把异常信息包装下返回
        log.error("违反业务逻辑",e);
        return new Result(false,e.getMessage());
    }

    //所有未知的异常处理

    @ExceptionHandler(Exception.class)
    public Result handleHealthException(Exception e){
        log.error("发生未知异常",e);
        return new Result(false,"发生未知错误，操作失败，请联系管理员");
    }
}
