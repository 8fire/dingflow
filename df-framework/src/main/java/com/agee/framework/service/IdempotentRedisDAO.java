package com.agee.framework.service;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author qimingjin
 * @date 2022-08-04 16:45
 * @Description:
 */
@AllArgsConstructor
public class IdempotentRedisDAO {
    private static final String DEFAULT_IDEMPOTENT_KEY_PREFIX = "df:common:idempotent:{}";

    private final StringRedisTemplate redisTemplate;

    public Boolean setIfAbsent(String key, long timeout, TimeUnit timeUnit) {
        String redisKey = formatKey(key);
        return redisTemplate.opsForValue().setIfAbsent(redisKey, "", timeout, timeUnit);
    }

    private static String formatKey(String key) {
        return StrUtil.format(DEFAULT_IDEMPOTENT_KEY_PREFIX, key);
    }
}
