package com.atguigu.ssyx.activity.service;

import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【coupon_info(优惠券信息)】的数据库操作Service
* @createDate 2025-07-25 17:19:28
*/
public interface CouponInfoService extends IService<CouponInfo> {

    IPage<CouponInfo> selectPageCouponInfo(Long page, Long limit);

    CouponInfo getCouponInfo(Long id);

    Map<String, Object> findCouponRuleList(Long id);

    void saveCouponRule(CouponRuleVo couponRuleVo);

    List<CouponInfo> findCouponByKeyword(String keyword);
}
