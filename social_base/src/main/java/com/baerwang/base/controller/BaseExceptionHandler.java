package com.baerwang.base.controller;

import com.baerwang.common.entity.Result;
import com.baerwang.common.entity.Constant;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/14 12:30
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result error(Exception ex) {
        ex.printStackTrace();
        return new Result(false, Constant.ERROR, ex.getMessage());
    }
}
