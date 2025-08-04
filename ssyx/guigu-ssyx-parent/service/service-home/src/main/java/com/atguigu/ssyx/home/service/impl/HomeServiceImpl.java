package com.atguigu.ssyx.home.service.impl;

import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.client.search.SkuFeignClient;
import com.atguigu.ssyx.client.user.UserFeignClient;
import com.atguigu.ssyx.home.service.HomeService;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.model.search.SkuEs;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @BelongsProject: guigu-ssyx-parent
 * @BelongsPackage: com.atguigu.ssyx.home.service.impl
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-30  00:02
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private SkuFeignClient skuFeignClient;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public Map<String, Object> homeData(Long userId) {
        Map<String, Object> result = new HashMap<>();
        // 1. 根据userId获取当前登录用户提货地址信息
        // 远程调用service-user模块接口获取所需数据
        CompletableFuture<Map<String, Object>> leaderCompletableFuture = CompletableFuture.supplyAsync(() -> {
            LeaderAddressVo leaderAddressVo = userFeignClient.getUserAddressByUserId(userId);
            result.put("leaderAddressVo", leaderAddressVo);
            return result;
        }, threadPoolExecutor);
        // 2. 获取所有分类
        // 远程调用service-product模块接口
        CompletableFuture<Void> categoryCompletableFuture = CompletableFuture.runAsync(() -> {
            List<Category> categoryList = productFeignClient.findAllCategoryList();
            result.put("categoryList", categoryList);
        }, threadPoolExecutor);
        // 3. 获取新人专享商品
        // 远程调用service-product模块接口
        CompletableFuture<Void> newPersonSkuCompletableFuture = CompletableFuture.runAsync(() -> {
            List<SkuInfo> newPersonSkuInfoList = productFeignClient.findNewPersonSkuInfoList();
            result.put("newPersonSkuInfoList", newPersonSkuInfoList);
        }, threadPoolExecutor);

        // 4. 获取爆款商品
        // 远程调用service-search模块接口，根据Es的hotScore热门评分降序排序
        CompletableFuture<Void> hotSkuCompletableFuture = CompletableFuture.runAsync(() -> {
            List<SkuEs> hotSkuList = skuFeignClient.findHotSkuList();
            result.put("hotSkuList", hotSkuList);
        }, threadPoolExecutor);

        // 5. 封装获取数据到map集合
        CompletableFuture.allOf(leaderCompletableFuture,
                categoryCompletableFuture,
                newPersonSkuCompletableFuture,
                hotSkuCompletableFuture).join();
        return result;
    }

}
