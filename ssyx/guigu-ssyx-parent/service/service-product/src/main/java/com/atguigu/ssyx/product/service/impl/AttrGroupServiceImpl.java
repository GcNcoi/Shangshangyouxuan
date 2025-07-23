package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.AttrGroup;
import com.atguigu.ssyx.vo.product.AttrGroupQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.product.service.AttrGroupService;
import com.atguigu.ssyx.product.mapper.AttrGroupMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author 信信宝贝
* @description 针对表【attr_group(属性分组)】的数据库操作Service实现
* @createDate 2025-07-23 00:15:06
*/
@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup>
    implements AttrGroupService{

    @Override
    public IPage<AttrGroup> selectPageAttrGroup(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo) {
        LambdaQueryWrapper<AttrGroup> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(attrGroupQueryVo.getName())) {
            wrapper.like(AttrGroup::getName, attrGroupQueryVo.getName());
        }
        wrapper.orderByAsc(AttrGroup::getSort);
        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public List<AttrGroup> findAllList() {
        LambdaQueryWrapper<AttrGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(AttrGroup::getSort);
        return baseMapper.selectList(wrapper);
    }
}




