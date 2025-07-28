package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.IndexService;
import com.atguigu.ssyx.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: 首页接口
 *
 * @author: Gxf
 * @date: 2025年07月18日 14:41
 */
@Api(tags = "登录接口")
@RestController
@RequestMapping("/admin/acl/index")
@CrossOrigin
public class IndexController {

    @PostMapping("/login")
    @ApiOperation("登录")
    public Result login() {
        Map<String, String> map = new HashMap<>();
        map.put("token", "token-admin");
        return Result.ok(map);
    }

    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public Result info() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "admin");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public Result logout() {
        return Result.ok(null);
    }

}
