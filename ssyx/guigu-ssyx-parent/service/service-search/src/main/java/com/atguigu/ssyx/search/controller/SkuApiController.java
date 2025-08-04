package com.atguigu.ssyx.search.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.search.SkuEs;
import com.atguigu.ssyx.search.service.SkuService;
import com.atguigu.ssyx.vo.search.SkuEsQueryVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能描述
 *
 * @author: Gxf
 * @date: 2025年07月24日 16:36
 */
@RestController
@RequestMapping("/api/search/sku")
public class SkuApiController {

    @Autowired
    private SkuService skuService;

    @ApiOperation(value = "上架商品")
    @GetMapping("/inner/upperSku/{skuId}")
    public Result upperGoods(@PathVariable("skuId") Long skuId) {
        skuService.upperSku(skuId);
        return Result.ok();
    }

    @ApiOperation(value = "下架商品")
    @GetMapping("/inner/lowerSku/{skuId}")
    public Result lowerGoods(@PathVariable("skuId") Long skuId) {
        skuService.lowerSku(skuId);
        return Result.ok();
    }

    @ApiOperation(value = "获取爆品商品")
    @GetMapping("/inner/findHotSkuList")
    public List<SkuEs> findHotSkuList() {
        return skuService.findHotSkuList();
    }

    @ApiOperation("查询分类商品")
    @GetMapping("/{page}/{limit}")
    public Result listSku(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Integer page,
                          @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Integer limit,
                          @ApiParam(name = "searchParamVo", value = "查询对象", required = false) SkuEsQueryVo skuEsQueryVo) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<SkuEs> skuEsPage = skuService.search(pageable, skuEsQueryVo);
        return Result.ok(skuEsPage);
    }

    @ApiOperation(value = "更新商品incrHotScore")
    @GetMapping("/inner/incrHotScore/{skuId}")
    public Boolean incrHotScore(@PathVariable("skuId") Long skuId) {
        skuService.incrHotScore(skuId);
        return true;
    }

}
