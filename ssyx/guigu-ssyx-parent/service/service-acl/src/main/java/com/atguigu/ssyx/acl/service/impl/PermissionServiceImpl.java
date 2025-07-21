package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.PermissionMapper;
import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.acl.service.RolePermissionService;
import com.atguigu.ssyx.acl.util.PermissionHelper;
import com.atguigu.ssyx.model.acl.Permission;
import com.atguigu.ssyx.model.acl.RolePermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @BelongsProject: guigu-ssyx-parent
 * @BelongsPackage: com.atguigu.ssyx.acl.service.impl
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-21  20:37
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public List<Permission> queryAllPermission() {
        // 1.查询所有菜单
        List<Permission> allPermissionList = baseMapper.selectList(null);
        // 2.转换要求数据格式
        List<Permission> result = PermissionHelper.buildPermission(allPermissionList);
        return result;
    }

    @Override
    public void removeChildById(Long id) {
        List<Long> idList = new ArrayList<>();
        this.getAllPermissionId(id, idList);
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    @Override
    public List<Permission> queryAllPermissionByRoleId(Long roleId) {
        // 1.查询所有菜单
        List<Permission> allPermissionList = baseMapper.selectList(null);
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId).select(RolePermission::getPermissionId);
        List<RolePermission> rolePermissions = rolePermissionService.list(wrapper);
        List<Long> existPermissionList = rolePermissions.stream().map(s -> s.getPermissionId()).collect(Collectors.toList());
        for (Permission permission : allPermissionList) {
            if (existPermissionList.contains(permission.getId())) {
                permission.setSelect(true);
            }
        }
        // 2.转换要求数据格式
        List<Permission> result = PermissionHelper.buildPermission(allPermissionList);
        return result;
    }

    @Override
    public void saveRolePermission(Long roleId, List<Long> permissionIdList) {
        // 1.删除角色分配过的权限
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId);
        rolePermissionService.remove(wrapper);
        // 2.重新分配
        List<RolePermission> rolePermissionList = new ArrayList<>();
        for (Long permissionId : permissionIdList)  {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissionList);
    }

    private void getAllPermissionId(Long id, List<Long> idList) {
        // 根据当前id查询子id
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getPid, id);
        List<Permission> childList = baseMapper.selectList(wrapper);
        // 递归查询
        childList.stream().forEach(item -> {
            // 封装菜单id到idList集合里面
            idList.add(item.getId());
            this.getAllPermissionId(item.getId(), idList);
        });
    }

}
