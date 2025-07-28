package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能描述: 角色管理
 *
 * @author: Gxf
 * @date: 2025年07月18日 15:26
 */
@Api(tags = "角色接口")
@RestController
@RequestMapping("/admin/acl/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("角色条件分页查询")
    @GetMapping("/{current}/{limit}")
    public Result<IPage<Role>> pageList(@ApiParam(name = "current", value = "当前页码", required = true) @PathVariable Long current,
                                        @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
                                        @ApiParam(name = "roleQueryVo", value = "查询对象", required = false) RoleQueryVo roleQueryVo) {
        Page<Role> page = new Page<>(current, limit);
        IPage<Role> result = roleService.selectRolePage(page, roleQueryVo);
        return Result.ok(result);
    }

    @ApiOperation("根据id查询角色")
    @GetMapping("/get/{id}")
    public Result<Role> getRole(@ApiParam(name = "id", value = "角色id", required = true) @PathVariable Long id) {
        Role role = roleService.getById(id);
        return Result.ok(role);
    }

    @ApiOperation("添加角色")
    @PostMapping("/save")
    public Result<Role> saveRole(@ApiParam(name = "role", value = "角色对象", required = true) @RequestBody Role role) {
        roleService.saveOrUpdate(role);
        return Result.ok();
    }

    @ApiOperation("修改角色")
    @PutMapping("/update")
    public Result<Role> updateRole(@ApiParam(name = "role", value = "角色对象", required = true) @RequestBody Role role) {
        roleService.saveOrUpdate(role);
        return Result.ok();
    }

    @ApiOperation("根据id删除角色")
    @DeleteMapping("/remove/{id}")
    public Result<Role> removeRole(@ApiParam(name = "id", value = "角色id", required = true) @PathVariable Long id) {
        roleService.removeById(id);
        return Result.ok();
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping("/batchRemove")
    public Result<Role> batchRemoveRole(@RequestBody List<Long> idList) {
        roleService.removeByIds(idList);
        return Result.ok();
    }

}
