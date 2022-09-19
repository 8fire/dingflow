package com.agee.system.service;

import com.agee.system.domain.SysUser;
import com.agee.system.domain.req.SysUserCreateReq;
import com.agee.system.domain.req.SysUserPageQueryReq;
import com.agee.system.domain.req.SysUserUpdateReq;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author qimingjin
 * @date 2022-09-09 16:36
 * @Description:
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据条件分页查询用户列表
     *
     * @param sysUserPageQueryReq 用户信息
     * @return 用户信息集合信息
     */
    List<SysUser> selectUserList(SysUserPageQueryReq sysUserPageQueryReq);

    /**
     * 新增用户信息
     *
     * @param sysUserCreateReq 用户信息
     * @return 结果
     */
    Long insertUser(SysUserCreateReq sysUserCreateReq);
    /**
     * 修改保存用户信息
     *
     * @param sysUserUpdateReq 用户信息
     * @return 结果
     */
    Long editUser(SysUserUpdateReq sysUserUpdateReq);

    /**
     * 根据登录账户查询用户信息
     * @param loginName 登录账户
     * @return 用户信息
     */
    SysUser selectUserByLoginName(String loginName);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    int deleteUserByIds(Long[] userIds);

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    void checkUserAllowed(SysUser user);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
     SysUser selectUserById(Long userId);

    /**
     * 保存用户角色关系
     * @param userId 用户id
     * @param roleIds 角色集合
     */
     void insertUserRole(Long userId, Long[] roleIds);
}
