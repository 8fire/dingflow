package com.agee.system.service;

import com.agee.system.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author qimingjin
 * @date 2022-09-09 16:36
 * @Description:
 */
public interface SysUserService extends IService<SysUser> {

    SysUser selectUserByLoginName(String userName);
}
