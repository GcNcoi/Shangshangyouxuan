package com.atguigu.ssyx.sys.service;

import com.atguigu.ssyx.model.sys.Region;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 信信宝贝
* @description 针对表【region(地区表)】的数据库操作Service
* @createDate 2025-07-22 00:14:56
*/
public interface RegionService extends IService<Region> {

    List<Region> findRegionByKeyword(String keyword);
}
