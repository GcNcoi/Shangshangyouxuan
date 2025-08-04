package com.atguigu.ssyx.activity.service.impl;

import com.atguigu.ssyx.activity.mapper.ActivityRuleMapper;
import com.atguigu.ssyx.activity.mapper.ActivitySkuMapper;
import com.atguigu.ssyx.activity.service.CouponInfoService;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.enums.ActivityType;
import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.atguigu.ssyx.model.activity.ActivityRule;
import com.atguigu.ssyx.model.activity.ActivitySku;
import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.model.order.CartInfo;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.ActivityRuleVo;
import com.atguigu.ssyx.vo.order.CartInfoVo;
import com.atguigu.ssyx.vo.order.OrderConfirmVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.activity.service.ActivityInfoService;
import com.atguigu.ssyx.activity.mapper.ActivityInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【activity_info(活动表)】的数据库操作Service实现
 * @createDate 2025-07-25 17:14:45
 */
@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo>
        implements ActivityInfoService {

    @Autowired
    private ActivityRuleMapper activityRuleMapper;

    @Autowired
    private ActivitySkuMapper activitySkuMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private CouponInfoService couponInfoService;

    @Override
    public IPage<ActivityInfo> selectPageActivityInfo(Page<ActivityInfo> pageParam) {
        IPage<ActivityInfo> activityInfoIPage = baseMapper.selectPage(pageParam, null);
        // 分页查询对象里面获取列表数据
        List<ActivityInfo> activityInfoList = activityInfoIPage.getRecords();
        // 遍历activityInfoList集合，得到每个ActivityInfo对象
        // 调用ActivityType的name()方法，获取枚举类型的名称
        activityInfoList.stream().forEach(item -> {
            item.setActivityTypeString(item.getActivityType().getComment());
        });
        return activityInfoIPage;
    }

    @Override
    public Map<String, Object> findActivityRuleList(Long id) {
        Map<String, Object> result = new HashMap<>();
        // 1. 根据活动id查询，查询规则列表
        LambdaQueryWrapper<ActivityRule> ruleWrapper = new LambdaQueryWrapper<>();
        ruleWrapper.eq(ActivityRule::getActivityId, id);
        List<ActivityRule> activityRuleList = activityRuleMapper.selectList(ruleWrapper);
        result.put("activityRuleList", activityRuleList);
        // 2. 根据活动id查询，查询使用规则商品skuId列表
        LambdaQueryWrapper<ActivitySku> skuWrapper = new LambdaQueryWrapper<ActivitySku>();
        skuWrapper.eq(ActivitySku::getActivityId, id);
        List<ActivitySku> activitySkuList = activitySkuMapper.selectList(skuWrapper);
        // 获取所有的skuId
        List<Long> skuIdList = activitySkuList.stream().map(ActivitySku::getActivityId).collect(Collectors.toList());
        // 2.1 通过远程调用 service-product模块接口，根据skuId列表得到商品信息
        // 远程调用得到
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(skuIdList);
        result.put("skuInfoList", skuInfoList);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveActivityRule(ActivityRuleVo activityRuleVo) {
        // 1.根据活动id删除之前规则数据
        // 1.1 ActivityRule数据删除
        activityRuleMapper.delete(new LambdaQueryWrapper<ActivityRule>().eq(ActivityRule::getActivityId, activityRuleVo.getActivityId()));
        // 1.2 ActivitySku数据删除
        activitySkuMapper.delete(new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, activityRuleVo.getActivityId()));
        // 2.获取规则列表数据
        List<ActivityRule> activityRuleList = activityRuleVo.getActivityRuleList();
        ActivityInfo activityInfo = baseMapper.selectById(activityRuleVo.getActivityId());
        for (ActivityRule activityRule : activityRuleList) {
            activityRule.setActivityId(activityRuleVo.getActivityId());
            activityRule.setActivityType(activityInfo.getActivityType());
            activityRuleMapper.insert(activityRule);
        }
        // 3.获取规则范围数据
        for (ActivitySku activitySku : activityRuleVo.getActivitySkuList()) {
            activitySku.setActivityId(activityRuleVo.getActivityId());
            activitySkuMapper.insert(activitySku);
        }

    }

    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        // 1. 根据关键字查询sku匹配内容列表
        // 1.1 service-product模块创建接口，根据关键字查询sku匹配内容列表
        // 1.2 service-activity远程调用得到sku内容列表
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoByKeyWord(keyword);
        // 判断:如果根据关键字查询不到匹配内容，直接返回空集合
        if (skuInfoList.isEmpty()) {
            return skuInfoList;
        }
        // 获取所有skuId
        List<Long> skuIdList = skuInfoList.stream().map(SkuInfo::getId).collect(Collectors.toList());
        // 2. 判断添加商品之前是否参加过活动，如果之前参加过，活动正在进行中，排除商品
        // 2.1 查询两张表判断 activity_info和activity_sku，编写sql语句
        List<Long> existSkuIdList = baseMapper.selectSkuIdListExist(skuIdList);
        // 2.2 判断逻辑处理:排除已经参加活动商品
        return skuInfoList.stream()
                .filter(sku -> !existSkuIdList.contains(sku.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<Long, List<String>> findActivity(List<Long> skuIdList) {
        Map<Long, List<String>> result = new HashMap<>();
        // 1. 遍历skuIdList
        skuIdList.forEach(skuId -> {
            // 2. 根据skuId进行查询，查询sku对应活动里面规则列表
            List<ActivityRule> activityRuleList = baseMapper.findActivityRule(skuId);
            // 3. 数据封装规则名称
            if (!CollectionUtils.isEmpty(activityRuleList)) {
                // 规则名称拼接
                // 合并流操作：一次遍历完成设置和收集，减少迭代次数
                List<String> ruleList = activityRuleList.stream()
                        .peek(activityRule -> activityRule.setRuleDesc(this.getRuleDesc(activityRule))) // 设置规则描述（副作用操作）
                        .map(ActivityRule::getRuleDesc) // 提取规则描述
                        .collect(Collectors.toList()); // 收集结果
                result.put(skuId, ruleList);
            }
        });
        return result;
    }

    @Override
    public Map<String, Object> findActivityAndCoupon(Long skuId, Long userId) {
        Map<String, Object> map = new HashMap<>();
        // 1. 根据skuId获取sku营销活动，一个活动具有多个规则
        List<ActivityRule> activityRuleList = this.findActivityRuleBySkuId(skuId);
        // 2. 根据skuId和userId查询有优惠卷信息
        List<CouponInfo> couponInfoList = couponInfoService.findCouponInfo(skuId, userId);
        // 3. 封装到map集合
        map.put("activityRuleList", activityRuleList);
        map.put("couponInfoList", couponInfoList);
        return map;
    }

    // 根据skuId获取活动规则数据
    @Override
    public List<ActivityRule> findActivityRuleBySkuId(Long skuId) {
        List<ActivityRule> activityRuleList = baseMapper.findActivityRule(skuId);
        for (ActivityRule activityRule : activityRuleList) {
            String ruleDesc = this.getRuleDesc(activityRule);
            activityRule.setRuleDesc(ruleDesc);
        }
        return activityRuleList;
    }

    @Override
    public OrderConfirmVo findCartActivityAndCoupon(List<CartInfo> cartInfoList, Long userId) {
        // 1. 获取购物车每个购物项参与活动，根据活动规则分组，一个规则对应多个商品
        List<CartInfoVo> cartInfoVoList = this.findCartActivityList(cartInfoList);
        // 2. 计算参与活动之后最终金额

        // 3. 获取购物车可以使用优惠券列表

        // 4. 计算商品使用优惠券之后金额，一次只能使用一张优惠券

        // 5. 计算没有参与活动，没有使用优惠券原始金额

        // 6. 参与活动，使用优惠券总金额

        // 7. 封装数据到CartInfoVO返回
        return null;
    }

    // 获取购物车对应规则数据
    @Override
    public List<CartInfoVo> findCartActivityList(List<CartInfo> cartInfoList) {
        List<CartInfoVo> cartInfoVoList = new ArrayList<>();
        // 获取所有skuId
        List<Long> skuIdList = cartInfoList.stream().map(CartInfo::getSkuId).collect(Collectors.toList());
        // 根据所有skuId获取参与活动
        List<ActivitySku> activitySkuList = baseMapper.selectCartActivity(skuIdList);
        // 根据活动进行分组，每个活动里面有哪些skuId信息
        // map里面key是分组字段 活动id；value是sku列表数据 set集合
        Map<Long, Set<Long>> activityIdToSkuIdListMap = activitySkuList.stream().collect(Collectors.groupingBy(ActivitySku::getActivityId, Collectors.mapping(ActivitySku::getSkuId, Collectors.toSet())));
        // 获取活动规则数据
        Map<Long, List<ActivityRule>> activityIdToActivityRuleListMap = new HashMap<>();
        Set<Long> activityIdSet = activitySkuList.stream().map(ActivitySku::getActivityId).collect(Collectors.toSet());
        if(!CollectionUtils.isEmpty(activityIdSet)) {
            LambdaQueryWrapper<ActivityRule> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(ActivityRule::getConditionAmount, ActivityRule::getConditionNum);
            wrapper.in(ActivityRule::getActivityId, activityIdSet);
            List<ActivityRule> activityRuleList = activityRuleMapper.selectList(wrapper);
            // 封装到activityIdToActivityRuleListMap里面
            // 根据活动id进行分组
            activityIdToActivityRuleListMap = activityRuleList.stream().collect(Collectors.groupingBy(activityRule -> activityRule.getActivityId()));
        }
        // 参与活动的购物项的skuId
        Set<Long> activitySkuIdSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(activityIdToSkuIdListMap)) {
            Iterator<Map.Entry<Long, Set<Long>>> iterator = activityIdToSkuIdListMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Long, Set<Long>> entry = iterator.next();
                Set<Long> skuIdSet = entry.getValue();
                // 获取当前活动对应的购物项列表
                List<CartInfo> currentActivityCartInfoList = cartInfoList.stream().filter(cartInfo -> skuIdSet.contains(cartInfo.getSkuId())).collect(Collectors.toList());
                // 把交集的skuId添加到activitySkuIdSet
                activitySkuIdSet.addAll(skuIdSet);
            }
        }
        // 没有参与活动的购物项的skuId
        return Collections.emptyList();
    }

    // 构造规则名称的方法
    private String getRuleDesc(ActivityRule activityRule) {
        ActivityType activityType = activityRule.getActivityType();
        StringBuffer ruleDesc = new StringBuffer();
        if (activityType == ActivityType.FULL_REDUCTION) {
            ruleDesc
                    .append("满")
                    .append(activityRule.getConditionAmount())
                    .append("元减")
                    .append(activityRule.getBenefitAmount())
                    .append("元");
        } else {
            ruleDesc
                    .append("满")
                    .append(activityRule.getConditionNum())
                    .append("元打")
                    .append(activityRule.getBenefitDiscount())
                    .append("折");
        }
        return ruleDesc.toString();
    }
}




