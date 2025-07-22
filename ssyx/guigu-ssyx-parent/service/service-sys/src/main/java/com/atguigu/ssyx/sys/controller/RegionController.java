package com.atguigu.ssyx.sys.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.Region;
import com.atguigu.ssyx.model.sys.Ware;
import com.atguigu.ssyx.sys.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: guigu-ssyx-parent
 * @BelongsPackage: com.atguigu.ssyx.controller
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-22  00:16
 * @Description: TODO
 * @Version: 1.0
 */
@Api(tags = "区域接口")
@RestController
@RequestMapping("/admin/sys/region")
@CrossOrigin
public class RegionController {

    @Autowired
    private RegionService regionService;

    @ApiOperation("根据区域关键字查询区域列表信息")
    @GetMapping("/findRegionByKeyword/{keyword}")
    public Result<List<Region>> findRegionByKeyword(@ApiParam(name = "keyword", value = "关键字", required = false) @PathVariable("keyword") String keyword) {
        List<Region> result = regionService.findRegionByKeyword(keyword);
        return Result.ok(result);
    }

}
