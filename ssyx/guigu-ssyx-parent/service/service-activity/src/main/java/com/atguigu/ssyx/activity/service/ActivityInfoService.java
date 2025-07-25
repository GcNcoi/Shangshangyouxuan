package com.atguigu.ssyx.activity.service;

import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author Administrator
* @description 针对表【activity_info(活动表)】的数据库操作Service
* @createDate 2025-07-25 17:14:45
*/
public interface ActivityInfoService extends IService<ActivityInfo> {

    IPage<ActivityInfo> selectPageActivityInfo(Page<ActivityInfo> pageParam);

    Map<String, Object> findActivityRuleList(Long id);
}
