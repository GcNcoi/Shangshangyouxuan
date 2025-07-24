package com.atguigu.ssyx.search.service.impl;

import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.enums.SkuType;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.model.search.SkuEs;
import com.atguigu.ssyx.search.repository.SkuRepository;
import com.atguigu.ssyx.search.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述
 *
 * @author: Gxf
 * @date: 2025年07月24日 16:38
 */
@Slf4j
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private ProductFeignClient productFeignClient;

    // 上架
    @Override
    public void upperSku(Long skuId) {
        // 1.通过远程调用，根据skuId获取相关信息
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if (skuInfo == null) {
            return;
        }
        Category category = productFeignClient.getCategory(skuInfo.getCategoryId());
        // 2.获取数据封装skuEs对象
        SkuEs skuEs = new SkuEs();
        // 2.1封装分类
        if (category != null) {
            skuEs.setCategoryId(category.getId());
            skuEs.setCategoryName(category.getName());
        }
        // 2.2封装sku信息部分
        skuEs.setId(skuInfo.getId());
        skuEs.setKeyword(skuInfo.getSkuName()+","+skuEs.getCategoryName());
        skuEs.setWareId(skuInfo.getWareId());
        skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
        skuEs.setImgUrl(skuInfo.getImgUrl());
        skuEs.setTitle(skuInfo.getSkuName());
        if(skuInfo.getSkuType() == SkuType.COMMON.getCode()) {
            skuEs.setSkuType(0);
            skuEs.setPrice(skuInfo.getPrice().doubleValue());
            skuEs.setStock(skuInfo.getStock());
            skuEs.setSale(skuInfo.getSale());
            skuEs.setPerLimit(skuInfo.getPerLimit());
        }
        // 3.调用方法添加Es
        skuRepository.save(skuEs);
    }

    // 下架
    @Override
    public void lowerSku(Long skuId) {
        skuRepository.deleteById(skuId);
    }
}
