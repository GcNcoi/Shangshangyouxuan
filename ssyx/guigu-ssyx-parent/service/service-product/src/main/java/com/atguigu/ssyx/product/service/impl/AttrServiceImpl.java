package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.Attr;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.product.service.AttrService;
import com.atguigu.ssyx.product.mapper.AttrMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 信信宝贝
* @description 针对表【attr(商品属性)】的数据库操作Service实现
* @createDate 2025-07-23 00:15:00
*/
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr>
    implements AttrService{

    @Override
    public List<Attr> findByGroupId(Long groupId) {
        LambdaQueryWrapper<Attr> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Attr::getAttrGroupId, groupId);
        return baseMapper.selectList(wrapper);
    }
}




