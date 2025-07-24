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

import java.util.List;

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

    @ApiOperation(value = "获取商品详情信息")
    @GetMapping("/get/{id}")
    public Result<SkuInfoVo> get(@ApiParam(name = "id", value = "商品id", required = true) @PathVariable("id") Long id) {
        SkuInfoVo skuInfoVo = skuInfoService.getSkuInfoVo(id);
        return Result.ok(skuInfoVo);
    }

    @ApiOperation(value = "修改商品")
    @PutMapping("/update")
    public Result updateById(@ApiParam(name = "skuInfoVo", value = "商品对象", required = true) @RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.updateSkuInfo(skuInfoVo);
        return Result.ok();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/remove/{id}")
    public Result remove(@ApiParam(name = "id", value = "商品id", required = true) @PathVariable Long id) {
        skuInfoService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@ApiParam(name = "idList", value = "商品id列表", required = false) @RequestBody List<Long> idList) {
        skuInfoService.removeByIds(idList);
        return Result.ok();
    }

    @ApiOperation(value = "商品审核")
    @GetMapping("/check/{skuId}/{status}")
    public Result check(@ApiParam(name = "skuId", value = "商品id", required = true) @PathVariable("skuId") Long skuId,
                        @ApiParam(name = "status", value = "状态", required = true) @PathVariable("status") Integer status) {
        skuInfoService.check(skuId, status);
        return Result.ok();
    }

    @ApiOperation(value = "商品上架")
    @GetMapping("/publish/{skuId}/{status}")
    public Result publish(@ApiParam(name = "skuId", value = "商品id", required = true) @PathVariable("skuId") Long skuId,
                          @ApiParam(name = "status", value = "状态", required = true) @PathVariable("status") Integer status) {
        skuInfoService.publish(skuId, status);
        return Result.ok();
    }

    @ApiOperation(value = "新人专享")
    @GetMapping("/isNewPerson/{skuId}/{status}")
    public Result isNewPerson(@ApiParam(name = "skuId", value = "商品id", required = true) @PathVariable("skuId") Long skuId,
                              @ApiParam(name = "status", value = "状态", required = true) @PathVariable("status") Integer status) {
        skuInfoService.isNewPerson(skuId, status);
        return Result.ok();
    }
}
