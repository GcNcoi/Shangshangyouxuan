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

    IPage<Role> selectRolePage(Page<Role> page, RoleQueryVo roleQueryVo);

    Map<String, Object> getRoleByAdminId(Long id);

    void saveAdminRole(Long adminId, List<Long> roleIdList);

}
