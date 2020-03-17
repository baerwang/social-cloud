package com.baerwang.gathering.controller;

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
 * @date 2020/2/18 11:28
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return new Result(false, Constant.ERROR, "执行出错");
    }
}
