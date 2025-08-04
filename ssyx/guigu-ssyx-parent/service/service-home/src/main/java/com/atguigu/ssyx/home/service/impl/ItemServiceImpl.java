package com.atguigu.ssyx.home.service.impl;

import com.atguigu.ssyx.activity.client.ActivityFeignClient;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.client.search.SkuFeignClient;
import com.atguigu.ssyx.home.config.ThreadPoolConfig;
import com.atguigu.ssyx.home.service.ItemService;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @BelongsProject: guigu-ssyx-parent
 * @BelongsPackage: com.atguigu.ssyx.home.service.impl
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-08-02  11:32
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private ActivityFeignClient activityFeignClient;

    @Autowired
    private SkuFeignClient skuFeignClient;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public Map<String, Object> item(Long skuId, Long userId) {
        Map<String, Object> result = new HashMap<>();
        // skuId查询
        CompletableFuture<Map<String, Object>> skuInfoCompletableFuture = CompletableFuture.supplyAsync(() -> {
            // 远程调用获取sku对应数据
            SkuInfoVo skuInfoVo = productFeignClient.getSkuInfoVo(skuId);
            result.put("skuInfoVo", skuInfoVo);
            return result;
        }, threadPoolExecutor);
        // sku对应优惠券信息
        CompletableFuture<Void> activityCompletableFuture = CompletableFuture.runAsync(() -> {
            // 远程调用优惠券信息
            Map<String, Object> activityMap = activityFeignClient.findActivityAndCoupon(skuId, userId);
            result.putAll(activityMap);
        }, threadPoolExecutor);
        // 更新商品热度
        CompletableFuture<Void> hotCompletableFuture = CompletableFuture.runAsync(() -> {
            // 远程调用更新热度
            skuFeignClient.incrHotScore(skuId);
        }, threadPoolExecutor);
        // 任务组合
        CompletableFuture.allOf(skuInfoCompletableFuture,
                activityCompletableFuture,
                hotCompletableFuture).join();
        return result;
    }

}
