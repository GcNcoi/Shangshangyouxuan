package com.atguigu.ssyx.common.exception;

import com.atguigu.ssyx.common.result.ResultCodeEnum;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 功能描述: 自定义异常类
 *
 * @author: Gxf
 * @date: 2025年07月18日 11:19
 */
@Data
@ToString
public class SsyxException extends RuntimeException {

    //异常状态码
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    public SsyxException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public SsyxException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

}
