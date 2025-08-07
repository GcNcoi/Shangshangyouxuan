package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleService extends IService<Role> {

    /**
     * 分页查询角色列表:角色条件分页查询
     * @param page
     * @param roleQueryVo
     * @return
     */
    IPage<Role> selectRolePage(Page<Role> page, RoleQueryVo roleQueryVo);

    /**
     * 获取用户角色
     * @param id
     * @return
     */
    Map<String, Object> getRoleByAdminId(Long id);

    /**
     * 为用户进行角色分配
     * @param adminId
     * @param roleIdList
     */
    void saveAdminRole(Long adminId, List<Long> roleIdList);
}
