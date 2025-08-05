package com.atguigu.ssyx.activity.service.impl;

import com.atguigu.ssyx.activity.mapper.CouponRangeMapper;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.enums.CouponRangeType;
import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.model.activity.CouponRange;
import com.atguigu.ssyx.model.order.CartInfo;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.activity.service.CouponInfoService;
import com.atguigu.ssyx.activity.mapper.CouponInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【coupon_info(优惠券信息)】的数据库操作Service实现
* @createDate 2025-07-25 17:19:28
*/
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo>
    implements CouponInfoService{

    @Autowired
    private CouponRangeMapper couponRangeMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public IPage<CouponInfo> selectPageCouponInfo(Long page, Long limit) {
        Page<CouponInfo> pageParam = new Page<>(page, limit);
        Page<CouponInfo> couponInfoPage = baseMapper.selectPage(pageParam, null);
        List<CouponInfo> couponInfoList = couponInfoPage.getRecords();
        couponInfoList.stream().forEach(item -> {
            item.setCouponTypeString(item.getCouponType().getComment());
            CouponRangeType rangeType = item.getRangeType();
            if (rangeType != null) {
                item.setRangeTypeString(rangeType.getComment());
            }
        });
        return baseMapper.selectPage(pageParam, null);
    }

    @Override
    public CouponInfo getCouponInfo(Long id) {
        CouponInfo couponInfo = baseMapper.selectById(id);
        couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
        if (couponInfo.getRangeType() != null) {
            couponInfo.setRangeTypeString(couponInfo.getRangeType().getComment());
        }
        return couponInfo;
    }

    @Override
    public Map<String, Object> findCouponRuleList(Long id) {
        // 1. 根据优惠券id查询优惠券基本信息
        CouponInfo couponInfo = baseMapper.selectById(id);
        // 2. 根据优惠券id查询coupon_range 查询里面对应的range_id
        LambdaQueryWrapper<CouponRange> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CouponRange::getCouponId, id);
        List<CouponRange> couponRangeList = couponRangeMapper.selectList(queryWrapper);
        // 2.1 如果规则类型为SKU，range_id 对应 sku_id
        // 2.2 如果规则类型为CATEGORY，range_id 对应 category_id
        List<Long> randIdList = couponRangeList.stream().map(CouponRange::getRangeId).collect(Collectors.toList());

        // 3. 分别判断封装不同的数据
        Map<String, Object> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(randIdList)) {
            if (couponInfo.getRangeType() == CouponRangeType.SKU) {
                // 3.1 如果规则类型为SKU，远程调用根据多个skuId值获取对应sku信息
                List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(randIdList);
                map.put("skuInfoList", skuInfoList);
            } else if (couponInfo.getRangeType() == CouponRangeType.CATEGORY) {
                // 3.2 如果规则类型为CATEGORY，远程调用根据多个categoryId值获取对应category信息
                List<Category> categoryList = productFeignClient.findCategoryList(randIdList);
                map.put("categoryList", categoryList);
            }
        }
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCouponRule(CouponRuleVo couponRuleVo) {
        // 1.根据优惠券id删除规则数据
        LambdaQueryWrapper<CouponRange> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CouponRange::getCouponId, couponRuleVo.getCouponId());
        couponRangeMapper.delete(queryWrapper);
        // 2.更新优惠券基本信息
        CouponInfo couponInfo = baseMapper.selectById(couponRuleVo.getCouponId());
        couponInfo.setRangeType(couponRuleVo.getRangeType());
        couponInfo.setConditionAmount(couponRuleVo.getConditionAmount());
        couponInfo.setAmount(couponRuleVo.getAmount());
        couponInfo.setRangeDesc(couponRuleVo.getRangeDesc());
        baseMapper.updateById(couponInfo);
        // 3.添加优惠券新规则数据
        List<CouponRange> couponRangeList = couponRuleVo.getCouponRangeList();
        couponRangeList.forEach(item -> {
            item.setCouponId(couponRuleVo.getCouponId());
            couponRangeMapper.insert(item);
        });
    }

    @Override
    public List<CouponInfo> findCouponByKeyword(String keyword) {
        LambdaQueryWrapper<CouponInfo> couponInfoQueryWrapper = new LambdaQueryWrapper<>();
        couponInfoQueryWrapper.like(CouponInfo::getCouponName, keyword);
        return baseMapper.selectList(couponInfoQueryWrapper);
    }

    @Override
    public List<CouponInfo> findCouponInfo(Long skuId, Long userId) {
        // 远程调用：根据skuId获取skuInfo
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if(null == skuInfo) return new ArrayList<>();
        // 根据条件查询：skuId + 分类ID + userId
        List<CouponInfo> couponInfoList = baseMapper.selectCouponInfoList(skuInfo.getId(), skuInfo.getCategoryId(), userId);
        return couponInfoList;
    }

    @Override
    public List<CouponInfo> findCartCouponInfo(List<CartInfo> cartInfoList, Long userId) {
        // 1. 根据userId获取用户优惠券信息
        List<CouponInfo> usrAllCouponInfoList = baseMapper.selectCartCouponInfoList(userId);
        if (CollectionUtils.isEmpty(usrAllCouponInfoList)) {
            return new ArrayList<CouponInfo>();
        }
        // 2. 从第一步返回list集合中，获取所有优惠券id列表
        List<Long> couponIdList = usrAllCouponInfoList.stream().map(couponInfo -> couponInfo.getId()).collect(Collectors.toList());
        // 3. 查询优惠券对应范围
        LambdaQueryWrapper<CouponRange> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(CouponRange::getCouponId, couponIdList);
        List<CouponRange> couponRangeList = couponRangeMapper.selectList(wrapper);
        // 4. 获取优惠券id，对应skuId列表
        Map<Long,List<Long>> couponIdTuSkuIdMap = this.findCouponIdTuSkuIdMap(cartInfoList, couponRangeList);
        // 5. 遍历全部优惠券集合，判断优惠券类型(全场通用；sku；分类)

        // 6. 返回List<CouponInfo>
        return Collections.emptyList();
    }

    private Map<Long, List<Long>> findCouponIdTuSkuIdMap(List<CartInfo> cartInfoList, List<CouponRange> couponRangeList) {
        Map<Long,List<Long>> couponIdTuSkuIdMap = new HashMap<>();
        // couponRangeList数据处理，根据优惠券id分组
        Map<Long, List<CouponRange>> couponIdToCouponRangeMap = couponRangeList.stream().collect(Collectors.groupingBy(CouponRange::getCouponId));
        Iterator<Map.Entry<Long, List<CouponRange>>> iterator = couponIdToCouponRangeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, List<CouponRange>> entry = iterator.next();
            Long couponId = entry.getKey();
            // 创建集合set
            Set<Long> skuIdSet = new HashSet<>();
            for (CartInfo cartInfo : cartInfoList) {
                for(CouponRange couponRange : couponRangeList) {
                    if(CouponRangeType.SKU == couponRange.getRangeType() && couponRange.getRangeId().longValue() == cartInfo.getSkuId().intValue()) {
                        skuIdSet.add(cartInfo.getSkuId());
                    } else if(CouponRangeType.CATEGORY == couponRange.getRangeType() && couponRange.getRangeId().longValue() == cartInfo.getCategoryId().intValue()) {
                        skuIdSet.add(cartInfo.getSkuId());
                    } else {

                    }
                }
            }
            couponIdTuSkuIdMap.put(couponId, new ArrayList<>(skuIdSet));
        }
        return couponIdTuSkuIdMap;
    }
}