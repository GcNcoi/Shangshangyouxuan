package com.atguigu.ssyx.activity.mapper;

import com.atguigu.ssyx.model.activity.CouponInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* @author Administrator
* @description 针对表【coupon_info(优惠券信息)】的数据库操作Mapper
* @createDate 2025-07-25 17:19:28
* @Entity com.atguigu.ssyx.activity.CouponInfo
*/
public interface CouponInfoMapper extends BaseMapper<CouponInfo> {

    List<CouponInfo> selectCouponInfoList(@Param("skuId") Long skuId, @Param("categoryId") Long categoryId, @Param("userId") Long userId);

    List<CouponInfo> selectCartCouponInfoList(@Param("userId") Long userId);

}




