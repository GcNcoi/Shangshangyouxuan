package com.atguigu.ssyx.home.service;

import java.util.Map;

public interface ItemService {

    /**
     * 获取sku详细信息
     * @param id 商品Id userId 用户Id
     * @return
     */
    Map<String, Object> item(Long id, Long userId);
}
