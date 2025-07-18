package com.atguigu.ssyx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 功能描述:
 *
 * @author: Gxf
 * @date: 2025年07月18日 11:08
 */
@Getter
public enum CouponStatus {
    NOT_USED(1,"未使用"),
    USED(2,"已使用");

    @EnumValue
    private Integer code ;
    private String comment ;

    CouponStatus(Integer code, String comment ){
        this.code=code;
        this.comment=comment;
    }
}