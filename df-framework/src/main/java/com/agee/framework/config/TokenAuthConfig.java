package com.agee.framework.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.util.SaTokenConsts;
import com.agee.common.core.constant.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: df
 * @description
 * @author: 没用的阿吉
 * @create: 2022-09-03 11:08
 **/
@Configuration
public class TokenAuthConfig implements WebMvcConfigurer {

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
}
