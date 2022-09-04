package com.agee.framework.service;

import com.agee.common.core.domain.R;
import com.agee.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: df
 * @description
 * @author: 没用的阿吉
 * @create: 2022-09-04 14:48
 **/
@Component
public class AuthService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public void doLogin(String userName,String password){

        return R.ok();
    }
}
