package com.agee.framework.listener;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.agee.common.constant.CacheConstants;
import com.agee.common.utils.AddressUtils;
import com.agee.common.utils.RedisUtils;
import com.agee.common.utils.ServletUtils;
import com.agee.framework.domain.LoginUser;
import com.agee.framework.domain.UserLoginEvent;
import com.agee.system.domain.resp.UserOnlineResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

/**
 * @author qimingjin
 * @date 2022-09-21 09:18
 * @Description:
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationEventListener {

    @EventListener(classes = UserLoginEvent.class)
    @Async
    public void userLoginListener(UserLoginEvent userLoginEvent){
        LoginUser loginUser = userLoginEvent.getLoginUser();
        UserAgent userAgent = UserAgentUtil.parse(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = ServletUtils.getClientIP();
        UserOnlineResp userOnlineResp = new UserOnlineResp();
        userOnlineResp.setIpaddr(ip);
        userOnlineResp.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        userOnlineResp.setBrowser(userAgent.getBrowser().getName());
        userOnlineResp.setOs(userAgent.getOs().getName());
        userOnlineResp.setLoginTime(new Date());
        userOnlineResp.setTokenId(userLoginEvent.getTokenValue());
        userOnlineResp.setUserName(loginUser.getUserName());
        userOnlineResp.setDeptName(loginUser.getDeptName());
        RedisUtils.setCacheObject(CacheConstants.ONLINE_TOKEN_KEY + userLoginEvent.getTokenValue(), userOnlineResp, Duration.ofSeconds(userLoginEvent.getTimeout()));
    }
}
