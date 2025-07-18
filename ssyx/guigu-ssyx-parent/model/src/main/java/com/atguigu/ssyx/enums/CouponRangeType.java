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
public enum CouponRangeType {
    ALL(1,"通用"),
    SKU(2,"SKU" ),
    CATEGORY(3,"分类");

    @EnumValue
    private Integer code ;
    private String comment ;

    CouponRangeType(Integer code, String comment ){
        this.code=code;
        this.comment=comment;
    }

}