package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.RoleMapper;
import com.atguigu.ssyx.acl.service.AdminRoleService;
import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.model.acl.AdminRole;
import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 功能描述
 *
 * @author: Gxf
 * @date: 2025年07月18日 15:36
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private AdminRoleService adminRoleService;

    @Override
    public IPage<Role> selectRolePage(Page<Role> page, RoleQueryVo roleQueryVo) {
        // 获取条件值
        String roleName = roleQueryVo.getRoleName();
        // 创建条件构造器对象
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        // 判断条件之是否为空,如果不为空则封装查询条件
        if (!StringUtils.isEmpty(roleName)) {
            wrapper.like(Role::getRoleName, roleName);
        }
        // 调用方法实现条件分页查询
        Page<Role> rolePage = baseMapper.selectPage(page, wrapper);
        // 返回分页对象
        return rolePage;
    }

    @Override
    public Map<String, Object> getRoleByAdminId(Long id) {
        // 1.查询所有角色
        List<Role> allRolesList = baseMapper.selectList(null);
        // 2.根据用户id查询用户角色
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId, id);
        List<AdminRole> adminRoleList = adminRoleService.list(wrapper);
        List<Long> roleIdList = adminRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        List<Role> assignRoleList = allRolesList.stream()
                .filter(role -> roleIdList.contains(role.getId()))
                .collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("allRolesList", allRolesList);
        map.put("assignRoles", assignRoleList);
        return map;
    }

    @Override
    public void saveAdminRole(Long adminId, List<Long> roleIdList) {
        // 1.删除用户分配过的角色
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId, adminId);
        adminRoleService.remove(wrapper);
        // 2.重新分配
        List<AdminRole> adminRoleList = new ArrayList<>();
        for (Long roleId : roleIdList) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
            adminRoleList.add(adminRole);
        }
        adminRoleService.saveBatch(adminRoleList);
    }

}
