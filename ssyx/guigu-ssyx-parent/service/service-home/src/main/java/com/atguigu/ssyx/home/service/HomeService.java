package com.atguigu.ssyx.home.service;

import java.util.Map;

/**
 * @BelongsProject: guigu-ssyx-parent
 * @BelongsPackage: com.atguigu.ssyx.home.service
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-30  00:01
 * @Description: TODO
 * @Version: 1.0
 */
public interface HomeService {

    Map<String, Object> homeData(Long userId);
}
