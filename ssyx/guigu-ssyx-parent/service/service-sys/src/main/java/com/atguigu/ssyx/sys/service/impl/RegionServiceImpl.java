package com.atguigu.ssyx.sys.service.impl;

import com.atguigu.ssyx.model.sys.Region;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.sys.service.RegionService;
import com.atguigu.ssyx.sys.mapper.RegionMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
* @author 信信宝贝
* @description 针对表【region(地区表)】的数据库操作Service实现
* @createDate 2025-07-22 00:14:56
*/
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region>
    implements RegionService{

    @Override
    public List<Region> findRegionByKeyword(String keyword) {
        LambdaQueryWrapper<Region> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Region::getName, keyword);
        return baseMapper.selectList(wrapper);
    }
}




