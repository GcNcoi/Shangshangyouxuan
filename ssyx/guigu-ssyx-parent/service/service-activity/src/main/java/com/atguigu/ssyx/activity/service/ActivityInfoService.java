package com.atguigu.ssyx.activity.service;

import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.atguigu.ssyx.model.activity.ActivityRule;
import com.atguigu.ssyx.model.order.CartInfo;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.ActivityRuleVo;
import com.atguigu.ssyx.vo.order.CartInfoVo;
import com.atguigu.ssyx.vo.order.OrderConfirmVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【activity_info(活动表)】的数据库操作Service
* @createDate 2025-07-25 17:14:45
*/
public interface ActivityInfoService extends IService<ActivityInfo> {

    /**
     * 获取营销活动分页列表
     * @param pageParam
     * @return
     */
    IPage<ActivityInfo> selectPageActivityInfo(Page<ActivityInfo> pageParam);

    /**
     * 根据活动id获取活动规则数据
     * @param id
     * @return
     */
    Map<String, Object> findActivityRuleList(Long id);

    /**
     * 在活动里面添加规则数据
     * @param activityRuleVo
     */
    void saveActivityRule(ActivityRuleVo activityRuleVo);

    /**
     * 根据关键字查询匹配sku信息
     * @param keyword
     * @return
     */
    List<SkuInfo> findSkuInfoByKeyword(String keyword);

    /**
     * 根据skuId列表获取促销信息
     * @param skuIdList
     * @return
     */
    Map<Long, List<String>> findActivity(List<Long> skuIdList);

    /**
     * 根据skuId获取促销与优惠券信息
     * @param skuId
     * @param userId
     * @return
     */
    Map<String, Object> findActivityAndCoupon(Long skuId, Long userId);

    /**
     * 根据skuId获取活动规则列表信息
     * @param skuId
     * @return
     */
    List<ActivityRule> findActivityRuleBySkuId(Long skuId);

    /**
     * 获取购物车满足条件的促销与优惠券信息
     * @param cartInfoList
     * @param userId
     * @return
     */
    OrderConfirmVo findCartActivityAndCoupon(List<CartInfo> cartInfoList, Long userId);

    /**
     * 获取购物车满足条件的活动信息
     * @param cartInfoList
     * @return
     */
    List<CartInfoVo> findCartActivityList(List<CartInfo> cartInfoList);
}
