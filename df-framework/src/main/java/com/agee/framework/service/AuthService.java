package com.agee.framework.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.agee.common.bcrypt.BCryptPasswordEncoder;
import com.agee.common.enums.DeviceTypeEnum;
import com.agee.common.enums.EnableDisableStatusEnum;
import com.agee.common.enums.ResponseCodeEnum;
import com.agee.common.exception.ServiceException;
import com.agee.framework.domain.LoginUser;
import com.agee.system.domain.SysUser;
import com.agee.system.service.SysMenuService;
import com.agee.system.service.SysRoleService;
import com.agee.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: df
 * @description
 * @author: 没用的阿吉
 * @create: 2022-09-04 14:48
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final SysUserService sysUserService;

    private final SysPermissionService permissionService;


    public SaTokenInfo doLogin(String userName,String password){
        SysUser sysUser = sysUserService.selectUserByLoginName(userName);
        checkLogin(sysUser,userName,password);
        //赋值角色和权限
        LoginUser loginUser = BeanUtil.toBean(sysUser, LoginUser.class);
        loginUser.setRolePermission(permissionService.getRolePermission(sysUser));
        loginUser.setMenuPermission(permissionService.getMenuPermission(sysUser));

        //todo 记录登录信息
        //把登录信息交给token
        return SecurityUtils.login(sysUser.getUserId().intValue(), DeviceTypeEnum.PC, loginUser);
    }

    public void resetPwd(String oldPassword, String newPassword){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(oldPassword, loginUser.getPassword())) {
            log.info("@@AuthService->resetPwd修改密码时,老密码{}不正确",oldPassword);
            throw new ServiceException(ResponseCodeEnum.ACCOUNT_OLD_PASSWORD_ERROR);
        }
        if(encoder.matches(newPassword, loginUser.getPassword())){
            log.info("@@AuthService->resetPwd修改密码时,新密码与老密码相同");
            throw new ServiceException(ResponseCodeEnum.ACCOUNT_OLD_NEW_SOME_PASSWORD_ERROR);
        }
        SysUser sysUser=new SysUser();
        sysUser.setUserId(loginUser.getUserId());
        sysUser.setPassword(encoder.encode(newPassword));
        sysUserService.updateById(sysUser);
    }

    private void checkLogin(SysUser sysUser,String userName,String password){
        if(ObjectUtil.isEmpty(sysUser)){
            log.info("@@密码登录时,账号:{}不存在",userName);
            throw new ServiceException(ResponseCodeEnum.ACCOUNT_PASSWORD_ERROR);
        }
        if(sysUser.getStatus().equals(EnableDisableStatusEnum.DISABLE.getValue().toString())){
            log.info("@@密码登录时,账号:{}已被被禁用",userName);
            throw new ServiceException(ResponseCodeEnum.ACCOUNT_DISABLED_ERROR);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, sysUser.getPassword())) {
            log.info("@@密码登录时,账号:{}查询出该账户密码不正确",userName);
            throw new ServiceException(ResponseCodeEnum.ACCOUNT_PASSWORD_ERROR);
        }
    }

}
