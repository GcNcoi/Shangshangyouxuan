package com.atguigu.ssyx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 功能描述: MybatisPlus配置类
 *
 * @author: Gxf
 * @date: 2025年07月18日 11:08
 */
@Getter
public enum CouponType {
    FULL_REDUCTION(1,"满减"),
    CASH(2,"现金卷");

    @EnumValue
    private Integer code;
    private String comment ;

    CouponType(Integer code, String comment ){
        this.code=code;
        this.comment=comment;
    }
}