package com.atguigu.ssyx.activity.controller;

import com.atguigu.ssyx.activity.service.CouponInfoService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 功能描述
 *
 * @author: Gxf
 * @date: 2025年07月25日 17:07
 */
@Api(tags = "优惠券接口")
@RestController
@RequestMapping("/admin/activity/couponInfo")
public class CouponInfoController {

    @Autowired
    private CouponInfoService couponInfoService;

    @ApiOperation("获取优惠券分页列表")
    @GetMapping("/{page}/{limit}")
    public Result<IPage<CouponInfo>> list(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable("page") Long page,
                                          @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable("limit") Long limit) {
        IPage<CouponInfo> result = couponInfoService.selectPageCouponInfo(page, limit);
        return Result.ok(result);
    }

    @ApiOperation("根据id查询优惠券")
    @GetMapping("/get/{id}")
    public Result<CouponInfo> get(@ApiParam(name = "id", value = "优惠券id", required = true) @PathVariable("id") Long id) {
        CouponInfo couponInfo = couponInfoService.getCouponInfo(id);
        return Result.ok(couponInfo);
    }

    @ApiOperation(value = "新增优惠券")
    @PostMapping("/save")
    public Result save(@ApiParam(name = "couponInfo", value = "优惠券对象", required = true) @RequestBody CouponInfo couponInfo) {
        couponInfoService.save(couponInfo);
        return Result.ok();
    }

    @ApiOperation(value = "修改优惠券")
    @PutMapping("/update")
    public Result updateById(@ApiParam(name = "couponInfo", value = "优惠券对象", required = true) @RequestBody CouponInfo couponInfo) {
        couponInfoService.updateById(couponInfo);
        return Result.ok();
    }

    @ApiOperation(value = "删除优惠券")
    @DeleteMapping("/remove/{id}")
    public Result remove(@ApiParam(name = "id", value = "优惠券id", required = true) @PathVariable("id") Long id) {
        couponInfoService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表删除优惠券")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@ApiParam(name = "idList", value = "优惠券id列表", required = true) @RequestBody List<Long> idList) {
        couponInfoService.removeByIds(idList);
        return Result.ok();
    }

    @ApiOperation("根据优惠券id查询规则列表")
    @GetMapping("/findCouponRuleList/{id}")
    public Result<Map<String, Object>> findCouponRuleList(@ApiParam(name = "id", value = "优惠券id", required = true) @PathVariable("id") Long id) {
        Map<String, Object> map = couponInfoService.findCouponRuleList(id);
        return Result.ok(map);
    }

    @ApiOperation(value = "新增优惠券规则")
    @PostMapping("/saveCouponRule")
    public Result saveCouponRule(@ApiParam(name = "couponRuleVo", value = "优惠券规则对象", required = true) @RequestBody CouponRuleVo couponRuleVo) {
        couponInfoService.saveCouponRule(couponRuleVo);
        return Result.ok();
    }

    @ApiOperation(value = "根据关键字查询匹配优惠券信息")
    @GetMapping("/findCouponByKeyword/{keyword}")
    public Result<List<CouponInfo>> findCouponByKeyword(@ApiParam(name = "keyword", value = "查询关键字", required = false) @PathVariable("keyword") String keyword) {
        List<CouponInfo> result = couponInfoService.findCouponByKeyword(keyword);
        return Result.ok(result);
    }

}
