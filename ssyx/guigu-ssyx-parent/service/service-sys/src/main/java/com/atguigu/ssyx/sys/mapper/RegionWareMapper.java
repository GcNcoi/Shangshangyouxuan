package com.atguigu.ssyx.sys.mapper;

import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 信信宝贝
* @description 针对表【region_ware(城市仓库关联表)】的数据库操作Mapper
* @createDate 2025-07-22 00:18:02
* @Entity com.atguigu.ssyx.RegionWare
*/
@Mapper
public interface RegionWareMapper extends BaseMapper<RegionWare> {

}




