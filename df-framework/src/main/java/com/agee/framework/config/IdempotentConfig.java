package com.agee.framework.config;

import com.agee.framework.aop.IdempotentAspect;
import com.agee.framework.resolver.IdempotentKeyResolver;
import com.agee.framework.resolver.impl.DefaultIdempotentKeyResolver;
import com.agee.framework.resolver.impl.ExpressionIdempotentKeyResolver;
import com.agee.framework.service.IdempotentRedisDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * @author qimingjin
 * @date 2022-08-05 08:39
 * @Description:
 */
@Configuration
public class IdempotentConfig {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }
}
