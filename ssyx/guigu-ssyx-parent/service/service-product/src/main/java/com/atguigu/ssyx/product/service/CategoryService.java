package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.vo.product.CategoryQueryVo;
import com.atguigu.ssyx.vo.product.CategoryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 信信宝贝
* @description 针对表【category(商品三级分类)】的数据库操作Service
* @createDate 2025-07-23 00:15:15
*/
public interface CategoryService extends IService<Category> {

    /**
     * 商品分类列表
     * @param pageParam 分页参数
     * @param categoryQueryVo 查询参数
     * @return
     */
    IPage<Category> selectPageCategory(Page<Category> pageParam, CategoryQueryVo categoryQueryVo);

    /**
     * 获取全部商品分类
     * @return
     */
    List<Category> findAllList();

}
