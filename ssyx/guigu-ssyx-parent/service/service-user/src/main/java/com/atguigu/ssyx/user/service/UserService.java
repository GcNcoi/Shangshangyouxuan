package com.atguigu.ssyx.user.service;

import com.atguigu.ssyx.model.user.User;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import com.atguigu.ssyx.vo.user.UserLoginVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【user(会员表)】的数据库操作Service
* @createDate 2025-07-29 17:18:14
*/
public interface UserService extends IService<User> {

    /**
     * 根据微信openid获取用户信息
     * @param openId
     * @return
     */
    User getByOpenid(String openId);

    /**
     * 根据用户id获取用户地址信息
     * @param userId
     * @return
     */
    LeaderAddressVo getLeaderAddressVoByUserId(Long userId);

    /**
     * 获取当前登录用户信息
     * @param userId
     * @return
     */
    UserLoginVo getUserLoginVo(Long userId);
}
