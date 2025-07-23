package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.SkuImage;
import com.atguigu.ssyx.model.product.SkuPoster;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.product.service.SkuPosterService;
import com.atguigu.ssyx.product.mapper.SkuPosterMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
* @author 信信宝贝
* @description 针对表【sku_poster(商品海报表)】的数据库操作Service实现
* @createDate 2025-07-23 00:16:07
*/
@Service
public class SkuPosterServiceImpl extends ServiceImpl<SkuPosterMapper, SkuPoster>
    implements SkuPosterService{

    @Override
    public List<SkuPoster> getPosterListBySkuId(Long id) {
        LambdaQueryWrapper<SkuPoster> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuPoster::getSkuId, id);
        return baseMapper.selectList(wrapper);
    }
}




