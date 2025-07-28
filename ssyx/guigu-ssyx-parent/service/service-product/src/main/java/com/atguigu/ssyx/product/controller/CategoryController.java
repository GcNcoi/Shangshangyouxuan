package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.vo.product.CategoryQueryVo;
import com.atguigu.ssyx.vo.product.CategoryVo;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
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
@Api(tags = "商品分类管理")
@RestController
@RequestMapping("/admin/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("商品分类列表")
    @GetMapping("/{page}/{limit}")
    public Result<IPage<Category>> list(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable("page") Long page,
                                        @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable("limit") Long limit,
                                        @ApiParam(name = "categoryQueryVo", value = "查询条件对象", required = false) CategoryQueryVo categoryQueryVo) {
        Page<Category> pageParam = new Page<>(page, limit);
        IPage<Category> result = categoryService.selectPageCategory(pageParam, categoryQueryVo);
        return Result.ok(result);
    }

    @ApiOperation(value = "获取商品分类信息")
    @GetMapping("/get/{id}")
    public Result<Category> get(@ApiParam(name = "id", value = "商品分类id", required = true) @PathVariable("id") Long id) {
        Category category = categoryService.getById(id);
        return Result.ok(category);
    }

    @ApiOperation(value = "新增商品分类")
    @PostMapping("/save")
    public Result save(@ApiParam(name = "category", value = "商品分类对象", required = true) @RequestBody Category category) {
        categoryService.save(category);
        return Result.ok();
    }

    @ApiOperation(value = "修改商品分类")
    @PutMapping("/update")
    public Result updateById(@ApiParam(name = "category", value = "商品分类对象", required = true) @RequestBody Category category) {
        categoryService.updateById(category);
        return Result.ok();
    }

    @ApiOperation(value = "删除商品分类")
    @DeleteMapping("/remove/{id}")
    public Result remove(@ApiParam(name = "id", value = "商品分类id", required = true) @PathVariable("id") Long id) {
        categoryService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表删除商品分类")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@ApiParam(name = "idList", value = "商品分类id列表", required = true) @RequestBody List<Long> idList) {
        categoryService.removeByIds(idList);
        return Result.ok();
    }

    @ApiOperation(value = "获取全部商品分类")
    @GetMapping("/findAllList")
    public Result<List<Category>> findAllList() {
        return Result.ok(categoryService.findAllList());
    }

}
