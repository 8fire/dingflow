package com.agee.system.service.impl;

import com.agee.system.domain.SysUser;
import com.agee.system.mapper.SysUserMapper;
import com.agee.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qimingjin
 * @date 2022-09-09 16:36
 * @Description:
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService  {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser selectUserByLoginName(String userName) {
        return sysUserMapper.selectUserByLoginName(userName);
    }
}
