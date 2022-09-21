package com.agee.framework.domain;

import org.springframework.context.ApplicationEvent;

/**
 * @author qimingjin
 * @date 2022-09-21 09:01
 * @Description:
 */
public class UserLoginEvent extends ApplicationEvent {
    private static final long serialVersionUID = -1783300481399991184L;


    /**
     * token值
     */
    private String tokenValue;


    /**
     * token的长久有效期(单位:秒)
     */
    private long timeout;

    /**
     * 用户登录信息
     */
    private LoginUser loginUser;



    public UserLoginEvent(Object source) {
        super(source);
    }

    public UserLoginEvent(Object source,String tokenValue,long timeout,LoginUser loginUser) {
        super(source);
        this.tokenValue=tokenValue;
        this.timeout=timeout;
        this.loginUser=loginUser;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }
}
