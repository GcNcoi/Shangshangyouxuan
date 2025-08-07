package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionService extends IService<Permission> {

    /**
     * 查询所有菜单
     * @return
     */
    List<Permission> queryAllPermission();

    /**
     * 删除菜单
     * @param id
     */
    void removeChildById(Long id);

    /**
     * 根据角色查询权限列表
     * @param roleId
     * @return
     */
    List<Permission> queryAllPermissionByRoleId(Long roleId);

    /**
     * 授权角色权限
     * @param roleId
     * @param permissionIdList
     */
    void saveRolePermission(Long roleId, List<Long> permissionIdList);
}
