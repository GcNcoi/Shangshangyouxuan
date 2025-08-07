package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.Attr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 信信宝贝
* @description 针对表【attr(商品属性)】的数据库操作Service
* @createDate 2025-07-23 00:15:00
*/
public interface AttrService extends IService<Attr> {
    /**
     * 根据平台属性分组id查询
     * @param groupId 属性分组Id
     * @return
     */
    List<Attr> findByGroupId(Long groupId);
}
