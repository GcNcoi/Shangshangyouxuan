package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionService extends IService<Permission> {

    List<Permission> queryAllPermission();

    void removeChildById(Long id);

    List<Permission> queryAllPermissionByRoleId(Long roleId);

    void saveRolePermission(Long roleId, List<Long> permissionIdList);
}
