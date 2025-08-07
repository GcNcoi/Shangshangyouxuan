package com.atguigu.ssyx.activity.service;

import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.model.order.CartInfo;
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

    /**
     * 获取优惠券分页列表
     * @param page
     * @param limit
     * @return
     */
    IPage<CouponInfo> selectPageCouponInfo(Long page, Long limit);

    /**
     * 根据id查询优惠券
     * @param id
     * @return
     */
    CouponInfo getCouponInfo(Long id);

    /**
     * 根据优惠券id查询规则列表
     * @param id
     * @return
     */
    Map<String, Object> findCouponRuleList(Long id);

    /**
     * 新增优惠券规则
     * @param couponRuleVo
     */
    void saveCouponRule(CouponRuleVo couponRuleVo);

    /**
     * 根据关键字查询匹配优惠券信息
     * @param keyword
     * @return
     */
    List<CouponInfo> findCouponByKeyword(String keyword);

    /**
     * 根据skuId和userId查询有优惠卷信息
     * @param skuId
     * @param userId
     * @return
     */
    List<CouponInfo> findCouponInfo(Long skuId, Long userId);

    /**
     * 获取购物车可以使用优惠券列表
     * @param cartInfoList
     * @param userId
     * @return
     */
    List<CouponInfo> findCartCouponInfo(List<CartInfo> cartInfoList, Long userId);

    /**
     * 获取购物车对应优惠券
     * @param cartInfoList
     * @param couponId
     * @return
     */
    CouponInfo findRangeSkuIdList(List<CartInfo> cartInfoList, Long couponId);

    /**
     * 更新优惠券使用状态
     * @param couponId
     * @param userId
     * @param orderId
     */
    void updateCouponInfoUseStatus(Long couponId, Long userId, Long orderId);
}
