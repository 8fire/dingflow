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
     * @param user 用户信息
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
}
