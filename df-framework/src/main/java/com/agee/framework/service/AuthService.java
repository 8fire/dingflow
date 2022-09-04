package com.agee.framework.service;

import com.agee.system.mapper.SysUserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: df
 * @description
 * @author: 没用的阿吉
 * @create: 2022-09-04 14:48
 **/
@Component
public class AuthService {

    @Resource
    private SysUserMapper sysUserMapper;

    public void doLogin(String userName,String password){



    }
}
