package com.agee.framework.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaTokenConsts;
import cn.hutool.core.collection.CollUtil;
import com.agee.common.core.constant.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @program: df
 * @description
 * @author: 没用的阿吉
 * @create: 2022-09-03 11:08
 **/
@Configuration
public class TokenAuthConfig implements StpInterface,WebMvcConfigurer {

    @Bean
    @Primary
    public SaTokenConfig getTokenConfig(){
        SaTokenConfig config=new SaTokenConfig();
        config.setTokenName(Constants.DF_USER_TOKEN);
        config.setTimeout(30*24*60*60);
        config.setActivityTimeout(30*60);
        config.setIsConcurrent(true);
        config.setIsShare(true);
        config.setTokenStyle(SaTokenConsts.TOKEN_STYLE_RANDOM_64);
        config.setIsLog(true);
        config.setIsPrint(false);
        return config;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //不需要登录的
        List<String> notLogin= CollUtil.newArrayList(
                "/auth/doLogin",
                "/swagger-ui.html",
                "/swagger/**","/webjars/**",
                "/swagger-resources/**",
                "/doc.html","/error");
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns(notLogin);
    }

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        return null;
    }
}
