package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.Category;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.product.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author 信信宝贝
* @description 针对表【category(商品三级分类)】的数据库操作Service实现
* @createDate 2025-07-23 00:15:15
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




