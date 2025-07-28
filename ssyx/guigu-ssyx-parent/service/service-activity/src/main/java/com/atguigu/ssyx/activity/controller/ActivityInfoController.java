package com.atguigu.ssyx.activity.controller;

import com.atguigu.ssyx.activity.service.ActivityInfoService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.atguigu.ssyx.model.product.AttrGroup;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.ActivityRuleVo;
import com.atguigu.ssyx.vo.product.AttrGroupQueryVo;
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
@Api(tags = "营销活动接口")
@RestController
@RequestMapping("/admin/activity/activityInfo")
public class ActivityInfoController {

    @Autowired
    private ActivityInfoService activityInfoService;

    @ApiOperation("获取营销活动分页列表")
    @GetMapping("/{page}/{limit}")
    public Result<IPage<ActivityInfo>> list(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable("page") Long page,
                                            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable("limit") Long limit) {
        Page<ActivityInfo> pageParam = new Page<>(page, limit);
        IPage<ActivityInfo> result = activityInfoService.selectPageActivityInfo(pageParam);
        return Result.ok(result);
    }

    @ApiOperation("根据id查询活动")
    @GetMapping("/get/{id}")
    public Result<ActivityInfo> get(@ApiParam(name = "id", value = "活动id", required = true) @PathVariable("id") Long id) {
        ActivityInfo activityInfo = activityInfoService.getById(id);
        activityInfo.setActivityTypeString(activityInfo.getActivityType().getComment());
        return Result.ok(activityInfo);
    }

    @ApiOperation(value = "新增营销活动")
    @PostMapping("/save")
    public Result save(@ApiParam(name = "activityInfo", value = "营销活动对象", required = true) @RequestBody ActivityInfo activityInfo) {
        activityInfoService.save(activityInfo);
        return Result.ok();
    }

    @ApiOperation(value = "修改营销活动")
    @PutMapping("/update")
    public Result updateById(@ApiParam(name = "activityInfo", value = "营销活动对象", required = true) @RequestBody ActivityInfo activityInfo) {
        activityInfoService.updateById(activityInfo);
        return Result.ok();
    }

    @ApiOperation(value = "删除营销活动")
    @DeleteMapping("/remove/{id}")
    public Result remove(@ApiParam(name = "id", value = "营销活动id", required = true) @PathVariable("id") Long id) {
        activityInfoService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表删除营销活动")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@ApiParam(name = "idList", value = "营销活动id列表", required = true) @RequestBody List<Long> idList) {
        activityInfoService.removeByIds(idList);
        return Result.ok();
    }

    @ApiOperation(value = "根据活动id获取活动规则数据")
    @GetMapping("/findActivityRuleList/{id}")
    public Result<Map<String, Object>> findActivityRuleList(@ApiParam(name = "id", value = "活动id", required = true) @PathVariable("id") Long id) {
        Map<String, Object> activityRuleMap = activityInfoService.findActivityRuleList(id);
        return Result.ok(activityRuleMap);
    }

    @ApiOperation(value = "在活动里面添加规则数据")
    @PostMapping("/saveActivityRule")
    public Result saveActivityRule(@ApiParam(name = "activityRuleVo", value = "规则数据Vo", required = true) @RequestBody ActivityRuleVo activityRuleVo) {
        activityInfoService.saveActivityRule(activityRuleVo);
        return Result.ok();
    }

    @ApiOperation(value = "根据关键字查询匹配sku信息")
    @GetMapping("/findSkuInfoByKeyword/{keyword}")
    public Result<List<SkuInfo>> findSkuInfoByKeyword(@ApiParam(name = "keyword", value = "查询关键字", required = false) @PathVariable("keyword") String keyword) {
        List<SkuInfo> result =  activityInfoService.findSkuInfoByKeyword(keyword);
        return Result.ok(result);
    }

}
