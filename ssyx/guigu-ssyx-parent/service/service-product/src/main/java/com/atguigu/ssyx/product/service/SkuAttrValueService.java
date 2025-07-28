package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.SkuAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 信信宝贝
* @description 针对表【sku_attr_value(spu属性值)】的数据库操作Service
* @createDate 2025-07-23 00:15:39
*/
public interface SkuAttrValueService extends IService<SkuAttrValue> {

    List<SkuAttrValue> findBySkuId(Long id);
}
