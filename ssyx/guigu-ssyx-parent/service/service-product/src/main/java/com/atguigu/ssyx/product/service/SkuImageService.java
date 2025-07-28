package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.SkuImage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 信信宝贝
* @description 针对表【sku_image(商品图片)】的数据库操作Service
* @createDate 2025-07-23 00:15:50
*/
public interface SkuImageService extends IService<SkuImage> {

    List<SkuImage> findBySkuId(Long id);
}
