package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.SkuAttrValue;
import com.atguigu.ssyx.model.product.SkuPoster;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.product.service.SkuAttrValueService;
import com.atguigu.ssyx.product.mapper.SkuAttrValueMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
* @author 信信宝贝
* @description 针对表【sku_attr_value(spu属性值)】的数据库操作Service实现
* @createDate 2025-07-23 00:15:39
*/
@Service
public class SkuAttrValueServiceImpl extends ServiceImpl<SkuAttrValueMapper, SkuAttrValue>
    implements SkuAttrValueService{

    @Override
    public List<SkuAttrValue> getAttrValueListBySkuId(Long id) {
        LambdaQueryWrapper<SkuAttrValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuAttrValue::getSkuId, id);
        return baseMapper.selectList(wrapper);
    }
}




