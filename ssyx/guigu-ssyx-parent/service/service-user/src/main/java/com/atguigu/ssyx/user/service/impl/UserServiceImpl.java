package com.atguigu.ssyx.user.service.impl;

import com.atguigu.ssyx.model.user.User;
import com.atguigu.ssyx.user.Leader;
import com.atguigu.ssyx.user.UserDelivery;
import com.atguigu.ssyx.user.mapper.LeaderMapper;
import com.atguigu.ssyx.user.mapper.UserDeliveryMapper;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import com.atguigu.ssyx.vo.user.UserLoginVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.user.service.UserService;
import com.atguigu.ssyx.user.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user(会员表)】的数据库操作Service实现
* @createDate 2025-07-29 17:18:14
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserDeliveryMapper userDeliveryMapper;

    @Autowired
    private LeaderMapper leaderMapper;

    @Override
    public User getByOpenid(String openId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getOpenId, openId));
    }

    @Override
    public LeaderAddressVo getLeaderAddressVoByUserId(Long userId) {
        // 根据用户id查询用户默认的团长
        LambdaQueryWrapper<UserDelivery> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDelivery::getUserId, userId);
        queryWrapper.eq(UserDelivery::getIsDefault, 1);
        UserDelivery userDelivery = userDeliveryMapper.selectOne(queryWrapper);
        if(null == userDelivery) return null;
        // 根据团长id查询leader表查询团长其他信息
        Leader leader = leaderMapper.selectById(userDelivery.getLeaderId());
        // 封装返回数据
        LeaderAddressVo leaderAddressVo = new LeaderAddressVo();
        BeanUtils.copyProperties(leader, leaderAddressVo);
        leaderAddressVo.setUserId(userId);
        leaderAddressVo.setLeaderId(leader.getId());
        leaderAddressVo.setLeaderName(leader.getName());
        leaderAddressVo.setLeaderPhone(leader.getPhone());
        leaderAddressVo.setWareId(userDelivery.getWareId());
        leaderAddressVo.setStorePath(leader.getStorePath());
        return leaderAddressVo;
    }

    @Override
    public UserLoginVo getUserLoginVo(Long userId) {
        User user = baseMapper.selectById(userId);
        UserLoginVo userLoginVo = new UserLoginVo();
        BeanUtils.copyProperties(user, userLoginVo);
        userLoginVo.setUserId(userId);
        LambdaQueryWrapper<UserDelivery> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDelivery::getUserId, userId);
        queryWrapper.eq(UserDelivery::getIsDefault, 1);
        UserDelivery userDelivery = userDeliveryMapper.selectOne(queryWrapper);
        if(null != userDelivery) {
            userLoginVo.setLeaderId(userDelivery.getLeaderId());
            userLoginVo.setWareId(userDelivery.getWareId());
        } else {
            userLoginVo.setLeaderId(1L);
            userLoginVo.setWareId(1L);
        }
        return userLoginVo;
    }
}




