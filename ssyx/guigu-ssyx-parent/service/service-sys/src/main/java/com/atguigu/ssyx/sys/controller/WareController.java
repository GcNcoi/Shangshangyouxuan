package com.atguigu.ssyx.sys.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.Ware;
import com.atguigu.ssyx.sys.service.WareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @BelongsProject: guigu-ssyx-parent
 * @BelongsPackage: com.atguigu.ssyx.controller
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-22  00:17
 * @Description: TODO
 * @Version: 1.0
 */
@Api(tags = "仓库接口")
@RestController
@RequestMapping("/admin/sys/ware")
public class WareController {

    @Autowired
    private WareService wareService;

    @ApiOperation("查询所有仓库列表")
    @GetMapping("/findAllList")
    public Result<List<Ware>> findAllList() {
        List<Ware> list = wareService.list();
        return Result.ok(list);
    }

}
