package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.AttrGroup;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.product.service.SkuInfoService;
import com.atguigu.ssyx.vo.product.AttrGroupQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: guigu-ssyx-parent
 * @BelongsPackage: com.atguigu.ssyx.product.controller
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-23  00:17
 * @Description: TODO
 * @Version: 1.0
 */
@Api(value = "SkuInfo管理", tags = "商品Sku管理")
@RestController
@RequestMapping(value="/admin/product/skuInfo")
@CrossOrigin
public class SkuInfoController {

    @Autowired
    private SkuInfoService skuInfoService;

    @ApiOperation("获取sku页列表")
    @GetMapping("/{page}/{limit}")
    public Result<IPage<SkuInfo>> list(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable("page") Long page,
                                       @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable("limit") Long limit,
                                       @ApiParam(name = "skuInfoQueryVo", value = "查询条件对象", required = false) SkuInfoQueryVo skuInfoQueryVo) {
        Page<SkuInfo> pageParam = new Page<>(page, limit);
        IPage<SkuInfo> result = skuInfoService.selectPageSkuInfo(pageParam, skuInfoQueryVo);
        return Result.ok(result);
    }

    @ApiOperation(value = "商品添加方法")
    @PostMapping("/save")
    public Result save(@ApiParam(name = "skuInfoVo", value = "商品对象", required = true) @RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.saveSkuInfo(skuInfoVo);
        return Result.ok();
    }

}
