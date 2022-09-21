package com.agee.framework.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.agee.common.bcrypt.BCryptPasswordEncoder;
import com.agee.common.constant.CacheConstants;
import com.agee.common.constant.Constants;
import com.agee.common.enums.DeviceTypeEnum;
import com.agee.common.enums.EnableDisableStatusEnum;
import com.agee.common.enums.ResponseCodeEnum;
import com.agee.common.exception.ServiceException;
import com.agee.common.utils.RedisUtils;
import com.agee.common.utils.ServletUtils;
import com.agee.framework.config.CaptchaProperties;
import com.agee.framework.domain.LoginUser;
import com.agee.system.domain.SysUser;
import com.agee.system.domain.resp.SysUserResp;
import com.agee.system.domain.resp.UserOnlineResp;
import com.agee.system.service.SysLogininforService;
import com.agee.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    private final SysLogininforService logininforService;

    private final CaptchaProperties captchaProperties;


    public SaTokenInfo doLogin(String userName,String password,String code,String captchaId){
        boolean captchaEnabled = captchaProperties.isEnabled();
        // 验证码开关
        if (captchaEnabled) {
            validateCaptcha(userName, code, captchaId);
        }
        SysUser sysUser = sysUserService.selectUserByLoginName(userName);
        checkLogin(sysUser,userName,password);
        //赋值角色和权限
        LoginUser loginUser = BeanUtil.toBean(sysUser, LoginUser.class);
        loginUser.setRolePermission(permissionService.getRolePermission(sysUser));
        loginUser.setMenuPermission(permissionService.getMenuPermission(sysUser));
        //把登录信息交给token
        SaTokenInfo login = SecurityUtils.login(sysUser.getUserId().intValue(), DeviceTypeEnum.PC, loginUser);
        //记录登录日志信息
        logininforService.recordLogininfor(userName, Constants.LOGIN_SUCCESS, "登录成功", ServletUtils.getRequest());
        recordLoginInfo(sysUser.getUserId(),userName);
        return login;
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

    public SysUserResp getUserInfo(){
        SysUser sysUser = sysUserService.selectUserByLoginName(SecurityUtils.getLoginName());
        SysUserResp sysUserResp= BeanUtil.toBean(sysUser,SysUserResp.class);
        sysUserResp.setPermissions( permissionService.getMenuPermission(sysUser));
        sysUserResp.setRoles(permissionService.getRolePermission(sysUser));
        return sysUserResp;
    }

    public void logout() {
        StpUtil.logout();
        logininforService.recordLogininfor(SecurityUtils.getUserName(), Constants.LOGOUT, "退出登录成功", ServletUtils.getRequest());
    }

    public List<UserOnlineResp>  getUserOnlineList(String ipaddr, String userName){
        // 获取所有未过期的 token
        List<String> keys = StpUtil.searchTokenValue("", -1, 0,true);
        List<UserOnlineResp> userOnlineDTOList = new ArrayList<>();
        for (String key : keys) {
            String token = key.replace(CacheConstants.DF_USER_TOKEN_KEY, "");
            // 如果已经过期则跳过
            if (StpUtil.stpLogic.getTokenActivityTimeoutByToken(token) < 0) {
                continue;
            }
            userOnlineDTOList.add(RedisUtils.getCacheObject(CacheConstants.ONLINE_TOKEN_KEY + token));
        }
        if (StrUtil.isNotEmpty(ipaddr) && StrUtil.isNotEmpty(userName)) {
            userOnlineDTOList= userOnlineDTOList.stream().filter( userOnline ->
                    StrUtil.equals(ipaddr, userOnline.getIpaddr()) && StrUtil.equals(userName, userOnline.getUserName())
            ).collect(Collectors.toList());
        } else if (StrUtil.isNotEmpty(ipaddr)) {

            userOnlineDTOList= userOnlineDTOList.stream().filter( userOnline ->
                    StrUtil.equals(ipaddr, userOnline.getIpaddr())).collect(Collectors.toList());
        } else if (StrUtil.isNotEmpty(userName)) {
            userOnlineDTOList= userOnlineDTOList.stream().filter( userOnline ->
                    StrUtil.equals(ipaddr, userOnline.getUserName())).collect(Collectors.toList());
        }
        Collections.reverse(userOnlineDTOList);
        userOnlineDTOList.removeAll(Collections.singleton(null));
        return userOnlineDTOList;
    }

    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StrUtil.nullToDefault(uuid, "");
        String captcha = RedisUtils.getCacheObject(verifyKey);
        RedisUtils.deleteObject(verifyKey);
        if (captcha == null) {
            logininforService.recordLogininfor(username, Constants.LOGIN_FAIL, "验证码已过期", ServletUtils.getRequest());
            log.info("@@AuthService->validateCaptcha验证码已过期");
            throw new ServiceException(ResponseCodeEnum.ACCOUNT_CAPTCHA_EXPIRE_ERROR);
        }
        if (!code.equalsIgnoreCase(captcha)) {
            log.info("@@AuthService->validateCaptcha验证码不正确");
            logininforService.recordLogininfor(username, Constants.LOGIN_FAIL, "验证码不正确", ServletUtils.getRequest());
            throw new ServiceException(ResponseCodeEnum.ACCOUNT_CAPTCHA_ERROR);
        }
    }
    /**
     * 记录登录信息
     */
    private void recordLoginInfo(Long userId, String username) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(ServletUtil.getClientIP(ServletUtils.getRequest()));
        sysUser.setLoginDate(new Date());
        sysUser.setUpdateBy(username);
        sysUser.setUpdateTime(new Date());
        sysUserService.updateById(sysUser);
    }

    private void checkLogin(SysUser sysUser,String userName,String password){
        if(ObjectUtil.isEmpty(sysUser)){
            log.info("@@AuthService->checkLogin密码登录时,账号:{}不存在",userName);
            logininforService.recordLogininfor(userName, Constants.LOGIN_FAIL, "账户不存在", ServletUtils.getRequest());
            throw new ServiceException(ResponseCodeEnum.ACCOUNT_PASSWORD_ERROR);
        }
        if(sysUser.getStatus().equals(EnableDisableStatusEnum.DISABLE.getValue().toString())){
            log.info("@@AuthService->checkLogin密码登录时,账号:{}已被被禁用",userName);
            logininforService.recordLogininfor(userName, Constants.LOGIN_FAIL, "账户已被禁用", ServletUtils.getRequest());
            throw new ServiceException(ResponseCodeEnum.ACCOUNT_DISABLED_ERROR);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, sysUser.getPassword())) {
            log.info("@@AuthService->checkLogin密码登录时,账号:{}查询出该账户密码不正确",userName);
            logininforService.recordLogininfor(userName, Constants.LOGIN_FAIL, "密码不正确", ServletUtils.getRequest());
            throw new ServiceException(ResponseCodeEnum.ACCOUNT_PASSWORD_ERROR);
        }
    }

}
