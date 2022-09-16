package com.agee.framework.resolver;

import com.agee.framework.annotation.Idempotent;
import org.aspectj.lang.JoinPoint;


/**
 * @author qimingjin
 * @date 2022-08-04 16:48
 * @Description: 幂等解析器接口
 */
public interface IdempotentKeyResolver {

    /**
     * 解析一个 Key
     *
     * @param idempotent 幂等注解
     * @param joinPoint  AOP 切面
     * @return Key
     */
    String resolver(JoinPoint joinPoint, Idempotent idempotent);
}
