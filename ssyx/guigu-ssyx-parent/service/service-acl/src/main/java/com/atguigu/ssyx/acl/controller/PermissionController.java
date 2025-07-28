package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: guigu-ssyx-parent
 * @BelongsPackage: com.atguigu.ssyx.acl.controller
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-21  20:35
 * @Description: TODO 菜单管理 前端控制器
 * @Version: 1.0
 */

@RestController
@RequestMapping("/admin/acl/permission")
@Api(tags = "菜单管理")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("查询所有菜单")
    @GetMapping
    public Result<List<Permission>> list() {
        List<Permission> result = permissionService.queryAllPermission();
        return Result.ok(result);
    }

    @ApiOperation("添加菜单")
    @PostMapping("/save")
    public Result save(@ApiParam(name = "permission", value = "权限", required = true) @RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok();
    }

    @ApiOperation("修改菜单")
    @PutMapping("/update")
    public Result update(@ApiParam(name = "permission", value = "权限", required = true) @RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok();
    }

    @ApiOperation("删除菜单")
    @DeleteMapping("/remove")
    public Result remove(@ApiParam(name = "id", value = "菜单ID", required = true) @PathVariable Long id) {
        permissionService.removeChildById(id);
        return Result.ok();
    }

    @ApiOperation("查看某个角色的权限列表")
    @GetMapping("/toAssign/{roleId}")
    public Result<List<Permission>> toAssign(@ApiParam(name = "id", value = "角色ID", required = true) @PathVariable Long roleId) {
        List<Permission> result = permissionService.queryAllPermissionByRoleId(roleId);
        return Result.ok(result);
    }

    @ApiOperation("给某个角色授权")
    @PostMapping("/doAssign")
    public Result doAssign(@ApiParam(name = "roleId", value = "角色ID", required = true) @RequestParam Long roleId,
                           @ApiParam(name = "permissionIdList", value = "权限ID列表", required = false) @RequestParam List<Long> permissionIdList) {
        permissionService.saveRolePermission(roleId, permissionIdList);
        return Result.ok();
    }

}
