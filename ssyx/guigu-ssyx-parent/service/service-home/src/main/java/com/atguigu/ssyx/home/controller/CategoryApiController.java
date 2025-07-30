package com.atguigu.ssyx.home.controller;

import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Category;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能描述
 *
 * @author: Gxf
 * @date: 2025年07月30日 17:45
 */
@Api(tags = "商品分类")
@RestController
@RequestMapping("/api/home")
public class CategoryApiController {

    @Autowired
    private ProductFeignClient productFeignClient;

    @ApiOperation("查询所有分类")
    @GetMapping("/category")
    public Result<List<Category>> getCategoryList() {
        return Result.ok(productFeignClient.findAllCategoryList());
    }



}
