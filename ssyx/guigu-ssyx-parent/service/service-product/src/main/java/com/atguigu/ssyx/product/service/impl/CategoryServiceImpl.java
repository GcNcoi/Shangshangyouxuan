package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.vo.product.CategoryQueryVo;
import com.atguigu.ssyx.vo.product.CategoryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.product.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author 信信宝贝
* @description 针对表【category(商品三级分类)】的数据库操作Service实现
* @createDate 2025-07-23 00:15:15
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Override
    public IPage<Category> selectPageCategory(Page<Category> pageParam, CategoryQueryVo categoryQueryVo) {
        // 1.获取查询条件值
        String name = categoryQueryVo.getName();
        // 2.判断条件是否为空,不为空则封装条件
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(Category::getName, name);
        }
        wrapper.orderByAsc(Category::getSort);
        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public List<Category> findAllList() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        return baseMapper.selectList(wrapper);
    }
}




