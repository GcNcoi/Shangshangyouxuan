package com.atguigu.ssyx.activity.service.impl;

import com.atguigu.ssyx.activity.mapper.ActivityRuleMapper;
import com.atguigu.ssyx.activity.mapper.ActivitySkuMapper;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.atguigu.ssyx.model.activity.ActivityRule;
import com.atguigu.ssyx.model.activity.ActivitySku;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.ActivityRuleVo;
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

import java.util.*;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【activity_info(活动表)】的数据库操作Service实现
* @createDate 2025-07-25 17:14:45
*/
@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo>
    implements ActivityInfoService{

    @Autowired
    private ActivityRuleMapper activityRuleMapper;

    @Autowired
    private ActivitySkuMapper activitySkuMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

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
}




