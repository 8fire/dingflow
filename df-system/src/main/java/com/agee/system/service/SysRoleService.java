package com.agee.system.service;

import com.agee.system.domain.SysRole;
import com.agee.system.domain.req.SysRoleCreateReq;
import com.agee.system.domain.req.SysRoleUpdateReq;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * @author qimingjin
 * @date 2022-09-14 16:14
 * @Description:
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据条件分页查询角色数据
     * @param sysRole 角色实体
     * @return 返回结果
     */
    List<SysRole> selectPageRoleList(SysRole sysRole);

    /**
     * 新增角色
     * @param sysRole 角色信息
     * @return 角色id
     */
    Long insertRole(SysRoleCreateReq sysRoleCreateReq);

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    void updateRole(SysRoleUpdateReq role);

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    int deleteRoleByIds(Long[] roleIds);


    /**
     * 根据用户ID查询角色权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectRolePermissionByUserId(Long userId);

    /**
     * 用户授权角色
     *
     * @param roleId 用户ID
     * @param userIds 角色组
     */
    int insertAuthUsers(Long roleId, Long[] userIds);
}
