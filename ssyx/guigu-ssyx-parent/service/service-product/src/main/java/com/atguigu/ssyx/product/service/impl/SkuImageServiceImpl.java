package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.SkuImage;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.product.service.SkuImageService;
import com.atguigu.ssyx.product.mapper.SkuImageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 信信宝贝
* @description 针对表【sku_image(商品图片)】的数据库操作Service实现
* @createDate 2025-07-23 00:15:50
*/
@Service
public class SkuImageServiceImpl extends ServiceImpl<SkuImageMapper, SkuImage>
    implements SkuImageService{

    @Override
    public List<SkuImage> findBySkuId(Long id) {
        return baseMapper.selectList(new LambdaQueryWrapper<SkuImage>().eq(SkuImage::getSkuId, id));
    }
}




