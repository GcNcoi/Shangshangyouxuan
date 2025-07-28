package com.atguigu.ssyx.common.exception;

import com.atguigu.ssyx.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能描述: 全局异常处理类
 *
 * @author: Gxf
 * @date: 2025年07月18日 11:14
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) // 处理Exception异常
    @ResponseBody // 响应体,返回json数据
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail(null);
    }

    /**
     * 自定义异常处理方法
     * @param e
     * @return
     */
    @ExceptionHandler(SsyxException.class)
    @ResponseBody
    public Result error(SsyxException e){
        return Result.build(null, e.getCode(), e.getMessage());
    }

}
