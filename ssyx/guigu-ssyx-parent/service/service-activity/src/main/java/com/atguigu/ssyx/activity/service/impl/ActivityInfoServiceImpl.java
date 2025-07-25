package com.atguigu.ssyx.activity.service.impl;

import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.activity.service.ActivityInfoService;
import com.atguigu.ssyx.activity.mapper.ActivityInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【activity_info(活动表)】的数据库操作Service实现
* @createDate 2025-07-25 17:14:45
*/
@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo>
    implements ActivityInfoService{

    @Override
    public IPage<ActivityInfo> selectPageActivityInfo(Page<ActivityInfo> pageParam) {
        IPage<ActivityInfo> activityInfoIPage = baseMapper.selectPage(pageParam, null);
        // 分页查询对象里面获取列表数据
        List<ActivityInfo> activityInfoList = activityInfoIPage.getRecords();
        // 遍历activityInfoList集合，得到每个ActivityInfo对象
        // 调用ActivityType的name()方法，获取枚举类型的名称
        activityInfoList.stream().forEach(item -> {
            item.setActivityTypeString(item.getActivityType().getComment());
        });
        return activityInfoIPage;
    }

    @Override
    public Map<String, Object> findActivityRuleList(Long id) {
        // 1. 根据活动id查询，查询规则列表
        // 2. 根据活动id查询，查询使用规则商品skuId列表
        // 2.1 通过远程调用 service-product模块接口，根据skuId列表得到商品信息
        return null;
    }
}




