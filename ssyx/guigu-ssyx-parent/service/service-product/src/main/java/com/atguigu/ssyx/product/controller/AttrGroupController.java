package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.AttrGroup;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.product.service.AttrGroupService;
import com.atguigu.ssyx.vo.product.AttrGroupQueryVo;
import com.atguigu.ssyx.vo.product.CategoryQueryVo;
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
 * @CreateTime: 2025-07-23  00:16
 * @Description: TODO
 * @Version: 1.0
 */
@Api(value = "AttrGroup管理", tags = "平台属性分组管理")
@RestController
@RequestMapping("/admin/product/attrGroup")
@CrossOrigin
public class AttrGroupController {

    @Autowired
    private AttrGroupService attrGroupService;

    @ApiOperation("获取平台属性分页列表")
    @GetMapping("/{page}/{limit}")
    public Result<IPage<AttrGroup>> list(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable("page") Long page,
                                         @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable("limit") Long limit,
                                         @ApiParam(name = "attrGroupQueryVo", value = "查询条件对象", required = false) AttrGroupQueryVo attrGroupQueryVo) {
        Page<AttrGroup> pageParam = new Page<>(page, limit);
        IPage<AttrGroup> result = attrGroupService.selectPageAttrGroup(pageParam, attrGroupQueryVo);
        return Result.ok(result);
    }

    @ApiOperation(value = "获取平台属性分组信息")
    @GetMapping("/get/{id}")
    public Result get(@ApiParam(name = "id", value = "平台属性分组id", required = true) @PathVariable("id") Long id) {
        AttrGroup attrGroup = attrGroupService.getById(id);
        return Result.ok(attrGroup);
    }

    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public Result save(@ApiParam(name = "attrGroup", value = "平台属性分组对象", required = true) @RequestBody AttrGroup attrGroup) {
        attrGroupService.save(attrGroup);
        return Result.ok();
    }

    @ApiOperation(value = "修改")
    @PutMapping("/update")
    public Result updateById(@ApiParam(name = "attrGroup", value = "平台属性分组对象", required = true) @RequestBody AttrGroup attrGroup) {
        attrGroupService.updateById(attrGroup);
        return Result.ok();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/remove/{id}")
    public Result remove(@ApiParam(name = "id", value = "平台属性分组id", required = true) @PathVariable("id") Long id) {
        attrGroupService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@ApiParam(name = "idList", value = "平台属性分组id列表", required = true) @RequestBody List<Long> idList) {
        attrGroupService.removeByIds(idList);
        return Result.ok();
    }

    @ApiOperation(value = "获取全部属性分组分类")
    @GetMapping("/findAllList")
    public Result<List<AttrGroup>> findAllList() {
        return Result.ok(attrGroupService.findAllList());
    }

}
