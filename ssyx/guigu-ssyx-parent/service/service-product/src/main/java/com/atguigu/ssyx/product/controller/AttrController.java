package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Attr;
import com.atguigu.ssyx.product.service.AttrService;
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
 * @CreateTime: 2025-07-23  00:16
 * @Description: TODO
 * @Version: 1.0
 */
@Api(value = "Attr管理", tags = "平台属性管理")
@RestController
@RequestMapping(value="/admin/product/attr")
public class AttrController {

    @Autowired
    private AttrService attrService;

    @ApiOperation("根据平台属性分组id查询")
    @GetMapping("/{groupId}")
    public Result findByGroupId(@ApiParam(name = "groupId", value = "平台属性分组id", required = true) @PathVariable("groupId") Long groupId) {
        List<Attr> attrList = attrService.findByGroupId(groupId);
        return Result.ok(attrList);
    }

    @ApiOperation(value = "获取")
    @GetMapping("/get/{id}")
    public Result get(@ApiParam(name = "id", value = "平台属性id", required = true) @PathVariable("id") Long id) {
        Attr attr = attrService.getById(id);
        return Result.ok(attr);
    }

    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public Result save(@ApiParam(name = "attr", value = "平台属性", required = true) @RequestBody Attr attr) {
        attrService.save(attr);
        return Result.ok();
    }

    @ApiOperation(value = "修改")
    @PutMapping("/update")
    public Result updateById(@ApiParam(name = "attr", value = "平台属性", required = true) @RequestBody Attr attr) {
        attrService.updateById(attr);
        return Result.ok();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/remove/{id}")
    public Result remove(@ApiParam(name = "id", value = "平台属性id", required = true) @PathVariable("id") Long id) {
        attrService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@ApiParam(name = "idList", value = "平台属性id列表", required = true) @RequestBody List<Long> idList) {
        attrService.removeByIds(idList);
        return Result.ok();
    }

}
