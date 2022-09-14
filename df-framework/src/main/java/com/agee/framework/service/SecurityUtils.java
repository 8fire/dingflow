package com.agee.framework.service;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.*;
import cn.hutool.core.util.StrUtil;
import com.agee.common.core.constant.Constants;
import com.agee.common.enums.DeviceTypeEnum;
import com.agee.framework.domain.LoginUser;
import org.springframework.util.ObjectUtils;

/**
 * @author qimingjin
 * @date 2022-09-09 13:58
 * @Description:
 */
public class SecurityUtils {

    public static StpLogic stpLogic = new StpLogic(StpUtil.TYPE);
    /**
     * 获取当前登录人userId
     * @return 返回userId
     */
    public static int getLoginUserId(){
        return StpUtil.getLoginIdAsInt();
    }

    /**
     * 获取当前登录人userId
     * @return 返回userId字符串
     */
    public static String getLoginUserIdAsString(){
        return StpUtil.getLoginIdAsString();
    }

    /**
     * 获取当前登录人的账户（手机号）
     * @return 账户（手机号）
     */
    public static String getLoginUserName(){
        Object extra = StpUtil.getExtra(Constants.LOGIN_USER_NAME);
        if(ObjectUtils.isEmpty(extra)){
            return null;
        }
        return String.valueOf(extra);
    }


    /**
     * 获取当前登录人的token值
     * @return
     */
    public static String getTokenValue(){
        return StpUtil.getTokenValue();
    }

    /**
     * 判断当前是否登录
     * @return 是否登录
     */
    public static Boolean getIsLogin(){
        return StpUtil.isLogin();
    }

    /**
     * 获取当前登录人的token信息
     * @return token信息
     */
    public static SaTokenInfo getTokenInfo(){
        return StpUtil.getTokenInfo();
    }

    /**
     * 登录
     * @param userId 登录用户id
     * @param deviceTypeEnum 设备类型
     * @param loginUser 用户信息
     * @return 登录成功信息
     */
    public static SaTokenInfo login(Integer userId, DeviceTypeEnum deviceTypeEnum, LoginUser loginUser){
        SaHolder.getStorage().set(Constants.LOGIN_USER, loginUser);
        SaLoginModel saLoginModel = SaLoginConfig.setDevice(deviceTypeEnum.getCode())
                .setExtra(Constants.LOGIN_USER, loginUser);
        StpUtil.login(userId, saLoginModel);
        return StpUtil.getTokenInfo();
    }


    /**
     * 获取指定Token对应的账号id
     *  如果被顶，被踢,超时会跑抛出异常
     * @param tokenValue token值
     * @return 账号id
     */
    public static String getValidLoginId(String tokenValue) {
        String loginIdNotHandle = stpLogic.getLoginIdNotHandle(tokenValue);
        // 如果是已经过期，则抛出：已经过期
        if(StrUtil.isNotEmpty(loginIdNotHandle)&&loginIdNotHandle.equals(NotLoginException.TOKEN_TIMEOUT)) {
            throw NotLoginException.newInstance(StpUtil.TYPE, NotLoginException.TOKEN_TIMEOUT, tokenValue);
        }
        // 如果是已经被顶替下去了, 则抛出：已被顶下线
        if(StrUtil.isNotEmpty(loginIdNotHandle)&&loginIdNotHandle.equals(NotLoginException.BE_REPLACED)) {
            throw NotLoginException.newInstance(StpUtil.TYPE, NotLoginException.BE_REPLACED, tokenValue);
        }
        // 如果是已经被踢下线了, 则抛出：已被踢下线
        if(StrUtil.isNotEmpty(loginIdNotHandle)&&loginIdNotHandle.equals(NotLoginException.KICK_OUT)) {
            throw NotLoginException.newInstance(StpUtil.TYPE, NotLoginException.KICK_OUT, tokenValue);
        }
        return loginIdNotHandle;
    }
}
