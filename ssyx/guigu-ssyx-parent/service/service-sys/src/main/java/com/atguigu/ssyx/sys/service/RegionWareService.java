package com.atguigu.ssyx.sys.service;

import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;

/**
* @author 信信宝贝
* @description 针对表【region_ware(城市仓库关联表)】的数据库操作Service
* @createDate 2025-07-22 00:18:02
*/
@Repository
public interface RegionWareService extends IService<RegionWare> {

    IPage<RegionWare> selectPageRegionWare(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo);

    void addRegionWare(RegionWare regionWare);

    void updateStatus(Long id, Integer status);
}
