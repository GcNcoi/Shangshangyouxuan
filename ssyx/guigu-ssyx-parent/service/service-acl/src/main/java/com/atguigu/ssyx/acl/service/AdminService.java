package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminService extends IService<Admin> {

    /**
     * 分页查询:获取管理用户分页列表
     * @param pageParam
     * @param userQueryVo
     * @return
     */
    IPage<Admin> selectPage(Page<Admin> pageParam, AdminQueryVo userQueryVo);
}
