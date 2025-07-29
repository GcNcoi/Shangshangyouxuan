package com.atguigu.ssyx.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.ssyx.common.auth.AuthContextHolder;
import com.atguigu.ssyx.common.constant.RedisConst;
import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.common.utils.JwtHelper;
import com.atguigu.ssyx.enums.UserType;
import com.atguigu.ssyx.model.user.User;
import com.atguigu.ssyx.user.service.UserService;
import com.atguigu.ssyx.user.utils.ConstantPropertiesUtil;
import com.atguigu.ssyx.user.utils.HttpClientUtils;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import com.atguigu.ssyx.vo.user.UserLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述
 *
 * @author: Gxf
 * @date: 2025年07月29日 16:55
 */
@Api(tags = "微信登录接口")
@RestController
@RequestMapping("/api/user/weixin")
public class WeixinApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "微信登录获取openid(小程序)")
    @GetMapping("/wxLogin/{code}")
    public Result loginWx(@ApiParam(name = "code", value = "code", required = true) @PathVariable String code) {
        // 1. 获取授权临时票据
        String wxOpenAppId = ConstantPropertiesUtil.WX_OPEN_APP_ID;
        String wxOpenAppSecret = ConstantPropertiesUtil.WX_OPEN_APP_SECRET;
        // 拼接请求地址 + 参数
        StringBuffer url = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/jscode2session")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&js_code=%s")
                .append("&grant_type=authorization_code");
        String tokenUrl = String.format(url.toString(), wxOpenAppId, wxOpenAppSecret, code);
        // 2. HttpClient发送get请求
        String result = null;
        try {
            result = HttpClientUtils.get(tokenUrl);
        } catch (Exception e) {
            throw new SsyxException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }
        // 3. 请求微信接口服务，返回两个值 session_key和openid
        JSONObject jsonObject = JSONObject.parseObject(result);
        String sessionKey = jsonObject.getString("session_key");
        String openId = jsonObject.getString("openid");
        // 4. 添加微信用户信息到数据库里面
        // 4.1 通过openId判断是否是第一次使用微信授权登录
        // 先根据openid进行数据库查询
        User user = userService.getByOpenid(openId);
        // 如果没有查到用户信息,那么调用微信个人信息获取的接口
        if(null == user){
            user = new User();
            user.setOpenId(openId);
            user.setNickName(openId);
            user.setPhotoUrl("");
            user.setUserType(UserType.USER);
            user.setIsNew(0);
            userService.save(user);
        }
        // 5. 根据userId查询提货点和团长信息
        LeaderAddressVo leaderAddressVo = userService.getLeaderAddressVoByUserId(user.getId());
        // 6. 使用JWT工具根据userId和userName生成token字符串
        String token = JwtHelper.createToken(user.getId(), user.getNickName());
        // 7. 获取当前登录用户信息，放到Redis里面，设置有效时间
        UserLoginVo userLoginVo = this.userService.getUserLoginVo(user.getId());
        redisTemplate.opsForValue().set(RedisConst.USER_LOGIN_KEY_PREFIX + user.getId(), userLoginVo, RedisConst.USERKEY_TIMEOUT, TimeUnit.DAYS);
        // 8. 封装数据到map返回
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("token", token);
        map.put("leaderAddressVo", leaderAddressVo);
        return Result.ok(map);
    }

    @PostMapping("/auth/updateUser")
    @ApiOperation(value = "更新用户昵称与头像")
    public Result updateUser(@RequestBody User user) {
        //获取当前登录用户id
        User user1 = userService.getById(AuthContextHolder.getUserId());
        //把昵称更新为微信用户
        user1.setNickName(user.getNickName().replaceAll("[ue000-uefff]", "*"));
        user1.setPhotoUrl(user.getPhotoUrl());
        userService.updateById(user1);
        return Result.ok(null);
    }

}
