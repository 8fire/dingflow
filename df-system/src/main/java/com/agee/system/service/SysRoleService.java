package com.agee.system.service;

import com.agee.system.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * @author qimingjin
 * @date 2022-09-14 16:14
 * @Description:
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据用户ID查询角色权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectRolePermissionByUserId(Long userId);
}
