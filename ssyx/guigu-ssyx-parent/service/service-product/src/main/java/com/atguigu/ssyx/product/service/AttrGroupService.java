package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.AttrGroup;
import com.atguigu.ssyx.vo.product.AttrGroupQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 信信宝贝
* @description 针对表【attr_group(属性分组)】的数据库操作Service
* @createDate 2025-07-23 00:15:06
*/
public interface AttrGroupService extends IService<AttrGroup> {

    IPage<AttrGroup> selectPageAttrGroup(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo);

    List<AttrGroup> findAllList();
}
