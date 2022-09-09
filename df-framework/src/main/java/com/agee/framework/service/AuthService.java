package com.agee.framework.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.hutool.core.util.ObjectUtil;
import com.agee.common.bcrypt.BCryptPasswordEncoder;
import com.agee.common.core.domain.UserExtraEntity;
import com.agee.common.enums.DeviceTypeEnum;
import com.agee.common.enums.EnableDisableStatusEnum;
import com.agee.common.enums.LoginTypeEnum;
import com.agee.common.enums.ResponseCodeEnum;
import com.agee.common.exception.ServiceException;
import com.agee.common.utils.SecurityUtils;
import com.agee.system.domain.SysUser;
import com.agee.system.service.SysUserService;
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
public class AuthService {

    @Autowired
    private SysUserService sysUserService;

    public SaTokenInfo doLogin(String userName,String password){
        SysUser sysUser = sysUserService.selectUserByLoginName(userName);
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
        //组装额外session信息
        UserExtraEntity userExtraEntity = UserExtraEntity.builder()
                .userName(sysUser.getUserName())
                .loginName(sysUser.getLoginName())
                .loginType(LoginTypeEnum.PASSWORD.getCode())
                .build();
        //把登录信息交给token
        return SecurityUtils.login(sysUser.getUserId().intValue(), DeviceTypeEnum.PC, userExtraEntity);
    }

}
