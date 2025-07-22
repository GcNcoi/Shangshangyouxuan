package com.atguigu.ssyx.sys.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.sys.service.RegionWareService;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: guigu-ssyx-parent
 * @BelongsPackage: com.atguigu.ssyx.controller
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-22  00:17
 * @Description: TODO
 * @Version: 1.0
 */
@Api(tags = "开通区域接口")
@RestController
@RequestMapping("/admin/sys/regionWare")
@CrossOrigin
public class RegionWareController {

    @Autowired
    private RegionWareService regionWareService;

    @ApiOperation("开通区域列表")
    @GetMapping("/{page}/{limit}")
    public Result<IPage<RegionWare>> list(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                                          @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
                                          @ApiParam(name = "regionWareVo", value = "查询对象", required = false) RegionWareQueryVo regionWareQueryVo) {
        Page<RegionWare> pageParam = new Page<>(page, limit);
        IPage<RegionWare> result = regionWareService.selectPageRegionWare(pageParam, regionWareQueryVo);
        return Result.ok(result);
    }

}
