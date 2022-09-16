package com.agee.framework.resolver.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.agee.framework.annotation.Idempotent;
import com.agee.common.core.constant.Constants;
import com.agee.common.utils.ServletUtils;
import com.agee.framework.resolver.IdempotentKeyResolver;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;


/**
 * @author qimingjin
 * @date 2022-08-04 16:50
 * @Description:
 *   默认幂等 Key 解析器，使用方法名 + 方法参数，组装成一个 Key
 *  为了避免 Key 过长，使用 MD5 进行“压缩”
 */
public class DefaultIdempotentKeyResolver implements IdempotentKeyResolver {

    @Override
    public String resolver(JoinPoint joinPoint, Idempotent idempotent) {
        String methodName = joinPoint.getSignature().toString();
        String argsStr = StrUtil.join(",", joinPoint.getArgs());
        HttpServletRequest request = ServletUtils.getRequest();
        if(ObjectUtil.isNull(request)){
            return SecureUtil.md5(methodName + argsStr);
        }
        String backToken = request.getHeader(Constants.DF_USER_TOKEN);
        //获取管理员端token
        if(StrUtil.isNotEmpty(backToken)){
            return SecureUtil.md5(methodName + argsStr + backToken);
        }
        return SecureUtil.md5(methodName + argsStr);
    }
}
