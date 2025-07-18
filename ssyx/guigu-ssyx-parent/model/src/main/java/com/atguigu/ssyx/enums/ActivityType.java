package com.atguigu.ssyx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 功能描述: 活动类型
 *
 * @author: Gxf
 * @date: 2025年07月18日 11:08
 */
@Getter
public enum ActivityType {
    FULL_REDUCTION(1,"满减"),
    FULL_DISCOUNT(2,"满量打折" );

    @EnumValue
    private Integer code ;
    private String comment ;

    ActivityType(Integer code, String comment ){
        this.code=code;
        this.comment=comment;
    }
}