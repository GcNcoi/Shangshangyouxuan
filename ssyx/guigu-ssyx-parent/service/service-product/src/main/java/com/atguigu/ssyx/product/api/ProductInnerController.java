package com.atguigu.ssyx.product.api;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.product.service.SkuInfoService;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.atguigu.ssyx.vo.product.SkuStockLockVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: guigu-ssyx-parent
 * @BelongsPackage: com.atguigu.ssyx.product.controller
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-24  23:46
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api/product")
public class ProductInnerController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuInfoService skuInfoService;

    @ApiOperation(value = "根据分类id获取分类信息")
    @GetMapping("/inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") Long categoryId) {
        return categoryService.getById(categoryId);
    }

    @ApiOperation(value = "根据skuId获取sku信息")
    @GetMapping("/inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable("skuId") Long skuId) {
        return skuInfoService.getById(skuId);
    }

    @ApiOperation(value = "根据skuId列表得到sku列表")
    @PostMapping("/inner/findSkuInfoList")
    public List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdList) {
        return skuInfoService.findSkuInfoList(skuIdList);
    }

    @ApiOperation(value = "根据分类id列表得到分类列表")
    @PostMapping("/inner/findCategoryList")
    public List<Category> findCategoryList(@RequestBody List<Long> categoryIdList) {
        return categoryService.listByIds(categoryIdList);
    }

    @ApiOperation(value = "根据关键字匹配sku列表")
    @GetMapping("/inner/findSkuInfoByKeyWord/{keyword}")
    public List<SkuInfo> findSkuInfoByKeyWord(@PathVariable("keyword") String keyword) {
        return skuInfoService.findSkuInfoByKeyWord(keyword);
    }

    @ApiOperation(value = "获取所有分类")
    @GetMapping("/inner/findAllCategoryList")
    public List<Category> findAllCategoryList() {
        return categoryService.findAllList();
    }

    @ApiOperation(value = "获取新人专享")
    @GetMapping("/inner/findNewPersonSkuInfoList")
    public List<SkuInfo> findNewPersonSkuInfoList() {
        return skuInfoService.findNewPersonList();
    }

    @ApiOperation(value = "根据skuId获取sku信息")
    @GetMapping("/inner/getSkuInfoVo/{skuId}")
    public SkuInfoVo getSkuInfoVo(@PathVariable("skuId") Long skuId) {
        return skuInfoService.getSkuInfoVo(skuId);
    }

    @ApiOperation(value = "锁定库存")
    @PostMapping("/inner/checkAndLock/{orderNo}")
    public Boolean checkAndLock(@RequestBody List<SkuStockLockVo> skuStockLockVoList, @PathVariable("orderNo") String orderNo) {
        return skuInfoService.checkAndLock(skuStockLockVoList, orderNo);
    }

}
