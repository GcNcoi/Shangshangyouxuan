package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.AdminService;
import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.model.acl.AdminRole;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 功能描述: 用户管理
 *
 * @author: Gxf
 * @date: 2025年07月18日 17:24
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/acl/user")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("/{page}/{limit}")
    public Result<IPage<Admin>> list(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
                        @ApiParam(name = "userQueryVo", value = "查询对象", required = false) AdminQueryVo userQueryVo) {
        Page<Admin> pageParam = new Page<>(page, limit);
        IPage<Admin> pageModel = adminService.selectPage(pageParam, userQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("根据ID查询")
    @GetMapping("/get/{id}")
    public Result<Admin> get(@ApiParam(name = "id", value = "用户ID", required = true) @PathVariable Long id) {
        Admin admin = adminService.getById(id);
        return Result.ok(admin);
    }

    @ApiOperation("添加用户")
    @PostMapping("/save")
    public Result save(@RequestBody Admin admin) {
        if (admin.getPassword() != null && admin.getPassword() != "") {
            admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
        }
        adminService.save(admin);
        return Result.ok();
    }

    @ApiOperation("更新用户")
    @PutMapping("/update")
    public Result update(@RequestBody Admin admin) {
        if (admin.getPassword() != null && admin.getPassword() != "") {
            admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
        }
        adminService.updateById(admin);
        return Result.ok();
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/remove/{id}")
    public Result remove(@ApiParam(name = "id", value = "用户ID", required = true) @PathVariable Long id) {
        adminService.removeById(id);
        return Result.ok();
    }

    @ApiOperation("根据ID列表删除用户")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@ApiParam(name = "idList", value = "用户ID列表", required = true) @RequestBody List<Long> idList) {
        adminService.removeByIds(idList);
        return Result.ok();
    }

    @ApiOperation("获取用户角色")
    @GetMapping("/toAssign/{id}")
    public Result toAssign(@ApiParam(name = "id", value = "用户ID", required = true) @PathVariable Long id) {
        Map<String, Object> result = roleService.getRoleByAdminId(id);
        return Result.ok(result);
    }

    @ApiOperation("为用户进行角色分配")
    @PostMapping("/doAssign")
    public Result doAssign(@ApiParam(name = "id", value = "用户ID", required = true) @RequestParam Long adminId,
                           @ApiParam(name = "roleIdList", value = "角色ID列表", required = false) @RequestParam List<Long> roleIdList) {
        roleService.saveAdminRole(adminId, roleIdList);
        return Result.ok();
    }

}
