package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.SkuPoster;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 信信宝贝
* @description 针对表【sku_poster(商品海报表)】的数据库操作Service
* @createDate 2025-07-23 00:16:07
*/
public interface SkuPosterService extends IService<SkuPoster> {

    List<SkuPoster> findBySkuId(Long id);
}
